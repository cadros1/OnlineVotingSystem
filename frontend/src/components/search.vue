<template>
    <div class="background">
        <div class="container">
            <div class="left">
                <Sidebar></Sidebar>
            </div>
            <div class="right">
                <div class="questionnaire">
                    <div class="input-group">
                        <label for="voteID">问卷id:</label>
                        <input type="text" id="voteID" v-model="voteID" required></input>
                    </div>
                    <button @click="SearchVote">查找问卷</button>
                </div>
            </div>
            <router-view />
        </div>
    </div>

    <!-- 模态框 -->
    <div v-if="showModal" class="modal-overlay">
        <div class="modal">
            <div v-if="SearchVoteSuccess">

                <h2>查找成功！</h2>
                <p>问卷标题: {{ title }}</p>
                <p>问卷描述: {{ description }}</p>
                <button @click="showModal = false">关闭</button>
                <button class="edit" @click="startAnswer()">开始作答</button>
            </div>
            <div v-else>
                <h2>查找失败！</h2>
                <p>{{ error }}</p>
                <button @click="showModal = false">关闭</button>
            </div>
        </div>
    </div>
</template>

<script setup>
import Sidebar from './sidebar.vue';
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

const router = useRouter();
const SearchVoteSuccess = ref(null);
const showModal = ref(false);
const voteID = ref("");
const title = ref("");
const description = ref('');
const isPublic = ref(false);
const publishTime = ref("");
const error = ref("");
const usermame = ref("");


function SearchVote() {
    try {
        console.log('/vote/', voteID.value);
        axios.get('/vote/' + voteID.value)
            .then(res => {
                if (res.data.code === 20000) {
                    showModal.value = true;
                    SearchVoteSuccess.value = true;
                    title.value = res.data.data.title;
                    description.value = res.data.data.description;
                    isPublic.value = res.data.data.isPublic;
                    publishTime.value = res.data.data.publishTime;
                    usermame.value = res.data.data.user.username;
                } else {
                    showModal.value = true;
                    SearchVoteSuccess.value = false;
                    error.value = res.data.message;
                }
            })
            .catch(err => {
                console.log(err);
                SearchVoteSuccess.value = false;
                error.value = "请求失败，请检查网络连接或联系管理员。";
            });
    } catch (e) {
        SearchVoteSuccess.value = false;
        error.value = "代码已退出";
        return;
    }
    showModal.value = true;
}

function startAnswer() {
    router.push({
        //路径为/vote？voteid=1&questionid=1
        path: '/vote',
        query: {
            voteid: voteID,
            questionid: 1
        }
    });
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
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}

.questionnaire {
    width: 40%;
    border-radius: 10px;
    padding: 20px;
    display: flex;
    flex-direction: column;
}

.input-group {
    display: flex;
    flex-direction: row;
    margin-bottom: 10px;
}

.input-group label {
    width: 80px;
}

.input-group input {
    margin-left: 10px;
    width: 400px;
}

button {
    display: block;
    margin: 20px auto;
    padding: 10px 20px;
    background-color: #007BFF;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

button:hover {
    background-color: #0056b3;
}

.modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
}

.modal {
    background: white;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    width: 500px;
}
</style>