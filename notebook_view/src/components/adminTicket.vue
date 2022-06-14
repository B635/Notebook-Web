<template>
  <v-container>
    <v-card>
      <v-card-text>
        <v-row>
          <v-text-field
              v-model="beginStation"
              label="起始站">

          </v-text-field>
          <v-text-field
              label="终点站"
              v-model="endStation"
              class="pr-10"
          >
            <v-icon
                slot="prepend"
                color="green">
              mdi-minus
            </v-icon>
          </v-text-field>

          <v-btn
              color="blue"
              large
              @click="searchTrain()"
          >
            搜索
          </v-btn>
          <v-btn
              style="margin-left: 10px"
              color="blue"
              large
              @click="addTrain()"
          >
            添加路线
          </v-btn>
        </v-row>
      </v-card-text>
    </v-card>
    <template>
      <v-dialog
          v-model="addDialog"
          max-width="500px"
      >
        <v-card>
          <v-card-title>
            <span class="text-h5">新增路线</span>
          </v-card-title>

          <v-card-text>
            <v-container>
              <v-row>
                <v-col
                    cols="12"
                    sm="6"
                    md="4"
                >
                  <v-text-field
                      v-model="trainInformation.trainNo"
                      label="车次"
                  ></v-text-field>
                </v-col>
                <v-col
                    cols="12"
                    sm="6"
                    md="4"
                >
                  <v-text-field
                      v-model="trainInformation.beginStation"
                      label="起始站"
                  ></v-text-field>
                </v-col>
                <v-col
                    cols="12"
                    sm="6"
                    md="4"
                >
                  <v-text-field
                      v-model="trainInformation.endStation"
                      label="终点站"
                  ></v-text-field>
                </v-col>
                <v-col
                    cols="12"
                    sm="6"
                    md="4"
                >
                  <v-text-field
                      hint="yyyy-MM-dd HH:mm"
                      v-model="trainInformation.startTime"
                      label="出发时间"
                  ></v-text-field>
                </v-col>
                <v-col
                    cols="12"
                    sm="6"
                    md="4"
                >
                  <v-text-field
                      hint="yyyy-MM-dd HH:mm"
                      v-model="trainInformation.finishTime"
                      label="到达时间"
                  ></v-text-field>
                </v-col>
                <v-col
                    cols="12"
                    sm="6"
                    md="4"
                >
                  <v-text-field
                      v-model="trainInformation.hardSeat"
                      label="硬座"
                  ></v-text-field>
                </v-col>
                <v-col
                    cols="12"
                    sm="6"
                    md="4"
                >
                  <v-text-field
                      v-model="trainInformation.softSleeper"
                      label="软卧"
                  ></v-text-field>
                </v-col>
                <v-col
                    cols="12"
                    sm="6"
                    md="4"
                >
                  <v-text-field
                      v-model="trainInformation.notSeat"
                      label="无座"
                  ></v-text-field>
                </v-col>
              </v-row>
            </v-container>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                color="blue darken-1"
                text
                @click="close()"
            >
              Cancel
            </v-btn>
            <v-btn
                color="blue darken-1"
                text
                @click="add()"
            >
              Sure
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </template>
    <template class="pa-10">
      <v-data-table
          disable-pagination
          hide-default-footer
          :headers="headers"
          :items="contents"
          class="font-weight-black text-h3"
      >
        <template v-slot:top>
          <v-dialog
              v-model="dialog"
              max-width="500px"
          >
            <v-card>
              <v-card-title>
                <span class="text-h5">延误修改</span>
              </v-card-title>

              <v-card-text>
                <v-container>
                  <v-row>
                    <v-col
                        cols="12"
                        sm="6"
                        md="4"
                    >
                      <v-text-field
                          disabled
                          v-model="train.trainNo"
                          label="车次"
                      ></v-text-field>
                    </v-col>
                    <v-col
                        cols="12"
                        sm="6"
                        md="4"
                    >
                      <v-text-field
                          disabled
                          v-model="train.beginStation"
                          label="起始站"
                      ></v-text-field>
                    </v-col>
                    <v-col
                        cols="12"
                        sm="6"
                        md="4"
                    >
                      <v-text-field
                          disabled
                          v-model="train.endStation"
                          label="终点站"
                      ></v-text-field>
                    </v-col>
                    <v-col
                        cols="12"
                        sm="6"
                        md="4"
                    >
                      <v-text-field
                          v-model="train.startTime"
                          label="出发时间"
                      ></v-text-field>
                    </v-col>
                    <v-col
                        cols="12"
                        sm="6"
                        md="4"
                    >
                      <v-text-field
                          v-model="train.finishTime"
                          label="到达时间"
                      ></v-text-field>
                    </v-col>
                  </v-row>
                </v-container>
              </v-card-text>

              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                    color="blue darken-1"
                    text
                    @click="close()"
                >
                  Cancel
                </v-btn>
                <v-btn
                    color="blue darken-1"
                    text
                    @click="save"
                >
                  Sure
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </template>
        <template v-slot:item.actions="{ item }">
          <v-btn
              color="orange"
              small
              @click="delayTicket(item)"
          >
            延误
          </v-btn>
        </template>
        <template v-slot:item.del="{ item }">
          <v-btn
              color="orange"
              small
              @click="deleteTicket(item)"
          >
            删除
          </v-btn>
        </template>
      </v-data-table>
    </template>
  </v-container>
