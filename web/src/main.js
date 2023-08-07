import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
document.title='King Of Bots';
createApp(App).use(store).use(router).mount('#app')
//const jwt_token = localStorage.getItem('jwt_token')
// const token = localStorage.getItem('token')
// if (jwt_token) {
//   store.state.user.is_login = true
//   store.state.user.token = jwt_token
//   store.state.pulling_info = false;
// } 

 