<template>
  <v-app>
    <ToolBar></ToolBar>
    <tag-list ></tag-list>
  </v-app>
</template>

<script>
import tagList from "@/components/tagList";
import ToolBar from "@/components/ToolBar";
export default {
  name: "tag",
  components: {ToolBar, tagList}
}
</script>

<style scoped>

</style>
