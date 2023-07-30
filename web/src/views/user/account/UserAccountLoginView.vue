<template>
  <ContentField>
    <div class="row justify-content-md-center">
      <div class="col-3">
        <form @submit.prevent="login">
          <div class="mb-3">
            <label for="username"
                   class="form-label">用户名</label>
            <input type="text"
                   class="form-control"
                   v-model="username"
                   placeholder="请输入用户名">
          </div>

          <div class="mb-3">
            <label for="password"
                   class="form-label">密码</label>
            <input type="password"
                   class="form-control"
                   v-model="password"
                   placeholder="请输入密码">
          </div>
          <div class="error_msg">{{error_message}}</div>
          <button type="submit"
                  class="btn btn-primary">提交</button>
        </form>
      </div>
    </div>
  </ContentField>

</template>

<script>
import { ref } from 'vue'
import { useStore } from 'vuex'
import ContentField from '../../../components/ContentField.vue'
import router from '../../../router/index'
export default {
  components: {
    ContentField,
  },
  setup() {
    const store = useStore()
    let username = ref('')
    let password = ref('')
    let error_message = ref('')

    const login = () => {
      store.dispatch('login', {
        username: username.value,
        password: password.value,
        success() {
          store.dispatch('getinfo', {
            success() {
              router.push({ name: 'home' })
            },
          })
        },
        error(resp) {
          console.log(resp)
        },
      })
    }

    return {
      username,
      password,
      error_message,
      login,
    }
  },
}
</script>

<style scoped>
button {
  margin-top: 20px;
  width: 100%;
}
.error_msg {
  color: red;
}
</style>