<template>
  <v-app>
    <ToolBar></ToolBar>
    <category-list ></category-list>
  </v-app>
</template>

<script>
import categoryList from "@/components/categoryList";
import ToolBar from "@/components/ToolBar";
export default {
  name: "category",
  components: {ToolBar, categoryList}
}
</script>

<style scoped>

</style>
