<template>
  <div class="sidebar">
    <div class="top-buttons">
      <!-- 上方的四个按钮 -->
      <button v-for="n in 4" :key="n">按钮 {{ n }}</button>
    </div>
    <div class="main-content">
      <!-- 中间的侧边栏内容 -->
      <!-- 例如：导航菜单 -->
    </div>
    <div class="bottom-buttons">
      <!-- 下方的两个按钮 -->
      <button v-for="n in [5, 6]" :key="n">按钮 {{ n }}</button>
    </div>
  </div>
</template>

<script>
</script>

<style scoped>
.sidebar {
  position: fixed;
  top: 0;
  left: 0;
  width: 200px; /* 或者你想要的任何固定宽度 */
  height: 100vh; /* 视口高度 */
  background-color: #f8f9fa; /* 示例背景色 */
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
  background-color: #fff; /* 防止滚动条遮挡 */
  z-index: 1;
}

.bottom-buttons {
  position: absolute;
  bottom: 0;
  background-color: #fff; /* 防止滚动条遮挡 */
}
</style>