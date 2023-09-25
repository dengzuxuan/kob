<template>
  <div class="container">
    <div class="row">
      <div class="col-3">
        <div class="card"
             style="margin-top: 20px;">
          <div class="card-body">
            <img :src="$store.state.user.photo"
                 style="width: 100%;">
          </div>
        </div>
      </div>
      <div class="col-9">
        <div class="card"
             style="margin-top: 20px;">
          <div class="card-header">
            <span style="font-size:140%">
              我的bot
            </span>
            <button type="button"
                    class="btn btn-primary float-end"
                    data-bs-target="#add-bot-btn"
                    data-bs-toggle="modal">创建bot
            </button>

            <!-- Modal -->
            <div class="modal fade "
                 id="add-bot-btn"
                 tabindex="-1"
                 aria-labelledby="exampleModalLabel"
                 aria-hidden="true">
              <div class="modal-dialog modal-xl">
                <div class="modal-content">
                  <div class="modal-header">
                    <h1 class="modal-title fs-5"
                        id="exampleModalLabel">创建Bot</h1>
                    <button type="button"
                            class="btn-close"
                            data-bs-dismiss="modal"
                            aria-label="Close"></button>
                  </div>
                  <div class="modal-body">
                    <div class="mb-3">
                      <label for="add-bot-title"
                             class="form-label">bot名称</label>
                      <input type="text"
                             class="form-control"
                             id="exampleFormControlInput1"
                             placeholder="请输入bot名称"
                             v-model="botadd.title">
                    </div>
                    <div class="mb-3">
                      <label for="add-bot-description"
                             class="form-label">bot简介</label>
                      <textarea class="form-control"
                                id="exampleFormControlTextarea1"
                                rows="3"
                                placeholder="请输入bot简介"
                                v-model="botadd.description"></textarea>
                    </div>
                    <div class="mb-3">
                      <label for="add-bot-code"
                             class="form-label">bot代码</label>
                      <VAceEditor v-model:value="botadd.content"
                                  @init="editorInit"
                                  lang="c_cpp"
                                  theme="textmate"
                                  style="height: 300px"
                                  :options="{
                                                    enableBasicAutocompletion: true, //启用基本自动完成
                                                    enableSnippets: true, // 启用代码段
                                                    enableLiveAutocompletion: true, // 启用实时自动完成
                                                    fontSize: 18, //设置字号
                                                    tabSize: 4, // 标签大小
                                                    showPrintMargin: false, //去除编辑器里的竖线
                                                    highlightActiveLine: true,
                                                }" />

                    </div>
                  </div>
                  <div class="modal-footer">
                    <div class="error-message">{{ botadd.error_message }}</div>
                    <button type="button"
                            class="btn btn-primary"
                            @click="add_bot">创建</button>
                    <button type="button"
                            class="btn btn-secondary"
                            data-bs-dismiss="modal">取消</button>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div class="card-body">
            <table class="table table-hover table-striped">
              <thead>
                <tr>
                  <th>名称</th>
                  <th>创建时间</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="bot in bots"
                    :key="bot.id">
                  <td>{{ bot.title }}</td>
                  <td>{{ bot.createtime }}</td>
                  <td>
                    <button type="button"
                            class="btn btn-secondary"
                            style="margin-right: 10px;"
                            :data-bs-target="'#update-bot-modal-'+bot.id"
                            data-bs-toggle="modal">修改bot
                    </button>
                    <button type="button"
                            class="btn btn-danger"
                            @click="remove_bot(bot)">删除bot
                    </button>
                    <!-- Modal -->
                    <div class="modal fade "
                         :id="'update-bot-modal-'+bot.id"
                         tabindex="-1"
                         aria-labelledby="exampleModalLabel"
                         aria-hidden="true">
                      <div class="modal-dialog modal-xl">
                        <div class="modal-content">
                          <div class="modal-header">
                            <h1 class="modal-title fs-5">更新Bot</h1>
                            <button type="button"
                                    class="btn-close"
                                    data-bs-dismiss="modal"
                                    aria-label="Close"></button>
                          </div>
                          <div class="modal-body">
                            <div class="mb-3">
                              <label for="add-bot-title"
                                     class="form-label">bot名称</label>
                              <input type="text"
                                     class="form-control"
                                     placeholder="请输入bot名称"
                                     v-model="bot.title">
                            </div>
                            <div class="mb-3">
                              <label for="add-bot-description"
                                     class="form-label">bot简介</label>
                              <textarea class="form-control"
                                        rows="3"
                                        placeholder="请输入bot简介"
                                        v-model="bot.description"></textarea>
                            </div>
                            <div class="mb-3">
                              <label for="add-bot-code"
                                     class="form-label">bot代码</label>
                              <VAceEditor v-model:value="bot.content"
                                          @init="editorInit"
                                          lang="c_cpp"
                                          theme="textmate"
                                          style="height: 300px"
                                          :options="{
                                                    enableBasicAutocompletion: true, //启用基本自动完成
                                                    enableSnippets: true, // 启用代码段
                                                    enableLiveAutocompletion: true, // 启用实时自动完成
                                                    fontSize: 18, //设置字号
                                                    tabSize: 4, // 标签大小
                                                    showPrintMargin: false, //去除编辑器里的竖线
                                                    highlightActiveLine: true,
                                                }" />
                            </div>
                          </div>
                          <div class="modal-footer">
                            <div class="error-message">{{ bot.error_message }}</div>
                            <button type="button"
                                    class="btn btn-primary"
                                    @click="update_bot(bot)">更新</button>
                            <button type="button"
                                    class="btn btn-secondary"
                                    data-bs-dismiss="modal">取消</button>
                          </div>
                        </div>
                      </div>
                    </div>

                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
