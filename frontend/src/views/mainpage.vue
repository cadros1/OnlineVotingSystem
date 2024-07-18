<template>
  <div class="background">
    <div class="left">
      <sidebar></sidebar> 
    </div>
    <div class="right">
      <voteshow></voteshow>
    </div>
    
  </div>
</template>

<script setup>
import sidebar from './sidebar.vue';
import voteshow from './voteshow.vue';
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


  background: linear-gradient(220.55deg, #B7DCFF 0%, #FFA4F6 100%);

}

</style>