</template>

<script>
export default {
  name: "adminTicket",
  data: () => ({
    addDialog: false,
    dialog: false,
    beginStation: '',
    endStation: '',
    train: {
      trainNo: '',
      beginStation: '',
      endStation: '',
      startTime: '',
      finishTime: '',
    },
    trainInformation: {
      trainNo: '',
      beginStation: '',
      endStation: '',
      startTime: '',
      finishTime: '',
      hardSeat: '',
      softSleeper: '',
      notSeat: '',
    },
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
        text: '硬座',
        value: 'hardSeat'
      },
      {
        text: '软卧',
        value: 'softSleeper'
      },
      {
        text: '无座',
        value: 'notSeat'
      },
      {
        text: '备注',
        value: 'actions'
      },
      {
        text: ' ',
        value: 'del'
      }
    ],
    seats:[
      {
        text: '硬座',
        value: 'hardSeat'
      },
      {
        text: '软卧',
        value: 'softSleeper'
      },
      {
        text: '无座',
        value: 'notSeat'
      },
    ],
    contents: []
  }),
  methods: {
    data() {
      fetch("http://127.0.0.1:8080/showTrain", {credentials: 'include'})
          .then(r => r.json()
          )
          .then(data => this.contents = data)
          .catch(err => console.log(err))
    },
    delayTicket(item) {
      this.train = Object.assign({}, item)
      this.dialog=true
    },
    close() {
      this.dialog = false
      this.addDialog = false
    },
    save() {
      fetch("http://127.0.0.1:8080/changeTicket", {
        method: 'post',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
          trainNo: this.train.trainNo,
          startTime: this.train.startTime,
          finishTime: this.train.finishTime
        })
      }).then()
          .catch(err => console.log(err))
      this.dialog = false;
      window.location.reload();
    },
    deleteTicket(item) {
      this.train = Object.assign({}, item)
      fetch("http://127.0.0.1:8080/deleteTrain", {
        method: 'post',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
          trainNo: this.train.trainNo,
        })
      }).then()
          .catch(err => console.log(err))
      window.location.reload();
    },
    searchTrain() {
      fetch("http://127.0.0.1:8080/searchTrain", {
        method: 'post',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
          beginStation: this.beginStation,
          endStation: this.endStation
        })
      }).then(r => r.json())
          .then (data => this.contents = data)
          .catch(err => console.log(err))
    },
    addTrain() {
      this.addDialog = true
    },
    add() {
      fetch("http://127.0.0.1:8080/addTrain", {
        method: 'post',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
          trainNo: this.trainInformation.trainNo,
          beginStation: this.trainInformation.beginStation,
          endStation: this.trainInformation.endStation,
          startTime: this.trainInformation.startTime,
          finishTime: this.trainInformation.finishTime,
          hardSeat: this.trainInformation.hardSeat,
          softSleeper: this.trainInformation.softSleeper,
          notSeat: this.trainInformation.notSeat,
        })
      }).then()
          .catch(err => console.log(err))
      this.addDialog = false;
      window.location.reload();
    }
  },
  mounted() {
    this.data()
  }
}
</script>

<style scoped>

</style>