import ace from 'ace-builds'
import 'ace-builds/src-noconflict/ext-language_tools'
import 'ace-builds/src-noconflict/mode-c_cpp'
import 'ace-builds/src-noconflict/mode-json'
import 'ace-builds/src-noconflict/theme-chrome'
import { Modal } from 'bootstrap/dist/js/bootstrap'
import $ from 'jquery'
import { reactive, ref } from 'vue'
import { VAceEditor } from 'vue3-ace-editor'
import { useStore } from 'vuex'
export default {
  components: {
    VAceEditor,
  },
  setup() {
    ace.config.set(
      'basePath',
      'https://cdn.jsdelivr.net/npm/ace-builds@' +
        require('ace-builds').version +
        '/src-noconflict/'
    )

    const store = useStore()
    let bots = ref([])

    const botadd = reactive({
      title: '',
      description: '',
      content: '',
      error_message: '',
    })
    const refresh_bots = () => {
      $.ajax({
        url: 'https://app6040.acapp.acwing.com.cn/api/user/bot/getlist/',
        type: 'get',
        headers: {
          Authorization: 'Bearer ' + store.state.user.token,
        },
        success(resp) {
          bots.value = resp
        },
        error(resp) {
          console.log('error', resp)
        },
      })
    }

    refresh_bots()
    const add_bot = () => {
      botadd.error_message = ''
      $.ajax({
        url: 'https://app6040.acapp.acwing.com.cn/api/user/bot/add/',
        type: 'post',
        data: {
          title: botadd.title,
          description: botadd.description,
          content: botadd.content,
        },
        headers: {
          Authorization: 'Bearer ' + store.state.user.token,
        },
        success(resp) {
          if (resp.error_message == 'success') {
            botadd.content = ''
            botadd.title = ''
            botadd.description = ''
            refresh_bots()
            Modal.getInstance('#add-bot-btn').hide()
          } else {
            botadd.error_message = resp.error_message
          }
        },
        error(resp) {
          console.log(resp)
        },
      })
    }

    const remove_bot = (bot) => {
      $.ajax({
        url: 'https://app6040.acapp.acwing.com.cn/api/user/bot/remove/',
        type: 'post',
        data: {
          bot_id: bot.id,
        },
        headers: {
          Authorization: 'Bearer ' + store.state.user.token,
        },
        success(resp) {
          if (resp.error_message == 'success') {
            refresh_bots()
          }
          console.log(resp)
        },
        error(resp) {
          console.log(resp)
        },
      })
    }

    const update_bot = (bot) => {
      bot.error_message = ''
      $.ajax({
        url: 'https://app6040.acapp.acwing.com.cn/api/user/bot/update/',
        type: 'post',
        data: {
          bot_id: bot.id,
          title: bot.title,
          description: bot.description,
          content: bot.content,
        },
        headers: {
          Authorization: 'Bearer ' + store.state.user.token,
        },
        success(resp) {
          if (resp.error_message == 'success') {
            refresh_bots()
            Modal.getInstance('#update-bot-modal-' + bot.id).hide()
          } else {
            bot.error_message = resp.error_message
          }
        },
        error(resp) {
          console.log(resp)
        },
      })
    }
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
    // $.ajax({
    //   url: 'http://127.0.0.1:3000/user/bot/getlist/',
    //   type: 'get',
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
    return { bots, botadd, add_bot, remove_bot, update_bot }
  },
}
</script>
<style scoped>
div.error-message {
  color: red;
}
</style>