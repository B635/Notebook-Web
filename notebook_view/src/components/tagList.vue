<template>
    <div class ="app-container">
      <v-snackbar
          :timeout = "timeout"
          v-model="alert"
      >
        {{ message }}
      </v-snackbar>
      <v-row >
        <v-col :sm="5">
          <v-card
          >
            <v-card-text > {{ modified ? '修改': '添加'}}分类</v-card-text>
            <v-card-text>
              <v-list ref="tagFrom" v-model="addOrUpdateData" >
                <v-sheet>
                  名称:
                  <v-text-field
                      outlined
                      dense
                      v-model="addOrUpdateData.name">

                  </v-text-field>
                </v-sheet>
              </v-list>
              <v-btn
                  color="#F6B4A6"
                  small
                  @click="modified? updateTag(addOrUpdateData.id): addTag()">
                {{ modified ? '更 新' : '保 存' }}
              </v-btn>
              <v-btn v-if="modified"  color="#70ACC2" small @click="resetForm">
                返回添加
              </v-btn>
              <v-btn v-if="modified"
                     color="#DC8C6B"
                     small
                     style="float: right"
                     @click="deleteTag(addOrUpdateData.id)">
                删除
              </v-btn>
            </v-card-text>
          </v-card>
        </v-col>
        <v-col :sm="7">
          <v-card>
            <v-card-text> 所有标签 </v-card-text>
            <v-empty-data v-if="tagList.length===0" style="width: 100px" />
            <div class="tag-group">
              <v-chip-group center-active style="margin-left: 10px">
                <v-chip
                    v-for="tag in tagList"
                    :key="tag.id"
                    small :color="getRandomTagType()"
                    @click="tryUpdate(tag)"
                >
                  {{ tag.name }}
                </v-chip>
              </v-chip-group>
            </div>

          </v-card>
        </v-col>
      </v-row>
    </div>
</template>

<script>
export default {
  name: "tagList",
  components: {
    'v-empty-data': ()=> import('@/components/empty-data')
  },
  data() {
    return {
      tagList: [],
      listLoading: false,
      modified: false,
      timeout: 1500,
      alert: false,
      message: '',
      addOrUpdateData: {
        name: ''
      }
    }
  },
  created() {
    this.getTagList()
  },
  methods: {
    resetForm() {
      this.addOrUpdateData = { name: '' }
      this.modified = false
    },
    getRandomTagType() {
      const tagType = ['#FFE0E5', '#DC8C6B', '#F6B4A6', '#70ACC2', '#C7A4C4', '#9BACD8']
      return tagType[Math.floor(Math.random() * tagType.length)]
    },
    operationSuccess(msg) {
      this.message = msg
      this.alert = true
      this.getCategoryList()
      this.resetForm()
    },
    tryUpdate(index) {
      // modified是某组件v-if渲染条件，本函数是 v-for item中绑定的事件，
      // 点击item时会造成v-for重新渲染，解决方法？
      this.modified = true
      this.addOrUpdateData = JSON.parse(JSON.stringify(index))
    },
    getTagList() {
      this.listLoading = true
      fetch("http://127.0.0.1:8080/api/tag/list", {credentials: 'include'})
          .then(r => r.json()
          )
          .then(data => {
            this.tagList = data.data
            this.listLoading = false
          })
          .catch(err => console.log(err))
    },
    deleteTag(id) {
      fetch("http://127.0.0.1:8080/api/tag/delete/" + id, {
        method: 'delete',
        headers: {'Content-Type': 'application/json'},
      }).then(r => r.json())
          .then(data => {
            this.getTagList()
            this.resetForm()
            this.operationSuccess(data.message)
          })
          .catch(err => console.log(err))
    },
    addTag() {
      fetch("http://127.0.0.1:8080/api/tag/add", {
        method: 'post',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
          name: this.addOrUpdateData.name,
        })
      }).then(r => r.json())
          .then(data => {
            this.getTagList()
            this.operationSuccess(data.message)
          })
          .catch(err => console.log(err))
    },
    updateTag(index) {
      fetch("http://127.0.0.1:8080/api/tag/update", {
        method: 'post',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
          id: index,
          name: this.addOrUpdateData.name
        })
      }).then(r => r.json())
          .then(data => {
            this.getTagList()
            this.operationSuccess(data.message)
          })
          .catch(err => console.log(err))
    }
  }
}
</script>

<style lang="scss">
.el-card {
  &__header, &__body {
    padding: 12px 12px;
  }
}

.tag-group .el-tag {
  margin: 6px 3px;
  cursor: pointer;
}
</style>

<style scoped lang="scss">
.pagination {
  float: right;
  margin: 16px 0;
}

.app-container {
  padding: 24px;
}
</style>
