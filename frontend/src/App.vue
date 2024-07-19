<template>
  <div id="app">
    <!-- 使用 router-view 组件来渲染当前路由对应的视图 -->
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'App',
}
</script>

<style lang="scss" scoped>
// 定义全局字体
body {
  font-family: Arial, sans-serif;
}

.app-title {
  font-size: 2em;
  color: #333;
}
</style>