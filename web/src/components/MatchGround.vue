<template>
  <div class="matchground">
    <div class="row">
      <div class="col-4">
        <div class="user-photo">
          <img :src="$store.state.user.photo"
               alt="">
        </div>
        <div class="user-username">
          {{ $store.state.user.username }}
        </div>
      </div>
      <div class="col-4">
        <div class="user-select-bot">
          <select v-model="select_bot"
                  class="form-select"
                  aria-label="Default select example">
            <option selected
                    :value="-1">亲自出马</option>
            <option v-for="bot in bots"
                    :value="bot.id"
                    :key="bot.id">{{bot.title}}</option>
          </select>
        </div>
      </div>
      <div class="col-4">
        <div class="user-photo">
          <img :src="$store.state.pk.opponent_photo"
               alt="">
        </div>
        <div class="user-username">
          {{ $store.state.pk.opponent_username }}
        </div>
      </div>
      <div class="col-12"
           style="text-align: center;padding-top: 15vh;">
        <button type="button"
                class="btn btn-warning btn-lg"
                @click="click_match_btn">{{match_btn_info}}</button>
      </div>
    </div>
  </div>
</template>

<script >
import $ from 'jquery'
import { ref } from 'vue'
import { useStore } from 'vuex'
export default {
  setup() {
    let bots = ref([])
    const store = useStore()
    let select_bot = ref(-1)
    let match_btn_info = ref('开始匹配')
    const click_match_btn = () => {
      if (match_btn_info.value === '开始匹配') {
        match_btn_info.value = '取消匹配'
        store.state.pk.socket.send(
          JSON.stringify({
            event: 'start-matching',
            bot_id: select_bot.value,
          })
        )
      } else {
        match_btn_info.value = '开始匹配'
        store.state.pk.socket.send(
          JSON.stringify({
            event: 'stop-matching',
          })
        )
      }
    }
    const refresh_bots = () => {
      $.ajax({
        url: 'https://app6040.acapp.acwing.com.cn/api/user/bot/getlist/',
        type: 'get',
        headers: {
          Authorization: 'Bearer ' + store.state.user.token,
        },
        success(resp) {
          bots.value = resp
        },
        error(resp) {
          console.log('error', resp)
        },
      })
    }

    refresh_bots() //从云端动态获取bots

    return {
      match_btn_info,
      click_match_btn,
      bots,
      refresh_bots,
      select_bot,
    }
  },
}
</script>

<style scoped>
div.user-select-bot {
  padding-top: 60px;
}

div.user-select-bot > select {
  width: 60%;
  margin: 0 auto;
}

div.matchground {
  width: 60vw;
  height: 70vh;
  margin: 40px auto;
  background-color: rgba(50, 50, 50, 0.5);
}
div.user-photo {
  text-align: center;
  margin-top: 10vh;
}
div.user-photo > img {
  border-radius: 50%;
  width: 20vh;
}
div.user-username {
  text-align: center;
  font-size: 24px;
  font-weight: 600;
  color: white;
  padding-top: 3vh;
}
</style>