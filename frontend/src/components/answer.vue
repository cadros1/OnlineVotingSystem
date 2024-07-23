<template>
    <div class="background">
        <div class="container">
            <div class="left">
                <Sidebar></Sidebar>
            </div>
            <div class="right">
                <div class="content">
                    <div v-if="currentQuestionIndex < questions.length">
                        <h1>{{ questions[currentQuestionIndex].question_text }}</h1>
                        <div v-if="questions[currentQuestionIndex].question_type === 0">
                            <div v-for="(option, index) in questions[currentQuestionIndex].options" :key="index">
                                <label>
                                    <input type="radio" :name="`question-${currentQuestionIndex}`" :value="option"
                                        v-model="selectedOptions[currentQuestionIndex]">
                                    {{ option }}
                                </label>
                            </div>
                        </div>
                        <div v-else-if="questions[currentQuestionIndex].question_type === 1">
                            <div v-for="(option, index) in questions[currentQuestionIndex].options" :key="index">
                                <label>
                                    <input type="checkbox" :value="option"
                                        @change="handleMultiSelectChange(currentQuestionIndex, option)" />
                                    {{ option }}
                                </label>
                            </div>
                        </div>
                        <div v-else-if="questions[currentQuestionIndex].question_type === 2">
                            <label>
                                <input type="radio" :name="`question-${currentQuestionIndex}`" value="正确"
                                    v-model="selectedOptions[currentQuestionIndex]">
                                正确
                            </label>
                            <label>
                                <input type="radio" :name="`question-${currentQuestionIndex}`" value="错误"
                                    v-model="selectedOptions[currentQuestionIndex]">
                                错误
                            </label>
                        </div>
                        <div v-else-if="questions[currentQuestionIndex].question_type === 3">
                            <input type="text" v-model="selectedOptions[currentQuestionIndex]">
                        </div>
                        <button @click="lastQuestion">上一题</button>
                        <button @click="nextQuestion">下一题</button>
                    </div>
                    <div v-else>
                        <h1>问卷完成！</h1>
                        <button @click="submitAnswers">提交</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import axios from 'axios';
import Sidebar from './sidebar.vue';
import { ref, onMounted } from 'vue';

const vote = {
    title: '示例问卷标题',
    description: '示例问卷说明',
    isPublic: true,
    account: '示例用户',
    rootQuestionId: 1,
    questionMap: [
        [1, { "question_id": 1, "question_type": 0, "question_text": "示例单选题", "required": true, "options": ["选项一", "选项二"], "jumpLogic": ["3", null], "hasOther": true }],
        [2, { "question_id": 2, "question_type": 1, "question_text": "示例多选题", "required": true, "options": ["选项一", "选项二", "选项三", "选项四", "选项五", "选项六"], "jumpLogic": [null, 1, null, null, null, null], "hasOther": false }],
        [3, { "question_id": 3, "question_type": 2, "question_text": "示例判断题", "required": true, "options": [], "jumpLogic": [null, 1], "hasOther": false }],
        [4, { "question_id": 4, "question_type": 3, "question_text": "示例填空题", "required": false, "options": [], "jumpLogic": [null], "hasOther": false }]
    ]
}

const questions = ref([]);
const currentQuestionIndex = ref(0);
const selectedOptions = ref({});

onMounted(() => {
    questions.value = vote.questionMap.map(item => item[1]);
});

const lastQuestion = () => {
    if (currentQuestionIndex.value > 0) {
        currentQuestionIndex.value--;
    }
};

const nextQuestion = () => {
    // 如果题目必选，但没有选择答案
    if (questions.value[currentQuestionIndex.value].required && !selectedOptions.value[currentQuestionIndex.value]) {
        alert('请选择答案');
        return;
    }
    //单选题和判断题
    if (questions.value[currentQuestionIndex.value].question_type === 0 || questions.value[currentQuestionIndex.value].question_type === 1) {
        let nextQuestionId;
        for (let i = 0; i < questions.value[currentQuestionIndex.value].options.length; i++) {
            if (selectedOptions.value[currentQuestionIndex.value] === questions.value[currentQuestionIndex.value].options[i]) {
                if (questions.value[currentQuestionIndex.value].jumpLogic[i] == null || questions.value[currentQuestionIndex.value].jumpLogic[i] > questions.value.length || questions.value[currentQuestionIndex.value].jumpLogic[i] < 1) {
                    if (currentQuestionIndex.value === questions.value.length - 1) {
                        alert('问卷完成！');
                        return;
                    }
                    currentQuestionIndex.value++;
                    console.log('单选题，但没有指定合法的跳题逻辑');
                    return;
                }
                nextQuestionId = parseInt(questions.value[currentQuestionIndex.value].jumpLogic[i]);
                console.log('单选题，有指定合法的跳题逻辑', nextQuestionId);
                currentQuestionIndex.value = nextQuestionId - 1;
                return;
            }

        }
    }
    //多选题
    if (questions.value[currentQuestionIndex.value].question_type === 1) {
        let nextQuestionId;
        for (let i = 0; i < questions.value[currentQuestionIndex.value].options.length; i++) {
            if (selectedOptions.value[currentQuestionIndex.value].includes(questions.value[currentQuestionIndex.value].options[i])) {
                if (questions.value[currentQuestionIndex.value].jumpLogic[i] == null || questions.value[currentQuestionIndex.value].jumpLogic[i] > questions.value.length || questions.value[currentQuestionIndex.value].jumpLogic[i] < 1) {
                    console.log('多选题，但这个选项没有指定合法的跳题逻辑', i);
                } else {
                    nextQuestionId = parseInt(questions.value[currentQuestionIndex.value].jumpLogic[i]);
                    console.log('多选题，有指定合法的跳题逻辑', nextQuestionId);
                    currentQuestionIndex.value = nextQuestionId - 1;
                    return;
                }
            }
        }
        if (currentQuestionIndex.value === questions.value.length - 1) {
            alert('问卷完成！');
            submitAnswers();
            return;
        }
        currentQuestionIndex.value++;
        console.log('多选题，均没有指定合法的跳题逻辑');
        return;
    }

    //填空题
    if (currentQuestionIndex.value === questions.value.length - 1) {
        alert('问卷完成！');
        submitAnswers();
        return;
    }
    currentQuestionIndex.value++;
    console.log('填空题，但没有指定合法的跳题逻辑');
    return;
}
const submitAnswers = () => {
    console.log('提交答案', selectedOptions.value);
    // 这里可以添加提交答案的逻辑
};

const handleMultiSelectChange = (questionIndex, option) => {
    if (!selectedOptions.value[questionIndex]) {
        selectedOptions.value[questionIndex] = [];
    }
    const index = selectedOptions.value[questionIndex].indexOf(option);
    if (index === -1) {
        selectedOptions.value[questionIndex].push(option);
    } else {
        selectedOptions.value[questionIndex].splice(index, 1);
    }
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
    display: flex;
    flex-direction: column;
    align-items: center;
}

.content {
    width: 100%;
    max-width: 800px;
    margin-top: 20px;
    text-align: center;
}
</style>
