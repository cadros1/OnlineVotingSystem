<template>
    <div class="background">
        <div class="container">
            <div class="left">
                <Sidebar></Sidebar>
            </div>
            <div class="right">
                <h1>用户主页!</h1>
                <p>账户 {{ getInformation('account') }}.</p>
                <p>用户名 {{ getInformation('username') }}.</p>
                <button @click="Exit">退出登录</button>
            </div>
            <router-view />
        </div>
    </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
import Sidebar from './sidebar.vue';

const router = useRouter();

const Exit = () => {
    sessionStorage.removeItem('account');
    sessionStorage.removeItem('username');
    sessionStorage.removeItem('token');
    router.push('/login');
};

const getInformation = (key) => {
    return sessionStorage.getItem(key);
};

</script>

<style scoped>
.background {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-size: cover;
    background-position: center;
    background: linear-gradient(220.55deg, #FF9D7E 0%, #4D6AD0 100%);
    padding-left: 10px;
    padding-right: 8px;
    padding-top: 8px;
    padding-bottom: 8px;
}

.container {
    height: 100%;
    width: 100%;
    display: flex;
    flex-direction: row;
}

.left {
    width: 100px;
    height: 100%;
    margin-right: 10px;
}

.right {
    background-color: rgba(255, 255, 255, 0.65);
    flex-grow: 1;
    height: auto;
    border-radius: 10px;
    padding: 20px;
}
</style>