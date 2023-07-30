import { createRouter, createWebHistory } from 'vue-router' 
import PKIndexView from '../views/pk/PKIndexView'
import RecordIndexView from '../views/record/RecordIndexView'
import UserBotIndexView from '../views/user/bot/UserBotIndexView'
import NotFound from '../views/error/NotFound'
import RankLIstIndexView from '../views/ranklist/RanklistIndexView'
import UserAccountLoginViewVue from '../views/user/account/UserAccountLoginView.vue'
import UserAccountRegisterViewVue from '../views/user/account/UserAccountRegisterView.vue'
const routes = [
  {
    path: "/",
    name: "home",
    redirect:"/pk/",
  },
  {
    path: "/pk/",
    name: "pk_index",
    component:PKIndexView,
  },
  {
    path: "/record/",
    name: "record_index",
    component:RecordIndexView,
  },
  {
    path: "/ranklist/",
    name: "ranklist_index",
    component:RankLIstIndexView,
  },
  {
    path: "/user/bot",
    name: "user_bot_index",
    component:UserBotIndexView,
  },
  {
    path: "/user/account/login/",
    name: "user_account_login",
    component:UserAccountLoginViewVue,
  },
  {
    path: "/user/account/register/",
    name: "user_account_register",
    component:UserAccountRegisterViewVue,
  },
  {
    path: "/404/",
    name: "404",
    component:NotFound,
  },
  {
    path: "/:catchAll(.*)",
    redirect:"/404/",
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
