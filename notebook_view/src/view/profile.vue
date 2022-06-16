<template>
  <v-app>
    <ToolBar></ToolBar>
    <profile-message ></profile-message>
  </v-app>
</template>

<script>
import profileMessage from "@/components/profileMesssage";
import ToolBar from "@/components/ToolBar";
export default {
  name: "profile",
  components: {ToolBar, profileMessage}
}
</script>

<style scoped>

</style>
