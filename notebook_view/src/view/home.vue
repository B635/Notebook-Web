<template>
  <v-app>
    <ToolBar></ToolBar>
    <home-message ></home-message>
  </v-app>
</template>

<script>
import homeMessage from "@/components/homeMessage";
import ToolBar from "@/components/ToolBar";
export default {
  name: "home",
  components: {ToolBar, homeMessage}
}
</script>

<style scoped>

</style>
