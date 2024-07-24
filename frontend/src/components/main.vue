<template>
    <div class="background">
        <div class="container">
            <div class="left">
                <Sidebar></Sidebar>
            </div>
            <div class="right">
                <div class="content">
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
    </div>
</template>

<script setup>
import { onMounted } from 'vue';
import Sidebar from './sidebar.vue';
import axios from 'axios';
import { ref } from 'vue';

const questionnaires = ref([
    {
        vote_id: 1,
        title: '问卷1',
        description: '这是问卷1的说明。',
        username: 'admin',
    },
    // 其他问卷数据
]);

const startAnswering = (questionnaire) => {
    if (sessionStorage.getItem('token')) {
        // 已登录，跳转到回答页面
        router.push({
            path: '/answer',
            query: {
                voteID: questionnaire.vote_id.value,
            }
        });
    } else {
        // 未登录，跳转到登录页面
        window.alert('请先登录！');
        router.push('/login');
        return;
    }
};

onMounted(() => {
    getVoteList();
});

function getVoteList() {
    try {
        console.log('/vote');
        axios.get('/vote')
            .then(res => {
                if (res.data.code === 20000) {
                    questionnaires.value = res.data.data;
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
    width: 160px;
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
    border-radius: 10px;
    padding: 20px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}

.questionnaire-list {
    display: flex;
    gap: 20px;
    flex-wrap: wrap;
    justify-content: flex;
    overflow-y: auto;
    max-height: 700px;
}

.questionnaire-box {
    background-color: #f0f0f0;
    border-radius: 10px;
    padding: 20px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    width: 250px;
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
