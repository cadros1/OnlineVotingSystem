import './assets/main.css'

import { createApp } from 'vue'
import login from './views/login.vue'
import register from './views/registerpage.vue'
import App from './App.vue'

import router from './router'; // 导入路由实例

const app = createApp(App);
app.use(router); // 使用路由
app.mount('#app'); // 挂载到根实例上
