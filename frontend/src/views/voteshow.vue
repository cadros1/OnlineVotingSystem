<template>
  <div class="background">
    <div class="sidebar"></div>
    <div class="container">
        <h1>Welcome to Online Voting System</h1>
    </div>
  </div>
</template>

<script setup>
import Sidebar from './sidebar.vue';
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
  display: flex;    
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;

  background-color: antiquewhite;

}

</style>