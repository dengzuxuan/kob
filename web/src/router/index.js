import { createRouter, createWebHistory } from 'vue-router'
import store from '../store/index'
import NotFound from '../views/error/NotFound.vue'
import PKIndexView from '../views/pk/PKIndexView.vue'
import RankLIstIndexView from '../views/ranklist/RanklistIndexView.vue'
import RecordIndexView from '../views/record/RecordIndexView.vue'
import UserAccountLoginViewVue from '../views/user/account/UserAccountLoginView.vue'
import UserAccountRegisterViewVue from '../views/user/account/UserAccountRegisterView.vue'
import UserBotIndexView from '../views/user/bot/UserBotIndexView.vue'
import RecordContentView from '../views/record/RecordContentView.vue'
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
    path: "/record/:recordId/",
    name: "record_content",
    component:RecordContentView,
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

router.beforeEach((to, from, next) => { 
  if (to.meta.requestAuth && !store.state.user.is_login) {  
    next({ name: "user_account_login" }); 
  } else {
    next();
  }
})



//router跳转前执行的操作
// router.beforeEach((to, from, next) => {

//   let flag = 1;
//   const jwt_token = localStorage.getItem("jwt_token");

//   if (jwt_token) {
//     store.commit("updateToken", jwt_token);
//     store.dispatch("getinfo", {
//       success() {
//       },
//       error() {
//         alert("token无效，请重新登录！");
//         router.push({ name: 'user_account_login' });
//       }
//     })
//   } else {
//     flag = 2;
//   }

//   if (to.meta.requestAuth && !store.state.user.is_login) {
//     if (flag === 1) {
//       next();
//     } else {
//       alert("请先进行登录！");
//       next({name: "user_account_login"});
//     }
//   } else {
//     next();
//   }
// }) 

// router.beforeEach((to,from,next)=>{
//     let jwt_token=localStorage.getItem('jwt_token');
//     if(jwt_token){
//       store.dispatch('getinfo',jwt_token);
//       next();
//     }else{
//       if(to.meta.requestAuth&&!store.state.user.is_login)next({name:"user_account_token"});
//       else
//         next();
//     }
// })
 

export default router
