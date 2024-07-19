import { createRouter, createWebHistory } from 'vue-router';
import main from '../components/main.vue';
import login from '../components/login.vue';
import register from '../components/register.vue';
import ask from '../components/ask.vue';
import answer from '../components/answer.vue';
import manage from '../components/manage.vue';
import user from '../components/user.vue';

const routes = [
  { path: '/', component: main },
  { path: '/login', component: login },
  { path: '/register', component: register },
  { path: '/ask', component: ask },
  { path: '/answer' , component: answer },
  { path: '/manage', component: manage },
  { path: '/user', component: user }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;