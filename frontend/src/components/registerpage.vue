<template>
  <div class="background">
    <div class="login-container">

      <h1>Register</h1>

      <div class="input-group">
        <label for="account">账户:</label>
        <input type="text" id="account" v-model="account" placeholder="请输入6-12位数字或字母" pattern="[a-zA-Z0-9]{6,12}"
          maxlength="12" required />
      </div>

      <div class="input-group">
        <label for="password">用户名:</label>
        <input type="text" id="username" v-model="username" required />
      </div>

      <div class="input-group">
        <label for="password">密码:</label>
        <input type="password" id="password" v-model="password" placeholder="请输入6-12位数字或字母" pattern="[a-zA-Z0-9]{6,12}"
          maxlength="12" required />
      </div>

      <div class="input-group">
        <label for="password">再次输入:</label>
        <input type="password" id="confirm-password" v-model="confirmPassword" required />
      </div>

      <div class="button-container">
        <button class="login-button" @click="goToLogin">返回</button>
        <button class="register-button" @click="handleRegister">确认</button>
      </div>

    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { ref } from 'vue';
import axios from 'axios';

const account = ref('');
const username = ref('');
const password = ref('');
const confirmPassword = ref('');
const router = useRouter();

const goToLogin = () => {
  // 跳转到登录页面
  router.push('/login');
};
const handleRegister = async () => {
  if (account.value === '' || account.value.length < 6 || account.value.length > 12) {
    window.alert('无效的账户名');
    return;
  }
  if (username.value === '' || username.value.length > 16) {
    window.alert('无效的用户名');
    return;
  }
  if (password.value === '' || password.value.length < 6 || password.value.length > 12) {
    window.alert('无效的密码');
    return;
  }
  if (password.value !== confirmPassword.value) {
    window.alert('两次输入的密码不一致，请重新输入');
    return;
  }
  try {
    const response = await axios.post('/registerpage', {
      account: account.value,
      username: username.value,
      password: password.value
    });

    if (response.data.code === 20000) {
      // 注册成功处理
      goToLogin();
    } else {
      // 错误处理
      console.log('HTTP状态码:', response.status);
      console.log('业务逻辑状态码:', response.data.code);
      console.log('错误信息:', response.data.message)
      window.alert('注册失败，请检查你的账户名或密码是否正确');
    }
  } catch (error) {
    window.alert('注册失败，请检查连接:', error);
  }
};
</script>

<style scoped>
.background {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-image: url(../assets/bg/bg.jpg);
  /* 替换为你的图片路径 */
  background-size: cover;
  background-position: center;
  display: flex;
  justify-content: center;
  align-items: center;
}

.login-container {
  background-color: rgba(255, 255, 255, 0.8);
  /* 半透明白色背景 */
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
  filter: blur(1px);
  /* 添加高斯模糊效果 */
  transition: filter 0.3s ease-in-out;
  /* 模糊过渡效果 */

  display: flex;
  flex-direction: column;
  align-items: flex-start;
  /* 这将使所有子元素默认靠左对齐 */
  padding-right: 10px;
  /* 保持右侧有10px的内边距 */
}

.login-container h1 {
  text-align: center;
  /* 文本水平居中 */
  margin: 0 auto;
  /* 使元素在容器中水平居中 */
  margin-bottom: 15px;
}

.login-container:hover {
  filter: blur(0);
  /* 鼠标悬停时取消模糊 */
}

.input-group {
  margin-bottom: 15px;
  margin-right: 20px;
  display: flex;
  justify-content: space-between;
  /* 这将使标签和输入框分别靠向左右两侧 */
  width: 100%;
  /* 确保.input-group占据其父容器的全部宽度 */
}

.button-container {
  display: flex;
  /* 启用Flexbox布局 */
  justify-content: space-around;
  /* 在按钮之间分配空间，使它们水平均分 */
  width: 100%;
  /* 确保.button-container占据其父元素的完整宽度 */
}
</style>