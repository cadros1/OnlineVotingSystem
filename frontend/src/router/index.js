import { createRouter, createWebHistory } from 'vue-router';
import App from '../App.vue';
import login from '../components/login.vue';
import mainpage from '../components/mainpage.vue';

const routes = [
  { path: '/', component: mainpage },
  { path: '/login', component: login }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;