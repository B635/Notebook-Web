<template>
  <v-app>
    <LoginCard></LoginCard>
  </v-app>
</template>

<script>
import LoginCard from "../components/LoginCard"

export default {
  name: "Login",
  components: {
    LoginCard,
  },
}
</script>