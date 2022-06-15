<template>
  <v-app>
    <ToolBar></ToolBar>
    <Markdown/>
  </v-app>
</template>

<script>
import Markdown from "@/components/Markdown";
import ToolBar from "@/components/ToolBar";

export default {
  name: "writePage",
  components: {Markdown, ToolBar}
}
</script>

<style scoped>

</style>
