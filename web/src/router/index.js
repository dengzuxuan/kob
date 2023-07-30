import { createRouter, createWebHistory } from 'vue-router'
import store from '../store/index'
import NotFound from '../views/error/NotFound'
import PKIndexView from '../views/pk/PKIndexView'
import RankLIstIndexView from '../views/ranklist/RanklistIndexView'
import RecordIndexView from '../views/record/RecordIndexView'
import UserAccountLoginViewVue from '../views/user/account/UserAccountLoginView.vue'
import UserAccountRegisterViewVue from '../views/user/account/UserAccountRegisterView.vue'
import UserBotIndexView from '../views/user/bot/UserBotIndexView'
const routes = [
  {
    path: "/",
    name: "home",
    redirect: "/pk/",
    meta: {
      requestAuth:true
    }
  },
  {
    path: "/pk/",
    name: "pk_index",
    component:PKIndexView,
    meta: {
      requestAuth:true
    }
  },
  {
    path: "/record/",
    name: "record_index",
    component:RecordIndexView,
    meta: {
      requestAuth:true
    }
  },
  {
    path: "/ranklist/",
    name: "ranklist_index",
    component:RankLIstIndexView,
    meta: {
      requestAuth:true
    }
  },
  {
    path: "/user/bot",
    name: "user_bot_index",
    component:UserBotIndexView,
    meta: {
      requestAuth:true
    }
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

//router跳转前执行的操作
router.beforeEach((to,from,next) => {
  if (to.meta.requestAuth && !store.state.user.is_login) {
    next({
      name:"user_account_login"
    })
  } else {
    next()
  }
})

export default router
