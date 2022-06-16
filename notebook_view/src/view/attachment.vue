<template>
  <v-app>
    <ToolBar></ToolBar>
    <attachmentController></attachmentController>
  </v-app>
</template>

<script>
import ToolBar from "@/components/ToolBar";
import attachmentController from "@/components/attachmentController"
export default {
  name: "attachment",
  components: {ToolBar,attachmentController}
}
</script>

<style scoped>

</style>
