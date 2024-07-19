<template>
  <div class="background">

    <div class="left">
      <sidebar></sidebar>
    </div>

    <div class="right">x
      <voteshow></voteshow>
    </div>

    <button class="login-button" @click="handleSubmit">登录</button>
  </div>
</template>

<script type="text/javascript">
import sidebar from './views/sidebar.vue';
import voteshow from './views/voteshow.vue';
import login from './views/login.vue';
import { useRouter } from 'vue-router';

export default {
  setup() {
    const router = useRouter();

    const handleSubmit = async () => {
      router.push('/login');
    };

    return {
      handleSubmit
    };
  }
}
</script>

<style scoped>
.background {
  position: fixed;
  top: 0px;
  left: 0px;
  right: 0px;
  bottom: 0px;
  background-size: cover;
  background-position: center;
  width: 100%;
  height: 100%;


  background: linear-gradient(220.55deg, #B7DCFF 0%, #FFA4F6 100%);

}

.left {
  background-color: white;
  width: 50px;
  height: 100px;

}

.right {
  background-color: white;
  width: 50px;
  height: 100px;
}
</style>