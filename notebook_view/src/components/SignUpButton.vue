<template>
  <v-dialog
      v-model="dialog"
      dark
      max-width="600px"
      persistent
  >
    <template v-slot:activator="{ on, attrs }">
      <v-btn
          v-bind="attrs"
          v-on="on">
        Sign Up
      </v-btn>
    </template>
    <v-card>
      <v-card-text>
        <v-container>
          <v-row>
            <v-col
                sm="10"
            >
              <v-text-field
                  v-model="accountInput"
                  label="account"
                  prepend-icon="mdi-account"
              >
              </v-text-field>
            </v-col>
            <v-col
                sm="10"
            >
              <v-text-field
                  v-model="passwordInput"
                  label="password"
                  prepend-icon="mdi-lock"
              >
              </v-text-field>
            </v-col>
          </v-row>
        </v-container>
      </v-card-text>
      <v-card-actions>
        <v-btn
            text
            @click="dialog = false">
          Close
        </v-btn>
        <v-spacer></v-spacer>
        <v-btn
            text
            @click="verifyUser()"
        >
          Sure
        </v-btn>
      </v-card-actions>
      <v-alert
          v-model="accountExist"
          dismissible
          elevation="3"
      >账号已存在,请勿重新注册
      </v-alert>
      <v-alert
          v-model="accountNotNull"
          dismissible
          elevation="3"
      >账号,密码不能为空
      </v-alert>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  name: "SignUpButton",
  data: () => ({
    accountExist: false,
    accountNotNull: false,
    dialog: false,
    accountInput: "",
    passwordInput: "",
  }),
  methods: {
    addUser() {
      this.dialog = false
      let url = "http://127.0.0.1:8080/addUser/" + this.accountInput;
      fetch(url, {
        method: 'post',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
          account: this.accountInput,
          password: this.passwordInput,
        })
      }).then().catch(e => console.log(e))
    },
    verifyUser() {
      if (this.accountInput === "" || this.passwordInput === "") {
        this.accountNotNull = true;
      } else {
        let url = "http://127.0.0.1:8080/searchUser/" + this.accountInput;
        fetch(url, {
          method: 'post',
          headers: {'Content-Type': 'application/json'},
        })
            .then(r => r.text())
            .then(data => {
              if (data === 'true' || data === 'false') {
                this.accountExist = true;
              } else if (data === "AccountMissed") {
                this.addUser();
              }
            })
            .catch(e => console.log(e))
      }
    }
  }
}
</script>
