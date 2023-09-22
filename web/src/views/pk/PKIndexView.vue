<template>
  <PlayGround v-if="$store.state.pk.status==='playing'" />
  <MatchGround v-if="$store.state.pk.status==='matching'" />
  <ResultBoard v-if="$store.state.pk.loser!='none'" />
</template>

<script>
import ResultBoard from '@/components/ResultBoard.vue'
import { onMounted, onUnmounted } from 'vue'
import { useStore } from 'vuex'
import MatchGround from '../../components/MatchGround.vue'
import PlayGround from '../../components/PlayGround.vue'
export default {
  components: {
    PlayGround,
    MatchGround,
    ResultBoard,
  },
  setup() {
    const store = useStore()
    const socketUrl = `ws://127.0.0.1:3000/websocket/${store.state.user.token}/`
    store.commit('updateLoser', 'none')
    let socket = null
    socket = new WebSocket(socketUrl)

    socket.onopen = () => {
      store.commit('updateSocket', socket)
      console.log('connect!')
    }

    socket.onmessage = (msg) => {
      const data = JSON.parse(msg.data)
      if (data.event === 'start-matching') {
        //匹配成功
        store.commit('updateOpponent', {
          username: data.opponent_username,
          photo: data.opponent_photo,
        })
        setTimeout(() => {
          store.commit('updateStatus', 'playing')
        }, 200)
        store.commit('updateGame', data.game)
      } else if (data.event === 'move') {
        const game = store.state.pk.gameObject
        const [snake0, snake1] = game.snakes
        snake0.set_direction(data.a_direction)
        snake1.set_direction(data.b_direction)
      } else if (data.event === 'result') {
        const game = store.state.pk.gameObject
        const [snake0, snake1] = game.snakes

        if (data.loser === 'all' || data.loser === 'A') {
          snake0.status = 'die'
        }

        if (data.loser === 'all' || data.loser === 'B') {
          snake1.status = 'die'
        }
        store.commit('updateLoser', data.loser)
      }
    }

    socket.onclose = () => {
      console.log('disconnect!')
    }
    onMounted(() => {
      store.commit('updateOpponent', {
        username: '你的对手',
        photo:
          'https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png',
      })
    })

    onUnmounted(() => {
      store.commit('updateStatus', 'matching')
      socket.close()
    })
  },
}
</script>

<style scoped>
</style>