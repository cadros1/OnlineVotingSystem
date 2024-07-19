<template>
  <div class="background">
    这是注册页面
    <button @click="goToLogin">完成注册</button>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

const goToLogin = () => {
  router.push('/login');
};

</script>

<style scoped></style>