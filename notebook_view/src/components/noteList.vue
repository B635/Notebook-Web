<template>
  <div class="app-container">
    <div class="search-box">
      <v-list v-model="noteSelect">
        <v-row style="align-items: center; padding-left: 12px">
          <v-sheet
              width="50"
              height="50"
          >
            标题:
          </v-sheet>
            <v-text-field
                style="width: 150px;
                margin-left: 10px;
                margin-right: 20px;"
                outlined
                dense
                class="shrink"
                placeholder="请输入笔记标题"
                v-model="selectTitle"
            > </v-text-field>
          <v-sheet
              width="90"
              height="50"
          >
           笔记状态:
          </v-sheet>
          <v-select
              placeholder="请选择笔记状态"
              class="shrink"
              style="width: 150px;
                margin-left: 10px;
                margin-right: 20px;
                margin-bottom: 20px"
              v-model="selectStatus"
              :items="statusSelect"
              item-value="value"
              item-text="label"
            >
          </v-select>
          <v-sheet
              width="90"
              height="50"
          >
            笔记分类:
          </v-sheet>
            <v-select
                placeholder="请选择笔记分类"
                style="width: 150px;
                margin-left: 10px;
                margin-bottom: 20px;
                margin-right: 20px;"
                v-model="selectCategory"
                :items="usedCategoryList"
                class="shrink"
                item-text="name"
                item-value="id"
            >
            </v-select>
          <v-btn
              style="margin-left: 50px;margin-bottom: 20px;"
              @click="selectFind"
              color="#9BACD8"
          >
            查询
          </v-btn>
          <v-btn
              style="margin-left: 20px;margin-bottom: 20px;"
              @click="resetSelectParams"
              color="#FFE0E5"
          >
            重置
          </v-btn>
        </v-row>
      </v-list>
    </div>
    <div style="padding-bottom: 15px">
        <v-btn
            color="#FBC2A6"
            @click="toWrite"
            small
        >
          <v-icon
              small
              dark
          >
            mdi-border-color
          </v-icon>
          写文章
        </v-btn>
    </div>
    <v-snackbar
        :timeout = "timeout"
        v-model="alert"
    >
      {{ message }}
    </v-snackbar>
    <template class="pa-10">
      <v-data-table
        :headers="headers"
        :items="noteList"
        hide-default-footer
        class="elevation-1"
        >
        <template v-slot:item.status="{ item }">
          <v-badge left offset-x="-5px" offset-y="12px" dot :color="getStatus(item.status).type">
            {{ getStatus(item.status).name }}
          </v-badge>
        </template>
        <template v-slot:item.date="{ item }">
          {{new Date(item.date).toLocaleString()}}
        </template>
        <template v-slot:item.label="{ item }">
          <v-chip-group center-active>
            <v-chip  v-for="label in item.label" :key="label.id" small :color="getRandomTagType()">
              {{ label.name }}
            </v-chip>
          </v-chip-group>
        </template>
        <template v-slot:item.operation="{ item }">
          <v-btn
              v-if="item.status === 'RECYCLE'"
              small
              color="#F6B4A6"
              @click="statusRecycleToSaved(item)"
          >
            还原
          </v-btn>
          <v-btn
              v-else
              small
              color="#F6B4A6"
          >
            编辑
          </v-btn>
          <v-btn
              color="#DC8C6B"
              small
              @click="showSittingDrawer(item)"
          >
            设置
          </v-btn>
          <v-btn
              color="#70ACC2"
              small
              v-if="item.status === 'RECYCLE'"
              @click="deleteNote(item.id)"
          >
            删除
          </v-btn>
          <v-btn
            v-else
            small
            color="#70ACC2"
            @click="statusToRecycle(item)"
          >
            回收站
          </v-btn>
        </template>
      </v-data-table>
      <v-pagination
          :total="total"
          v-model="currentPage"
          :length="total / noteSelect.pageSize + 1"
          :total-visible="7"
          @input="changePage(currentPage)"
        >

      </v-pagination>
    </template>
    <v-navigation-drawer
        right
        absolute
        temporary
        width="500"
        v-model="sittingDrawer"
    >
      <v-list-item>
        笔记设置
      </v-list-item>
      <v-sitting-drawer ref="sittingDrawer">
      </v-sitting-drawer>
      <div class="footer-toolbar" style="width: 30%">
        <v-btn
            color="#FFE0E5"
            right
            @click="updateNote"
        >
          保存
        </v-btn>
      </div>
    </v-navigation-drawer>
  </div>
</template>

<script>

