<template>
    <div class="background">
        <div class="container">
            <div class="left">
                <Sidebar></Sidebar>
            </div>
            <div class="right">
                <h1>问卷列表</h1>
                <div class="questionnaire-list">
                    <div class="questionnaire-box" v-for="(questionnaire, index) in questionnaires" :key="index">
                        <h2>{{ questionnaire.title }}</h2>
                        <p>{{ questionnaire.description }}</p>
                        <button @click="startAnswering(questionnaire)">开始回答</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import Sidebar from './sidebar.vue';

const questionnaires = [
    {
        title: '问卷1',
        description: '这是问卷1的说明。'
    },
    {
        title: '问卷2',
        description: '这是问卷2的说明。'
    },
    {
        title: '问卷3',
        description: '这是问卷3的说明。'
    },
    {
        title: '问卷4',
        description: '这是问卷4的说明。'
    },
    {
        title: '问卷5',
        description: '这是问卷5的说明。'
    },
    {
        title: '问卷6',
        description: '这是问卷6的说明。'
    }
    // 可以添加更多的问卷
];

const startAnswering = (questionnaire) => {
    console.log('开始回答问卷:', questionnaire.title);
    // 这里可以添加跳转到问卷回答页面的逻辑
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
    width: 60px;
    height: 100%;
    margin-right: 10px;
}

.right {
    background-color: white;
    flex-grow: 1;
    height: 100%;
    border-radius: 10px;
    padding: 20px;
}

.questionnaire-list {
    display: flex;
    gap: 20px;
    flex-wrap: wrap;
}

.questionnaire-box {
    background-color: #f0f0f0;
    border-radius: 10px;
    padding: 20px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    width: 300px;
}

.questionnaire-box h2 {
    margin-bottom: 10px;
}

.questionnaire-box button {
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    background-color: #4D6AD0;
    color: white;
    cursor: pointer;
}

.questionnaire-box button:hover {
    background-color: #3a54a8;
}
</style>
