<template>
  <v-card class="overflow-hidden">
    <v-app-bar
        dark
        hide-on-scroll
        clipped-left
        class="black--text"
        color="#FBC2A6"
        scroll-target="#scrolling-techniques-4"
    >
      <v-app-bar-nav-icon
          @click.stop="drawer = !drawer"
      ></v-app-bar-nav-icon>

      <v-toolbar-title>My notebook</v-toolbar-title>

      <v-spacer></v-spacer>

      <v-menu
          offset-y
          open-on-hover
      >
        <template v-slot:activator="{ on, attrs }">
          <v-btn
              dark
              icon
              v-bind="attrs"
              v-on="on"
          >
            <v-icon>mdi-kite</v-icon>
          </v-btn>
        </template>
            <v-btn
                color="#C7A4C4"
                @click="logOut"
                style="text-align: center">登出</v-btn>
      </v-menu>
    </v-app-bar>

    <v-navigation-drawer
        v-model="drawer"
        app
        clipped
        color="#8AB6C9"
    >
      <v-list dense>
        <v-list-item @click="home">
          <v-list-item-icon>
            <v-icon>mdi-view-compact</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>首页</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-group
          :value="true"
          prepend-icon="mdi-notebook"
          >
          <template v-slot:activator>
            <v-list-item-title>笔记</v-list-item-title>
          </template>
            <v-list-item
              v-for="([title, icon, link], i) in note"
              :key="i"
              link
              :to="link"
            >
              <v-list-item-title v-text="title"></v-list-item-title>
              <v-list-item-icon>
                <v-icon v-text="icon"></v-icon>
              </v-list-item-icon>
            </v-list-item>
        </v-list-group>
        <v-list-item @click="attachment">
          <v-list-item-icon>
            <v-icon>mdi-folder-upload</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>附件</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item @click="profile">
          <v-list-item-icon>
            <v-icon>mdi-account</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>个人信息</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
  </v-card>
</template>

<script>
export default {
  name: "ToolBar",
  data: () => ({
    drawer: true,
    note: [
        ['所有笔记', 'mdi-note', '/note/list'],
      ['写笔记', 'mdi-note-edit', '/note/write'],
      ['分类', 'mdi-view-dashboard', '/note/category'],
      ['标签', 'mdi-label', '/note/tag'],
    ]
  }),
  methods: {
    home() {
        this.$router.replace("/home")
    },
    attachment() {
      this.$router.replace("/attachment")
    },
    profile() {
      this.$router.replace("/profile")
    },
    logOut() {
      this.$router.replace("/")
    }
  }
}
</script>
