<template>
    <div class="background">
        <div class="container">
            <div class="left">
                <Sidebar></Sidebar>
            </div>
            <div class="right">
                <div class="content">
                    <div v-if="currentQuestionIndex < questions.length">
                        <h1>{{ questions[currentQuestionIndex].question_id }}</h1>
                        <h1>{{ questions[currentQuestionIndex].question_text }}</h1>
                        <div v-if="questions[currentQuestionIndex].required == true">
                            <label> 必答题 </label>
                        </div>
                        <div v-if="questions[currentQuestionIndex].question_type === 0">
                            <div v-for="(option, index) in questions[currentQuestionIndex].options" :key="index">
                                <label>
                                    <input type="radio" :name="`question-${currentQuestionIndex}`" :value="option"
                                        v-model="selectedOptions[currentQuestionIndex]">
                                    {{ option }}
                                </label>
                            </div>
                            <div v-if="questions[currentQuestionIndex].hasOther">
                                <label>
                                    <input type="radio" :name="`question-${currentQuestionIndex}`" value="其他"
                                        v-model="selectedOptions[currentQuestionIndex]">
                                    其他（请补充）
                                </label>
                                <input type="text" v-model="otherOptions[currentQuestionIndex]"
                                    v-if="selectedOptions[currentQuestionIndex] === '其他'">
                            </div>
                        </div>
                        <div v-else-if="questions[currentQuestionIndex].question_type === 1">
                            <div v-for="(option, index) in questions[currentQuestionIndex].options" :key="index">
                                <label>
                                    <input type="checkbox" :value="option"
                                        v-model="selectedOptions[currentQuestionIndex]" />
                                    {{ option }}
                                </label>
                            </div>
                            <div v-if="questions[currentQuestionIndex].hasOther">
                                <label>
                                    <input type="checkbox" value="其他" v-model="selectedOptions[currentQuestionIndex]" />
                                    其他（请补充）
                                </label>
                                <input type="text" v-model="otherOptions[currentQuestionIndex]"
                                    v-if="selectedOptions[currentQuestionIndex].includes('其他')">
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
import { useRoute } from 'vue-router';

const vote = {
    title: '示例问卷标题',
    description: '示例问卷说明',
    isPublic: true,
    publishTime: 1721704188,
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
const otherOptions = ref({});
const route = useRoute();
const voteID = ref(0);

onMounted(() => {
    voteID = route.query.voteID;
    // 获取问卷信息
    SearchVote(voteID.value);
});

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

const lastQuestion = () => {
    if (currentQuestionIndex.value > 0) {
        currentQuestionIndex.value--;
    }
};

const submitAnswers = () => {
    // 打印答案
    console.log('答案：');
    for (let i = 0; i < questions.value.length; i++) {
        if (selectedOptions.value[i] === '其他') {
            console.log(`第${i + 1}题：${selectedOptions.value[i]} - ${otherOptions.value[i]}`);
        } else {
            console.log(`第${i + 1}题：${selectedOptions.value[i]}`);
        }
    }
};

function SearchVote() {
    try {
        console.log('/vote/' + voteID.value);
        axios.get('/vote/' + voteID.value)
            .then(res => {
                if (res.data.code === 20000) {
                    showModal.value = true;
                    SearchVoteSuccess.value = true;
                    vote.title = res.data.data.title;
                    vote.description = res.data.data.description;
                    vote.isPublic = res.data.data.isPublic;
                    vote.publishTime = res.data.data.publishTime;
                    vote.usermame = res.data.data.user.username;
                    vote.rootQuestionId = res.data.data.rootQuestionId;
                    vote.questionMap = res.data.data.questionMap;
                    questions.value = vote.questionMap.map(item => item[1]);
                    questions.value.forEach((question, index) => {
                        if (question.question_type === 1) {
                            selectedOptions.value[index] = [];
                        }
                    });
                } else {
                    showModal.value = true;
                    SearchVoteSuccess.value = false;
                    error.value = res.data.data.message;
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
