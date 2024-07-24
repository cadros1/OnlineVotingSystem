<template>
    <div class="background">
        <div class="container">
            <div class="left">
                <Sidebar></Sidebar>
            </div>
            <div class="right">
                <div class="content">
                    <h1>问卷统计</h1>
                    <div v-for="questionnaire in questionnaires" :key="questionnaire.id" class="questionnaire-item">
                        <h2>{{ questionnaire.title }}</h2>
                        <p>{{ questionnaire.description }}</p>
                        <p>发布时间: {{ questionnaire.publishTime }}</p>
                        <p>是否公开: {{ questionnaire.isPublic ? '是' : '否' }}</p>
                        <button @click="viewStatistics(questionnaire.id)">查看统计</button>
                        <button @click="deleteSurvey(questionnaire.id)">删除</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import Sidebar from './sidebar.vue';
import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import axios from 'axios';

onMounted(() => {
    getVoteList();
});

const questionnaires = ref([
    {
        id: 1,
        title: '问卷1',
        description: '这是一个关于问卷1的描述',
        publishTime: '2023-04-01',
        isPublic: true
    },
    {
        id: 2,
        title: '问卷2',
        description: '这是一个关于问卷2的描述',
        publishTime: '2023-04-02',
        isPublic: false
    }
]);

const viewStatistics = (id) => {
    console.log(`查看问卷 ${id} 的统计`);
};

const deleteSurvey = (id) => {
    console.log(`删除问卷 ${id}`);
};

function getVoteList() {
    try {
        console.log('/vote?user_account=' + sessionStorage.getItem('account'));
        axios.get('/vote?user_account=' + sessionStorage.getItem('account'))
            .then(res => {
                if (res.data.code === 20000) {
                    questionnaires.value = res.data.data;
                    console.log('res:', res.data.data);
                    console.log(questionnaires.value);
                    console.log('获取问卷列表成功1');
                } else {
                    console.error('获取问卷列表失败2');
                }
            })
            .catch(err => {
                console.log(err);
                console.error('获取问卷列表失败3');
            });
    } catch (e) {
        console.error('获取问卷列表失败4');
    }
}
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

.content {
    display: flex;
    flex-direction: column;
    margin-top: 20px;
    width: 100%;
}

.questionnaire-item {
    border-bottom: 1px solid #ccc;
    padding: 10px 0;
}

button {
    margin-right: 10px;
}
</style>
