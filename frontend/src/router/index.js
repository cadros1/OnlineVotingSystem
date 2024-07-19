import { createRouter, createWebHistory } from 'vue-router';
import login from '../components/login.vue';
import mainpage from '../components/mainpage.vue';
import registerpage from '../components/registerpage.vue';

const routes = [
  { path: '/', component: mainpage },
  { path: '/login', component: login },
  { path: '/register', component: registerpage }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;