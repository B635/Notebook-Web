<template>
  <div class="app-container">
    <v-snackbar
        :timeout = "timeout"
        v-model="alert"
    >
      {{ message }}
    </v-snackbar>
    <v-list v-model="noteData">
        <v-text-field
            outlined
            dense
            class="shrink"
            placeholder="请输入笔记标题"
            v-model="noteData.title"
        > </v-text-field>
    </v-list>
    <mavon-editor
        ref="editor"
        v-model="noteData.content"
        class="editor"
        :ishljs="true"
        @change="editing"
        @save="saveDraft"
        @fullScreen="editorFullscreen"
        @helpToggle="editorNotehelpToggle"
    />
    <div class="footer">
      <v-btn
          color="#8AB6C9"
          @click="saveDraft"
          style="margin-right: 10px"
      >保存草稿</v-btn>
      <v-btn color="#FFE0E5" @click="openSavedDrawer"
      >发 表</v-btn>
    </div>

    <v-navigation-drawer
        right
        absolute
        temporary

        width="500"
        v-model="saveDrawer"
    >
      <v-list-item>
        笔记设置
      </v-list-item>
      <v-sitting-drawer ref="saveDrawer">
      </v-sitting-drawer>
      <div class="footer-toolbar" style="width: 30%">
        <v-btn
            color="#FFE0E5"
            right
            @click="addNote"
        >
          发表
        </v-btn>
      </div>
    </v-navigation-drawer>
  </div>
</template>

<script>
import {deepClone} from "@/utils";

export default {
  name: "Markdown",
  components: {
    'v-sitting-drawer': () => import('@/components/sittingDraw')
  },
  data() {
    return {
      saveDrawer: false,
      timeout: 1500,
      message: '',
      alert: false,
      noteData: {
        content: '',
        html:'',
        title: '',
        summary: '',
        category: { id: null, name: '' },
        tags: [],
      }
    }
  },
  computed: {
    isUpdate: function () {
      return Reflect.apply(Object.prototype.toString, this.noteData, []) === '[object Object]' && this.noteData.id
    }
  },
  created() {
    this.getNote()
  },
  methods: {
    editing(value, render) {
      this.noteData.content = render
    },
    openSavedDrawer() {
      this.saveDrawer = true
      setTimeout(() => {
        this.$refs.saveDrawer.setData(this.noteData)
      }, 200)
    },
    editorFullscreen(status) {
      const e = document.querySelector('.editor')
      if (e) {
        e.style['z-index'] = status ? 9999 : 1500
      }
    },
    editorNotehelpToggle(status) {
      setTimeout(function() {
        const e = document.querySelector('.v-note-help-wrapper')
        if (e) {
          e.style['z-index'] = status ? 9999 : 1600
        }
      }, 36)
    },
    // 获取博客
    getNote() {
      const noteId = this.$route.query.noteId
      if (noteId) {
        fetch("http://127.0.0.1:8080/api/note/get/" + noteId, {credentials: 'include'})
            .then(r => r.json()
            )
            .then(data => {
              this.noteData = data.data
              this.noteData.id = noteId
            })
            .catch(err => console.log(err))
      }

    },
    addNote() {
      const noteData = this.$refs.saveDrawer.getData()
      console.log(noteData)
      if (this.isUpdate) {
        noteData.status = 'SAVED'
        fetch("http://127.0.0.1:8080/api/note/update", {
          method: 'post',
          headers: {'Content-Type': 'application/json'},
          body: JSON.stringify({
            id: noteData.id,
            title: noteData.title,
            status: noteData.status,
            summary: noteData.summary,
            category: noteData.category,
            label: noteData.tags,
            content: noteData.content,
          })
        }).then(r => r.json())
            .then(data => {
              this.message = data.message
              this.alert = true
              this.$router.replace("/note/list")
            })
            .catch(err => console.log(err))
      } else {
        fetch("http://127.0.0.1:8080/api/note/add", {
          method: 'post',
          headers: {'Content-Type': 'application/json'},
          body: JSON.stringify({
            title: noteData.title,
            summary: noteData.summary,
            status: noteData.status,
            category: noteData.category,
            label: noteData.tags,
            content: noteData.content,
          })
        }).then(r => r.json())
            .then(data => {
              this.message = data.message
              this.alert = true
              this.$router.replace("/note/list")
            })
            .catch(err => console.log(err))
      }
    },
    saveDraft() {
      const noteData = deepClone(this.noteData)
      noteData.status = 'DRAFT'
      if (this.isUpdate) {
        fetch("http://127.0.0.1:8080/api/note/update", {
          method: 'post',
          headers: {'Content-Type': 'application/json'},
          body: JSON.stringify({
            id: noteData.id,
            title: noteData.title,
            status: noteData.status,
            summary: noteData.summary,
            category: noteData.category,
            label: noteData.tags,
            content: noteData.content,
          })
        }).then(r => r.json())
            .then(data => {
              this.message = data.message
              this.alert = true
              this.$router.replace("/note/list")
            })
            .catch(err => console.log(err))
      } else {
        fetch("http://127.0.0.1:8080/api/note/add", {
          method: 'post',
          headers: {'Content-Type': 'application/json'},
          body: JSON.stringify({
            title: noteData.title,
            summary: noteData.summary,
            status: noteData.status,
            category: noteData.category,
            label: noteData.tags,
            content: noteData.content,
          })
        }).then(r => r.json())
            .then(data => {
              this.message = data.message
              this.alert = true
              this.$router.replace("/note/list")
            })
            .catch(err => console.log(err))
      }
    }
  }
}
</script>

<style scoped>
.app-container {
  padding: 24px;
}
.editor {
  min-height: 75vh;
  z-index: 1;
}


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

.footer {
  transition: all .3s;
  z-index: 1;
  position: fixed;
  bottom: 0;
  right: 0;
  height: 56px;
  line-height: 56px;
  padding: 0 24px;
  background-color: #fff;
  border-top: 1px #e8e8e8 solid;
  box-shadow: 0 -1px 2px rgba(0, 0, 0, .03);
  text-align: center;
}
</style>
