<template>
  <v-container>
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
    headers: [
      {
        text: '标题',
        value: 'title'
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
        text: '状态',
        value: 'status'
      },
      {
        text: '日期',
        value: 'date'
      }
    ],
    noteList: [],
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
          type: 'danger'
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
