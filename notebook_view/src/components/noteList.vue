<template>
  <v-container>
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
              color="primary"
              @click="statusRecycleToSaved(item)"
          >
            还原
          </v-btn>
          <v-btn
              v-else
              small
              color="primary"
          >
            编辑
          </v-btn>
          <v-btn
              color="success"
              small
              @click="update(item)"
          >
            设置
          </v-btn>
          <v-btn
              color="error"
              small
              @click="deleteNote(item.id)"
          >
            删除
          </v-btn>
        </template>
      </v-data-table>
    </template>
  </v-container>
</template>

<script>

export default {
  name: "noteList",
  data: () => ({
    noteSelect: {
      current: 1,
      pageSize: 10,
      title: null,
      categoryId: null,
      status: null
    },
    message: '',
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
      const tagType = ['info', 'success', 'warning', 'error']
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
    }
  },
  mounted() {
    this.data()
  }
}
</script>
