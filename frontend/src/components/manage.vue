<template>
    <div class="background">
        <div class="container">
            <div class="left">
                <Sidebar></Sidebar>
            </div>
            <div class="right">
                <div class="content">
                    <h1>问卷统计</h1>
                    <div v-for="questionnaire in questionnaires" :key="questionnaire.vote_id"
                        class="questionnaire-item">
                        <h2>{{ questionnaire.title }}</h2>
                        <p>{{ questionnaire.description }}</p>
                        <p>发布时间: {{ new Date(questionnaire.publishTime).toLocaleString() }}</p>
                        <p>是否公开: {{ questionnaire.isPublic ? '是' : '否' }}</p>
                        <button @click="openStatisticsModal(questionnaire.vote_id)">查看统计</button>
                        <button @click="deleteSurvey(questionnaire.vote_id)">删除</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal" v-if="showModal">
            <div class="modal-content">
                <span class="close" @click="closeStatisticsModal">&times;</span>
                <h2>问卷统计 - {{ vote.title }}</h2>
                <div v-if="statisticsLoading">加载中...</div>
                <div v-else>
                    <div v-for="(question, index) in questions" :key="question.question_id" class="question-statistics"
                        v-if="hasStatistics(question)">
                        <h3 @click="toggleQuestionDetails(index)">{{ question.question_text }}</h3>
                        <div v-show="question.showDetails" class="options-stats">
                            <div v-for="(option, optIndex) in question.options" :key="optIndex" class="option-item">
                                <p>{{ option }}: {{ question.optionStats[optIndex] || 0 }} ({{ getPercentage(question,
                                    optIndex) }}%)</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import Sidebar from './sidebar.vue';
import { ref, onMounted } from 'vue';
import axios from 'axios';

onMounted(() => {
    getVoteList();
});

const showModal = ref(false);
const selectedVoteId = ref(null);
const statistics = ref([]);
const vote = ref({
    title: '',
    description: '',
    isPublic: true,
    publishTime: null,
    username: '',
    rootQuestionId: null,
    questionMap: {}
});

const questions = ref([]);
const statisticsLoading = ref(true);

const toggleQuestionDetails = (index) => {
    questions.value[index].showDetails = !questions.value[index].showDetails;
};

const openStatisticsModal = (id) => {
    selectedVoteId.value = id;
    showModal.value = true;
    fetchStatistics(id);
};

const closeStatisticsModal = () => {
    showModal.value = false;
};

const hasStatistics = (question) => {
    return question.optionStats && question.optionStats.some(count => count > 0);
};

const fetchStatistics = async (id) => {
    statisticsLoading.value = true;
    try {
        const response = await axios.get('/analyse/' + id);
        if (response.data.code === 20000) {
            const { vote, questionAnalyseDatas } = response.data.data;
            vote.value = {
                title: vote.title,
                description: vote.description,
                isPublic: vote.isPublic,
                publishTime: vote.publishTime,
                username: response.data.data.vote.user.username,
                rootQuestionId: vote.rootQuestionId,
                questionMap: vote.questionMap
            };
            questions.value = Object.values(vote.questionMap).map(question => {
                const questionStats = questionAnalyseDatas.find(stat => stat.questionId === question.question_id);
                return {
                    ...question,
                    showDetails: false,
                    options: question.options,
                    optionStats: questionStats ? questionStats.count : question.options.map(() => 0)
                };
            }).filter(hasStatistics); // 过滤掉没有统计数据的题目
        } else {
            console.error('获取统计信息失败');
        }
    } catch (err) {
        console.error('获取统计信息出错：', err);
    } finally {
        statisticsLoading.value = false;
    }
};

const getPercentage = (question, index) => {
    const total = question.optionStats.reduce((sum, count) => sum + count, 0);
    return total > 0 ? ((question.optionStats[index] / total) * 100).toFixed(2) : 0;
};

const questionnaires = ref([]);

const deleteSurvey = (id) => {
    try {
        axios.delete('/vote/' + id)
            .then(res => {
                if (res.data.code === 20000) {
                    window.alert('删除问卷成功');
                    getVoteList();
                } else {
                    console.error('删除问卷失败');
                }
            })
            .catch(err => {
                console.error('删除问卷失败：', err);
            });
    } catch (e) {
        console.error('删除问卷失败：', e);
    }
};

function getVoteList() {
    try {
        axios.get('/vote?userAccount=' + sessionStorage.getItem('account'))
            .then(res => {
                if (res.data.code === 20000) {
                    questionnaires.value = res.data.data;
                } else {
                    console.error('获取问卷列表失败');
                }
            })
            .catch(err => {
                console.error('获取问卷列表失败：', err);
            });
    } catch (e) {
        console.error('获取问卷列表失败：', e);
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
    padding: 10px;
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
    overflow-y: auto;
}

.questionnaire-item {
    border-bottom: 1px solid #ccc;
    padding: 10px 0;
}

button {
    margin-right: 10px;
}

.modal {
    display: flex;
    justify-content: center;
    align-items: center;
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
}

.modal-content {
    background-color: #fff;
    padding: 20px;
    border-radius: 10px;
    width: 80%;
    max-width: 500px;
    position: relative;
}

.close {
    position: absolute;
    top: 10px;
    right: 15px;
    font-size: 20px;
    cursor: pointer;
}

.question-statistics {
    cursor: pointer;
    margin-bottom: 10px;
}

.options-stats {
    padding-left: 20px;
}

.option-item {
    margin: 5px 0;
    padding-left: 10px;
    border-left: 2px solid #ccc;
}
</style>
