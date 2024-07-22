<template>
    <div class="background">
        <div class="container">
            <div class="left">
                <Sidebar></Sidebar>
            </div>
            <div class="right">
                <div class="questionnaire">
                    <div class="input-group">
                        <label for="voteID">输入问卷id:</label>
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
                <button class="edit" @click="startAnswer(voteID)">开始作答</button>
            </div>
            <div v-else>
                <h2>查找失败！</h2>
                <p>问卷id不存在或已删除。</p>
                <button @click="showModal = false">关闭</button>
            </div>
        </div>
    </div>
</template>

<script setup>
import Sidebar from './sidebar.vue';
import { onMounted, watch, ref } from 'vue';
import { useRouter } from 'vue-router';
import { onBeforeRouteLeave } from 'vue-router';
import { axios } from 'axios';

const router = useRouter();
const textareaRef = ref(null);
const SearchVoteSuccess = ref(null);
const showModal = ref(false);
const voteID = ref("");
const title = ref("");
const description = ref('');


function SearchVote() {
    axios.get('/api/vote/' + voteID.value)
        .then(res => {
            if (res.data.success) {
                showModal.value = true;
                SearchVoteSuccess.value = true;
                title.value = res.data.data.title;
                description.value = res.data.data.description;
            } else {
                showModal.value = true;
                SearchVoteSuccess.value = false;
            }
        })
        .catch(err => {
            console.log(err);
        });
}

function startAnswer(voteID) {
    router.push({
        //路径为/vote？voteid=1&questionid=1
        path: '/vote',
        query: {
            voteid: voteID,
            questionid: 1
        }
    });
}



onBeforeRouteLeave((to, from, next) => {
    if (to.path === '/ask/edit') {
        to.meta.formData = { title: title.value, description: description.value };
    }
    next();
});
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
}

.questionnaire {
    width: 100%;
    height: 100%;
    background-color: #f5f5f5;
    border-radius: 10px;
    padding: 20px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}

.input-group {
    display: flex;
    flex-direction: row;
    margin-bottom: 10px;
}

.input-group label {
    width: 80px;
}

.input-group input,
.input-group textarea {
    margin-left: 10px;
    width: 400px;
}

.input-group textarea {
    overflow-y: auto;
    resize: none;
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
</style>