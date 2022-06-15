<template>
  <v-list >
    <v-list-item >
      <v-list-item-content>
        <v-col>
          <v-list-item-title >
            笔记标题：
          </v-list-item-title>
        </v-col>
        <v-col>
        <v-text-field
            outlined
            dense
            placeholder="请输入笔记标题"
            v-model="noteData.title"
        >
        </v-text-field>
        </v-col>

      </v-list-item-content>
    </v-list-item>
    <v-list-item >
      <v-list-item-content>
        <v-col>
          <v-list-item-title >
            笔记分类：
          </v-list-item-title>
        </v-col>
        <v-col>
          <v-select
              placeholder="请选择笔记分类"
              v-model="noteData.category.id"
              :items="usedCategoryList"
              :label="noteData.category.name"
              item-text="name"
              item-value="id"
          >
          </v-select>
        </v-col>
      </v-list-item-content>
    </v-list-item>
    <v-list-item >
      <v-list-item-content>
        <v-col>
          <v-list-item-title >
            笔记标签：
          </v-list-item-title>
        </v-col>
        <v-col>
          <v-combobox
              placeholder="请选择笔记标签"
              v-model="selectedTagList"
              :items="tagList"
              item-value="id"
              item-text="name"
              solo
              multiple
              chips
              clearable
          >
          </v-combobox>
        </v-col>
      </v-list-item-content>
    </v-list-item>
    <v-list-item >
      <v-list-item-content>
        <v-col>
          <v-list-item-title >
            笔记摘要：
          </v-list-item-title>
        </v-col>
        <v-col>
          <v-textarea
              v-model="noteData.summary"
              color="teal"
              placeholder="请输入笔记简介"
          >
          </v-textarea>
        </v-col>
      </v-list-item-content>
    </v-list-item>
  </v-list>
</template>

<script>
import {deepClone} from "@/utils";

export default {
  name: "sittingDraw",
  data() {
    return {
      drawer: false,
      noteData: {
        id: null,
        title: '',
        summary: '',
        category: {id: null, name: ''},
        label: [],
      },
      usedCategoryList: [],
      tagList: [],
      selectedTagList: []
    }
  },
  methods: {
    setData(data) {
      this.noteData = deepClone(data)
      if (!this.noteData.category) {
        this.noteData.category = { id: null, name: '' }
      }
      this.selectedTagList = []
      for (const tag of this.noteData.label) {
        this.selectedTagList.push(tag)
      }
    },

    getData() {
      this.noteData.label = []
      for (const tags of this.selectedTagList) {
        let isFound = false
        for (const tag of this.tagList) {
          if (tags.id === tag.id) {
            isFound = true
            this.noteData.label.push({ id: tags.id })
            break
          }
        }
        if (!isFound) {
          this.noteData.label.push({ name: tags.id })
        }
      }
      return {
        drawer: false,
        noteData: {
          id: this.noteData.id,
          title: this.noteData.title,
          summary: this.noteData.summary,
          category: this.noteData.category,
          label: this.noteData.label,
        }
      }
    },

    listAllCategory() {
      fetch("http://127.0.0.1:8080/api/category/list/all", {credentials: 'include'})
          .then(r => r.json()
          )
          .then(data => this.usedCategoryList = data.data)
          .catch(err => console.log(err))
    },

    listAllTags() {
      fetch("http://127.0.0.1:8080/api/tag/list", {credentials: 'include'})
          .then(r => r.json()
          )
          .then(data => this.tagList = data.data)
          .catch(err => console.log(err))
    }
  },
  created() {
    this.listAllTags()
    this.listAllCategory()
  }
}
</script>

