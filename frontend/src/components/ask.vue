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
                        <input type="text" id="title" v-model="title" required />
                    </div>
                    <div class="input-group">
                        <label for="description">问卷说明：</label>
                        <textarea type="textarea" ref="textareaRef" id="description" v-model="description" required />
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
import { onMounted, watch } from 'vue';
import { ref } from 'vue';


const textareaRef = ref(null);
const description = ref('');

onMounted(() => {
    autoResizeTextarea();
});

watch(description, () => {
    autoResizeTextarea();
});

function autoResizeTextarea() {
    if (textareaRef.value) {
        const textarea = textareaRef.value;
        textarea.style.height = 'auto';
        textarea.style.height = `${Math.min(textarea.scrollHeight, 200)}px`; // 设定最大高度为200px
    }
}
</script>

function startSurvey() {
router.push('/ask/edit');
}

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
    /* 或者根据你的设计要求设置宽度 */
}

.input-group input,
.input-group textarea {
    margin-left: 10px;
    width: 400px;
    /* 或者设置为具体数值，如300px */
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