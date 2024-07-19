import { createRouter, createWebHistory } from 'vue-router';
import Vue from 'vue'
import Router from 'vue-router';
import login from '../views/login.vue';
import registerpage from '../views/registerpage.vue';
import App from '@/App.vue';

const routes = [
  {
    path: '/',
    name: 'App',
    component: App
  },
  {
    path: '/login',
    name: 'login',
    component: login
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;