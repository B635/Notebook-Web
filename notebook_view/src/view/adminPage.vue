<template>
  <v-app>
    <ToolBar>
    </ToolBar>
    <admin-ticket/>
  </v-app>
</template>

<script>
import ToolBar from "@/components/ToolBar";
import AdminTicket from "@/components/adminTicket";
export default {
  name: "adminPage",
  components: {AdminTicket, ToolBar}
}
</script>

<style scoped>

</style>