export default {
  name: "noteList",
  components: {
    'v-sitting-drawer': () => import('@/components/sittingDraw'),
  },
  data: () => ({
    noteSelect: {
      current: 1,
      pageSize: 10,
      title: null,
      categoryId: null,
      status: null
    },
    sittingDrawer: false,
    message: '',
    selectTitle: '',
    selectStatus: '',
    selectCategory: 0,
    currentPage: 1,
    headers: [
      {
        text: '标题',
        value: 'title'
      },
      {
        text: '状态',
        value: 'status'
      },
      {
        text: '分类',
        value: 'category.name'
      },
      {
        text: '标签',
        value: 'label'
      },
      {
        text: '日期',
        value: 'date'
      },
      {
        text: '操作',
        value: 'operation'
      }
    ],
    noteList: [],
    usedCategoryList: [],
    statusSelect: [
      {label: '已保存', value: 'SAVED'},
      {label: '草稿箱', value: 'DRAFT'},
      {label: '回收站', value: 'RECYCLE'}
    ],
    alert: false,
    timeout: 1500,
    total: 0,
  }),
  methods: {
    data() {
      fetch("http://127.0.0.1:8080/api/note/list", {
        method: 'post',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
          current: this.noteSelect.current,
          pageSize: this.noteSelect.pageSize,
          title: this.noteSelect.title,
          categoryId: this.noteSelect.categoryId,
          status: this.noteSelect.status
        })
      }).then(r => r.json())
          .then(data => {
            this.noteList = data.data.list
            this.total = data.data.total
          })
          .catch(err => console.log(err))
    },

    changePage(page) {
      this.noteSelect.current = page
      this.data()
    },

    toWrite() {
      this.$router.replace("/note/write")
    },

    resetSelectParams() {
      this.noteSelect.categoryId=null
      this.noteSelect.status=null
      this.noteSelect.title=null
      this.noteSelect.current = 1
      this.data()
    },

    selectFind() {
      this.noteSelect.title = this.selectTitle
      this.noteSelect.status = this.selectStatus
      this.noteSelect.categoryId = this.selectCategory
      this.data()
    },

    showSittingDrawer(item) {
      this.sittingDrawer = true
      setTimeout(() => {
        this.$refs.sittingDrawer.setData(item)
      }, 200)
    },

    statusRecycleToSaved(item) {
      fetch("http://127.0.0.1:8080/api/note/update", {
        method: 'post',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
          id: item.id,
          status: 'SAVED'
        })
      }).then(r => r.json())
          .then(data => {
            this.message = data.message
            this.alert = true
            this.data()
          })
          .catch(err => console.log(err))
    },

    statusToRecycle(item) {
      fetch("http://127.0.0.1:8080/api/note/update", {
        method: 'post',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
          id: item.id,
          status: 'RECYCLE'
        })
      }).then(r => r.json())
          .then(data => {
            this.message = data.message
            this.alert = true
            this.data()
          })
          .catch(err => console.log(err))
    },

    deleteNote(noteId) {
      fetch("http://127.0.0.1:8080/api/note/delete/" + noteId, {
        method: 'delete',
      }).then(r => r.json())
          .then(data => {
            this.message = data.message
            this.alert = true
            this.data()
          })
          .catch(err => console.log(err))
    },

    getRandomTagType() {
      const tagType = ['#FFE0E5', '#DC8C6B', '#F6B4A6', '#70ACC2', '#C7A4C4', '#9BACD8']
      return tagType[Math.floor(Math.random() * tagType.length)]
    },

    getStatus(val) {
      const status = {
        SAVED: {
          name: '已保存',
          type: 'success'
        },
        DRAFT: {
          name: '草稿箱',
          type: 'info'
        },
        RECYCLE: {
          name: '回收站',
          type: 'error'
        }
      }
      const s = status[val]
      return s || { name: '', icon: '' }
    },

    updateNote() {
      const noteData = this.$refs.sittingDrawer.getData().noteData
      fetch("http://127.0.0.1:8080/api/note/update", {
        method: 'post',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
          id: noteData.id,
          title: noteData.title,
          summary: noteData.summary,
          category: noteData.category,
          label: noteData.label
        })
      }).then(r => r.json())
          .then(data => {
            this.message = data.message
            this.sittingDrawer = false
            this.data()
          })
          .catch(err => console.log(err))
    },

    listUsedCategory() {
      fetch("http://127.0.0.1:8080/api/category/list/used", {credentials: 'include'})
          .then(r => r.json()
          )
          .then(data => this.usedCategoryList = data.data)
          .catch(err => console.log(err))
    },
  },
  mounted() {
    this.data()
    this.listUsedCategory()
  }
}
</script>

<style lang="scss" scoped>
.footer-toolbar {
  z-index: 2000;
  position: fixed;
  bottom: 0;
  right: 0;
  height: 56px;
  line-height: 56px;
  padding: 0 24px;
  background-color: #fff;
  border-top: 1px #e8e8e8 solid;
  box-shadow: 0 -1px 2px rgba(0, 0, 0, .03);
  text-align: right;
}

.app-container {
  padding: 24px;
}

.search-box {
  .el-form-item {
    margin-right: 40px;
  }
}

</style>
