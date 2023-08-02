import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
document.title='King Of Bots';
createApp(App).use(store).use(router).mount('#app')
