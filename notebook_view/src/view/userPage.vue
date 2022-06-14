<template>
  <v-app>
    <ToolBar></ToolBar>
    <user-ticket/>
  </v-app>
</template>

<script>
import ToolBar from "@/components/ToolBar";
import UserTicket from "@/components/userTicket";
export default {
  name: "userPage",
  components: {UserTicket, ToolBar}
}
</script>

<style scoped>

</style>