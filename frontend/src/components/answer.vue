<template>
    <div class="background">
        <div class="container">
            <div class="left">
                <Sidebar></Sidebar>
            </div>
            <div class="right">
                <div v-if="currentQuestionIndex < questions.length">
                    <h1>{{ questions[currentQuestionIndex].question }}</h1>
                    <div v-if="questions[currentQuestionIndex].type === '判断题'">
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
                    <div v-else-if="questions[currentQuestionIndex].type === '填空题'">
                        <input type="text" v-model="selectedOptions[currentQuestionIndex]">
                    </div>
                    <div v-else-if="questions[currentQuestionIndex].type === '单选题'">
                        <div v-for="(option, index) in questions[currentQuestionIndex].options" :key="index">
                            <label>
                                <input type="radio" :name="`question-${currentQuestionIndex}`" :value="option"
                                    v-model="selectedOptions[currentQuestionIndex]">
                                {{ option }}
                            </label>
                        </div>
                    </div>
                    <div v-else-if="questions[currentQuestionIndex].type === '多选题'">
                        <p>{{ questions[currentQuestionIndex].question }}</p>
                        <div v-for="(option, index) in questions[currentQuestionIndex].options" :key="index">
                            <label>
                                <input type="checkbox" :value="option"
                                    @change="handleMultiSelectChange(currentQuestionIndex, option)" />
                                {{ option }}
                            </label>
                        </div>
                    </div>
                    <button @click="nextQuestion">下一题</button>
                </div>
                <div v-else>
                    <h1>问卷完成！</h1>
                    <button @click="submitAnswers">提交</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import axios from 'axios';
import Sidebar from './sidebar.vue';
import { ref } from 'vue';

const questions = [
    {
        type: '判断题',
        question: '地球是平的吗？',
        options: ['正确', '错误']
    },
    {
        type: '填空题',
        question: '你最喜欢的颜色是什么？'
    },
    {
        type: '单选题',
        question: '你最喜欢的动物是什么？',
        options: ['猫', '狗', '兔子', '鸟']
    },
    {
        type: '多选题',
        question: '你喜欢哪些水果？',
        options: ['苹果', '香蕉', '橙子', '草莓']
    },
    // 可以添加更多的问题
];
const currentQuestionIndex = ref(0);
const selectedOptions = ref({});

const fetchQuestions = async () => {
    try {
        const response = await axios.get('/vote/questionnaire'); // 替换为你的API URL
        questions.value = response.data.questions;
        // 初始化 selectedOptions 对象
        questions.value.forEach(question => {
            selectedOptions.value[question.id] = [];
            if (question.type === 'single_choice') {
                selectedOptions.value[question.id] = null;
            }
        });
    } catch (error) {
        console.error('Error fetching questionnaire:', error);
    }
};

const nextQuestion = () => {
    if (currentQuestionIndex.value < questions.length - 1) {
        currentQuestionIndex.value++;
    }
};

const submitAnswers = () => {
    console.log('提交的答案:', selectedOptions.value);
    // 这里可以添加提交答案的逻辑
};

// 初始化当前问题的选项数组
if (questions[currentQuestionIndex.value].type === '多选题') {
    selectedOptions.value[currentQuestionIndex.value] = [];
}

// 添加一个函数来处理多选题的选项变化
const handleMultiSelectChange = (index, option) => {
    const currentSelectedOptions = selectedOptions.value[index];
    if (currentSelectedOptions.includes(option)) {
        currentSelectedOptions.splice(currentSelectedOptions.indexOf(option), 1);
    } else {
        currentSelectedOptions.push(option);
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
}
</style>
