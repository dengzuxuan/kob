<template>
  <div class="result-borad">
    <div class="result-board-text"
         v-if="$store.state.pk.loser==='all'">
      打 平
    </div>
    <div class="result-board-text"
         v-else-if="$store.state.pk.loser==='A' && $store.state.pk.a_id==$store.state.user.id">
      失 败
    </div>
    <div class="result-board-text"
         v-else-if="$store.state.pk.loser==='B' && $store.state.pk.b_id==$store.state.user.id">
      失 败
    </div>
    <div class="result-board-text"
         v-else>
      胜 利
    </div>
    <div class="result-board-btn">
      <button type="button"
              class="btn btn-warning btn-lg"
              @click="restart">再来一把</button>
    </div>
  </div>
</template>

<script>
import { useStore } from 'vuex'
export default {
  setup() {
    const store = useStore()
    const restart = () => {
      store.commit('updateStatus', 'matching')
      store.commit('updateLoser', 'none')
      store.commit('updateOpponent', {
        username: '你的对手',
        photo:
          'https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png',
      })
    }
    return {
      restart,
    }
  },
}
</script>
<style scoped>
div.result-borad {
  height: 30vh;
  width: 30vw;
  background-color: rgba(50, 50, 50, 0.5);
  position: absolute;
  top: 30vh;
  left: 35vw;
}
div.result-board-text {
  text-align: center;
  color: white;
  font-size: 50px;
  font-weight: 600;
  font-style: italic;
  padding-top: 5vh;
}
div.result-board-btn {
  padding-top: 7vh;
  text-align: center;
}
</style>