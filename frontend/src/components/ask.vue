<template>
    <div class="background">
        <div class="container">
            <div class="left">
                <Sidebar></Sidebar>
            </div>
            <div class="right">
                <div class="questionnaire">
                    <div class="input-group">
                        <label for="title">问卷标题：</label>
                        <input type="text" id="title" v-model="title" required></input>
                    </div>
                    <div class="input-group">
                        <label for="description">问卷说明：</label>
                        <textarea id="description" v-model="description" required ref="textareaRef"></textarea>
                    </div>
                    <div>
                        <label for="description">是否公开：</label>
                        <input type="checkbox" v-model="isPublic" />
                    </div>
                    <div>
                        <label for="description">发布时间：</label>
                        <input type="radio" value="now" v-model="publishTime" name="publishTime" />立即发布
                        <input type="radio" value="later" v-model="publishTime" name="publishTime" />定时发布
                        <input type="datetime-local" v-model="publishDate" />

                    </div>
                    <button @click="startSurvey">开始创建</button>
                </div>
            </div>
            <router-view />
        </div>
    </div>
</template>

<script setup>
import Sidebar from './sidebar.vue';
import { onMounted, watch, ref } from 'vue';
import { useRouter } from 'vue-router';
import { onBeforeRouteLeave } from 'vue-router';

const router = useRouter();
const textareaRef = ref(null);

const title = ref("");
const description = ref('');
const isPublic = ref(false);
const publishDate = ref('');
const publishTime = ref('now');

onMounted(() => {
    autoResizeTextarea();
    setDefaultPublishDate();
});

watch(description, () => {
    autoResizeTextarea();
});

function startSurvey() {
    //如果立即发布，那么将publishDate设置为当前时间
    if (publishTime.value === 'now') {
        setDefaultPublishDate();
    }
    router.push({
        path: '/ask/edit',
        query: {
            title: title.value,
            description: description.value,
            isPublic: isPublic.value,
            publishTime: transformDateToUnix(publishDate.value)
        }
    });
}

function autoResizeTextarea() {
    if (textareaRef.value) {
        const textarea = textareaRef.value;
        textarea.style.height = 'auto';
        textarea.style.height = `${Math.min(textarea.scrollHeight, 200)}px`; // 设定最大高度为200px
    }
}

function setDefaultPublishDate() {
    const now = new Date();
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0');
    const day = String(now.getDate()).padStart(2, '0');
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    publishDate.value = `${year}-${month}-${day}T${hours}:${minutes}`;
}

//将YYYY-MM-DDTHH:mm格式的日期转换为Unix时间戳
function transformDateToUnix(date) {
    const dateObj = new Date(date);
    return dateObj.getTime() / 1000;
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

input,
textarea {
    padding: 10px;
    border-radius: 4px;
    margin-right: 10px;
    font-size: 20px;
}
</style>