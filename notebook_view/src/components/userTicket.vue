<template>
  <v-data-table
      disable-pagination
      hide-default-footer
      :headers="headers"
      :items="contents"
      class="font-weight-black text-h3"
  >
    <template v-slot:item.actions="{ item }">
      <v-btn
          color="orange"
          small
          @click="cancelTicket(item)"
      >
        取消
      </v-btn>
    </template>
  </v-data-table>
</template>

<script>
export default {
  name: "userTicket",
  data: () => ({
    headers: [
      {
        text: '车次',
        value: 'trainNo'
      },
      {
        text: '起始站',
        value: 'beginStation'
      },
      {
        text: '终点站',
        value: 'endStation'
      },
      {
        text: '出发时间',
        value: 'startTime'
      },
      {
        text: '到达时间',
        value: 'finishTime'
      },
      {
        text: '座位',
        value: 'seatClass'
      },
      {
        text: '备注',
        value: 'actions'
      }
    ],
    train: {
      trainNo: '',
      beginStation: '',
      endStation: '',
      startTime: '',
      finishTime: '',
      seatClass: '',
    },
    contents: []
  }),
  methods: {
    data() {
      let ls = window.localStorage;
      let user_account = ls.getItem('account');
      fetch("http://127.0.0.1:8080/searchTicket", {
            method: 'post',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({
              account: user_account,
            })
      }).then(r => r.json())
          .then(data => this.contents = data)
          .catch(err => console.log(err))
    },
    cancelTicket(item) {
      let ls = window.localStorage;
      let user_account = ls.getItem('account');
      this.train = Object.assign({}, item)
      fetch("http://127.0.0.1:8080/deleteTicket", {
        method: 'post',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
          account: user_account,
          trainNo: this.train.trainNo,
        })
      }).then()
          .catch(err => console.log(err))
      window.location.reload();
    },
    save() {
      let ls = window.localStorage;
      let user_account = ls.getItem('account');
      fetch("http://127.0.0.1:8080/addTicket", {
        method: 'post',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
          account: user_account,
          trainNo: this.train.trainNo,
          seatClass: this.select,
        })
      }).then()
          .catch(err => console.log(err))
      this.dialog = false
    }
  },
  mounted() {
    this.data()
  }
}
</script>

<style scoped>

</style>