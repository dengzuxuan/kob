<template>
  <ContentField>
    <div>
      我的bot
    </div>
  </ContentField>

</template>

<script>
import $ from 'jquery'
import { useStore } from 'vuex'
import ContentField from '../../../components/ContentField.vue'
export default {
  components: {
    ContentField,
  },
  setup() {
    const store = useStore()
    // $.ajax({
    //   url: 'http://127.0.0.1:3000/user/bot/add/',
    //   type: 'post',
    //   data: {
    //     title: '标题',
    //     description: '描述',
    //     content: '代码',
    //   },
    //   headers: {
    //     Authorization: 'Bearer ' + store.state.user.token,
    //   },
    //   success(resp) {
    //     console.log(resp)
    //   },
    //   error(resp) {
    //     console.log(resp)
    //   },
    // })
    // $.ajax({
    //   url: 'http://127.0.0.1:3000/user/bot/remove/',
    //   type: 'post',
    //   data: {
    //     bot_id: 4,
    //   },
    //   headers: {
    //     Authorization: 'Bearer ' + store.state.user.token,
    //   },
    //   success(resp) {
    //     console.log(resp)
    //   },
    //   error(resp) {
    //     console.log(resp)
    //   },
    // })
    // $.ajax({
    //   url: 'http://127.0.0.1:3000/user/bot/update/',
    //   type: 'post',
    //   data: {
    //     bot_id: 1,
    //     title: '修改标题',
    //     description: '修改描述',
    //     content: '修改代码',
    //   },
    //   headers: {
    //     Authorization: 'Bearer ' + store.state.user.token,
    //   },
    //   success(resp) {
    //     console.log(resp)
    //   },
    //   error(resp) {
    //     console.log(resp)
    //   },
    // })
    $.ajax({
      url: 'http://127.0.0.1:3000/user/bot/getlist/',
      type: 'get',
      headers: {
        Authorization: 'Bearer ' + store.state.user.token,
      },
      success(resp) {
        console.log(resp)
      },
      error(resp) {
        console.log(resp)
      },
    })
  },
}
</script>