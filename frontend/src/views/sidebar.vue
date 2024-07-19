<template>
  <div class="box">
    <div class=" top-buttons">
      <button class="login-button">登录1</button>
      <button class="login-button">登录2</button>
      <button class="login-button">登录3</button>
      <button class="login-button">登录4</button>
    </div>
    <div class="bottom-buttons">
      <button class="login-button">登录a</button>
      <button class="login-button">登录b</button>
    </div>
  </div>
</template>

<script>
</script>

<style scoped>
.box {
  position: fixed;
  top: 0;
  left: 0;
  width: 200px;
  height: 100px;
}

.top-buttons,
.bottom-buttons {
  display: flex;
  justify-content: space-around;
  padding: 10px;
}

.top-buttons {
  position: sticky;
  top: 0;
  background-color: #fff;
  /* 防止滚动条遮挡 */
  z-index: 1;
}

.bottom-buttons {
  position: absolute;
  bottom: 0;
  background-color: #fff;
  /* 防止滚动条遮挡 */
}
</style>