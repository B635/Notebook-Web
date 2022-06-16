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
          <v-card-text > {{ addOrUpdateData.id ? '修改': '添加'}}分类</v-card-text>
          <v-card-text>
            <v-list ref="categoryFrom" v-model="addOrUpdateData" >
                <v-sheet>
                  名称:
                  <v-text-field
                      outlined
                      dense
                      v-model="addOrUpdateData.name">

                  </v-text-field>
                </v-sheet>
              <v-sheet>
                描述:
                <v-textarea
                    single-line
                    color="teal"
                    v-model="addOrUpdateData.description">
                </v-textarea>
              </v-sheet>
            </v-list>
            <v-btn
                color="#F6B4A6"
              small
              @click="addOrUpdateData.id? updateCategory(addOrUpdateData.id): addCategory()">
              {{ addOrUpdateData.id ? '更 新' : '保 存' }}
            </v-btn>
            <v-btn v-if="addOrUpdateData.id"  color="#70ACC2" small @click="resetForm">
              返回添加
            </v-btn>
          </v-card-text>
        </v-card>
      </v-col>
      <v-col :sm="7">
        <v-card>
          <v-card-text> 分类列表 </v-card-text>
          <v-data-table
            ref="noteListTable"
            hide-default-footer
            :items="categoryList"
            :headers="headers">
            <template v-slot:item.operation="{ item }">
              <v-btn
                  small
                  color="#F6B4A6"
                  @click="tryUpdate(item)"
              >
                编辑
              </v-btn>
              <v-btn
                  color="#70ACC2"
                  small
                  @click="deleteCategory(item.id)"
              >
                删除
              </v-btn>
            </template>
          </v-data-table>
          <v-pagination
              :total="total"
              v-model="currentPage"
              :length="total / searchParams.pageSize + 1"
              :total-visible="7"
              @input="changePage(currentPage)"
          >

          </v-pagination>
        </v-card>
      </v-col>
    </v-row>
  </div>
</template>

<script>
export default {
  name: "categoryList",
  data() {
    return {
      currentPage: 1,
      timeout: 1500,
      message: '',
      headers: [
        {
          text: '名称',
          value: 'name'
        },
        {
          text: '文章数',
          value: 'noteCount'
        },
        {
          text: '描述',
          value: 'description'
        },
        {
          text: '操作',
          value: 'operation'
        }
      ],
      alert: false,
      searchParams: {
        current: 1,
        pageSize: 10
      },
      total: 0,
      categoryList: [],
      tableDataLoading: false,
      addOrUpdateData: {
        name: '',
        description: ''
      }
    }
  },
  created() {
    this.getCategoryList()
  },
  methods: {
    resetForm() {
      this.addOrUpdateData= {name: '', description: ''}
    },
    changePage(page) {
      this.searchParams.current = page
      this.getCategoryList()
    },
    operationSuccess(msg) {
      this.message = msg
      this.alert = true
      this.getCategoryList()
      this.resetForm()
    },
    tryUpdate(item) {
      this.addOrUpdateData = JSON.parse(JSON.stringify(item))
    },
    getCategoryList() {
      this.tableDataLoading = true
      fetch("http://127.0.0.1:8080/api/category/list", {
        method: 'post',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
          current: this.searchParams.current,
          pageSize: this.searchParams.pageSize
        })
      }).then(r => r.json())
          .then(data => {
            this.total = data.data.total
            this.categoryList = data.data.list
            this.tableDataLoading = false
          })
          .catch(err => console.log(err))
    },
    deleteCategory(id) {
      fetch("http://127.0.0.1:8080/api/category/delete/" + id, {
        method: 'delete',
        headers: {'Content-Type': 'application/json'},
      }).then(r => r.json())
          .then(data => {
            this.operationSuccess(data.message)
          })
          .catch(err => console.log(err))
    },
    addCategory() {
      fetch("http://127.0.0.1:8080/api/category/add", {
        method: 'post',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
          name: this.addOrUpdateData.name,
          description: this.addOrUpdateData.description
        })
      }).then(r => r.json())
          .then(data => {
            this.operationSuccess(data.message)
          })
          .catch(err => console.log(err))
    },
    updateCategory(index) {
      fetch("http://127.0.0.1:8080/api/category/update", {
        method: 'post',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
          id: index,
          name: this.addOrUpdateData.name,
          description: this.addOrUpdateData.description
        })
      }).then(r => r.json())
          .then(data => {
            this.operationSuccess(data.message)
          })
          .catch(err => console.log(err))
    }
  }
}
</script>

<style scoped>
.app-container {
  padding: 24px;
}
</style>
