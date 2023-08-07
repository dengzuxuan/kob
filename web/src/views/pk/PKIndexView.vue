<template>
  <PlayGround v-if="$store.state.pk.status==='playing'" />
  <MatchGround v-if="$store.state.pk.status==='matching'" />

</template>

<script>
import { onMounted, onUnmounted } from 'vue'
import { useStore } from 'vuex'
import MatchGround from '../../components/MatchGround.vue'
import PlayGround from '../../components/PlayGround.vue'
export default {
  components: {
    PlayGround,
    MatchGround,
  },
  setup() {
    const store = useStore()
    const socketUrl = `ws://127.0.0.1:3000/websocket/${store.state.user.token}/`
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
        }, 1000)
        store.commit('updateGamemap', data.gamemap)
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