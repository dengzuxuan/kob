import { createStore } from 'vuex';
import modelPK from './pk';
import moduleRecord from './record';
import moduleUser from './user';
export default createStore({
  state: {
  },
  getters: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    user: moduleUser,
    pk: modelPK,
    record:moduleRecord
  }
})
