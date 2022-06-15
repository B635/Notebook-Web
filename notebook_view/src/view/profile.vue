<template>
  <v-app>
    <ToolBar></ToolBar>
    <note-list ></note-list>
  </v-app>
</template>

<script>
import noteList from "@/components/noteList";
import ToolBar from "@/components/ToolBar";
export default {
  name: "profile",
  components: {ToolBar, noteList}
}
</script>

<style scoped>

</style>
