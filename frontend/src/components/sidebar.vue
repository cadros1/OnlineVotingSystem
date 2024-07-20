<template>
  <div class="sidebar">
    <div class="container">
      <div class="top">
        <div class="logo">
          <button class="button" @click="goToMainPage">首页</button>
        </div>
        <div class="question">
          <button class="button" @click="goToAskPage">新建</button>
        </div>
        <div class="answer">
          <button class="button" @click="goToAnswerPage">问卷</button>
        </div>
      </div>
      <div class="middle"></div>
      <div class="bottom">
        <div class="manage">
          <button class="button" @click="goToManagePage">管理</button>
        </div>
        <div class="user">
          <button class="button" @click="goToUserPage">我的</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
const router = useRouter();


const goToMainPage = () => {
  // 跳转到主页面
  router.push('/');
};
const goToAskPage = () => {
  if (sessionStorage.getItem('token')) {
    // 已登录，跳转到提问页面
    router.push('/ask');
  } else {
    // 未登录，跳转到登录页面
    window.alert('请先登录！');
    router.push('/login');
    return;
  }
}
const goToAnswerPage = () => {
  // 跳转到回答页面
  router.push('/answer');
}
const goToManagePage = () => {
  if (sessionStorage.getItem('token')) {
    // 已登录，跳转到管理页面
    router.push('/manage');
  } else {
    // 未登录，跳转到登录页面
    window.alert('请先登录！');
    router.push('/login');
    return;
  }
}
const goToUserPage = () => {
  if (sessionStorage.getItem('token')) {
    // 已登录，跳转到用户页面
    router.push('/user');
  } else {
    // 未登录，跳转到登录页面
    router.push('/login');
    return;
  }
}
</script>

<style scoped>
.sidebar {
  height: 100%;
  width: 100%;
}

.container {
  height: 100%;
  width: 100%;
  display: flex;
  flex-direction: column;
}

.top {
  height: 300px;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
}

.middle {
  flex-grow: 1;
}

.bottom {
  height: 200px;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
}

/* 按钮样式 */
.button {
  padding: 0.5em 0.5em;
  text-align: center;
  text-decoration: none;
  border: 2px solid;
  font-size: 18px;
  display: inline-block;
  border-radius: 0.3em;
  /* 隐藏按钮边框 */
  border-color: transparent;
  transition: all 0.2s ease-in-out;
  position: relative;
  overflow: hidden;
  background-color: var(--color);
  color: #fff;
}

/* 按钮悬停状态 */
.button:hover {
  border-color: #fff;
}
</style>