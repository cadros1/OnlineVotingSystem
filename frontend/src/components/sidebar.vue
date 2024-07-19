<template>
  <div class="sidebar">
    <div class="container">
      <div class="top">
        <div class="logo">
          首页
        </div>
        <div class="question">
          提问
        </div>
        <div class="answer">
          作答
        </div>
      </div>
      <div class="middle"></div>
      <div class="bottom">
        <div class="manage">
          管理
        </div>
        <div class="user">
          我的
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
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
</style>