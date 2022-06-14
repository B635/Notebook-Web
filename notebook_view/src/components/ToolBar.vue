<template>
  <v-container>
    <v-app-bar
        app
        clipped-left
        color="blue"
    >
      <v-app-bar-nav-icon
          @click.stop="drawer = !drawer"
      ></v-app-bar-nav-icon>

      <v-toolbar-title>Fast Ticket</v-toolbar-title>

      <v-spacer></v-spacer>

      <v-btn icon>
        <v-icon>mdi-train</v-icon>
      </v-btn>
    </v-app-bar>

    <v-navigation-drawer
        v-model="drawer"
        app
        clipped
        color="#ECEFF1"
    >
      <v-list dense>
        <v-list-item @click="verifyHome()">
          <v-list-item-icon>
            <v-icon>mdi-view-dashboard</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>车票信息</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item @click="verifyUser()">
          <v-list-item-icon>
            <v-icon>mdi-ticket</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>已预约车票信息</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item @click="verifyAdmin()">
          <v-list-item-icon>
            <v-icon>mdi-account-cog</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title>管理员模式</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item @click="logOut()">
          <v-list-item-icon>
            <v-icon>mdi-account-cancel</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>登出</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
    <v-alert
        v-model="alert"
        color="red"
        dense
        dismissible
        elevation="3"
        icon="mdi-account"
        type="info"
    >没有权限
    </v-alert>
  </v-container>
</template>

<script>
export default {
  name: "ToolBar",
  data: () => ({
    drawer: false,
    alert: false,
  }),
  methods: {
    verifyAdmin() {
      let ls = window.localStorage;
      let is_admin = ls.getItem('is_admin');
      if (is_admin === 'true') {
        this.$router.replace("/admin");
      } else {
        this.alert = true;
      }
    },
    verifyUser() {
      let ls = window.localStorage;
      let is_admin = ls.getItem('is_admin');
      if (is_admin === 'true' || is_admin === 'false') {
        this.$router.replace("/user");
      } else {
        this.$router.replace("/")
      }
    },
    verifyHome() {
      let ls = window.localStorage;
      let is_admin = ls.getItem('is_admin');
      if (is_admin === 'true' || is_admin === 'false') {
        this.$router.replace("/home");
      } else {
        this.$router.replace("/")
      }
    },
    logOut() {
      this.$router.replace("/")
    }
  }
}
</script>
