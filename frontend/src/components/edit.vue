<template>
    <div class="background">
        <div class="container">
            <div class="left">
                <Sidebar></Sidebar>
            </div>
            <div class="right">
                <div class="buttons">
                    <div class="actions">
                        <button class="back" @click="$router.push('/ask')">返回</button>
                        <button class="new" @click="showModal = true">新建</button>
                        <button class="save" @click="saveItems">发布</button>
                    </div>
                </div>
                <div class="content">
                    <table class="table">
                        <thead>
                            <tr>
                                <th style="width: 50px;">题号</th>
                                <th style="width: 50px;">类型</th>
                                <th style="width: 200px;">问题</th>
                                <th style="width: 50px;">必答</th>
                                <th style="width: 50px;">编辑</th>
                                <th style="width: 50px;">删除</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="item in items" :key="item.id">
                                <td>{{ item.id }}</td>
                                <td>{{ item.type }}</td>
                                <td
                                    style="max-width: 100px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">
                                    {{ item.name }}</td>

                                <td>
                                    <input type="checkbox" v-model="item.isRequired" />
                                </td>

                                <td>
                                    <button class="edit" @click="editItem(item)">编辑</button>
                                </td>
                                <td>
                                    <button class="delete" @click="deleteItem(item)">删除</button>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <router-view />
        </div>
    </div>

    <!-- 模态框 -->
    <div v-if="showModal" class="modal-overlay">
        <div class="modal">
            <h2>{{ editingItem ? '编辑题目' : '选择题目类型' }}</h2>
            <div v-if="!editingItem" class="radio-group">
                <label>
                    <input type="radio" v-model="selectedType" value="单选"> 单选
                </label>
                <label>
                    <input type="radio" v-model="selectedType" value="多选"> 多选
                </label>
                <label>
                    <input type="radio" v-model="selectedType" value="判断"> 判断
                </label>
                <label>
                    <input type="radio" v-model="selectedType" value="填空"> 填空
                </label>
                <button class="edit" @click="addItemWithType">确定</button>
            </div>
            <div v-else>
                <label>
                    题目类型: {{ editingItem.type }}
                    <div></div>
                </label>
                <label>
                    题目内容:
                    <textarea v-model="editingItem.name" rows="4" style="width: 100%;"></textarea>
                </label>
                <div v-if="editingItem.type === '单选' || editingItem.type === '多选'">
                    <div v-for="(option, index) in editingItem.options" :key="index" class="option">
                        <input type="text" class="option-input" v-model="editingItem.options[index]" placeholder="选项内容">
                        <input type="number" :value="editingItem.jumpLogic[index]"
                            @input="validateJumpLogic($event, index)" min="0" max="9999" placeholder="跳题逻辑">
                        <button class="delete" @click="removeOption(index)">删除</button>
                    </div>
                    <div>
                        <input type="checkbox" v-model="editingItem.hasOther" />
                        <label>
                            允许其他选项
                        </label>
                    </div>

                    <div class="option">
                        <button class="newOp" @click="addOption">添加选项</button>
                        <button class="edit" @click="saveItem">确定</button>
                    </div>
                </div>
                <div v-if="editingItem.type === '填空'">
                    <div v-for="(option, index) in editingItem.options" :key="index" class="option">
                        <input type="number" :value="editingItem.jumpLogic[index]" @input="validateJumpLogic($event, 0)"
                            min="0" max="9999" placeholder="跳题逻辑">
                        <button class="edit" @click="saveItem">确定</button>
                    </div>
                </div>
                <div v-if="editingItem.type === '判断'">
                    <div v-for="(option, index) in editingItem.options" :key="index" class="option">
                        <label>{{ option }}</label>
                        <input type="number" :value="editingItem.jumpLogic[index]" @input="validateJumpLogic($event, 0)"
                            min="0" max="9999" placeholder="跳题逻辑">
                    </div>
                    <div class="option">
                        <label></label>
                        <button class="edit" @click="saveItem">确定</button>
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

const router = useRouter();
const route = useRoute();

const items = ref([]);
const showModal = ref(false);
const selectedType = ref('');
const editingItem = ref(null);
const title = ref('');
const description = ref(null);
const isPublic = ref(false);
const publishTime = ref('');

// 模拟数据加载
onMounted(() => {
    // 这里可以调用API获取数据
    items.value = [
        { id: 1, type: '单选', name: '示例问题', options: ['选项一', '选项二'], jumpLogic: [null, null], isRequired: true, hasOther: false }
    ];
    title.value = route.query.title || 'error';
    description.value = route.query.description || 'error';
    isPublic.value = route.query.isPublic;
    publishTime.value = route.query.publishTime;
});

function editItem(item) {
    editingItem.value = { ...item, options: [...item.options], jumpLogic: [...item.jumpLogic], isRequired: item.isRequired, hasOther: item.hasOther };
    showModal.value = true;
}

function deleteItem(item) {
    // 删除功能实现
    const index = items.value.findIndex(i => i.id === item.id);
    if (index !== -1) {
        items.value.splice(index, 1);
        // 更新后续题目的题号
        for (let i = index; i < items.value.length; i++) {
            items.value[i].id -= 1;
        }
    }
}


function addItemWithType() {
    if (selectedType.value) {
        const maxId = Math.max(...items.value.map(item => item.id), 0);
        if (selectedType.value === '判断') {
            items.value.push({ id: maxId + 1, type: selectedType.value, name: '新建问题', options: ['对', '错'], jumpLogic: [null, null], isRequired: true, hasOther: false });
        }
        else if (selectedType.value === '单选' || selectedType.value === '多选') {
            items.value.push({ id: maxId + 1, type: selectedType.value, name: '新建问题', options: ['选项一', '选项二'], jumpLogic: [null, null], isRequired: true, hasOther: false });
        } else {
            items.value.push({ id: maxId + 1, type: selectedType.value, name: '新建问题', options: [''], jumpLogic: [null], isRequired: true, hasOther: false });
        }
        showModal.value = false;
    }
}

function validateJumpLogic(event, index) {
    let value = event.target.value;
    if (value < 0) {
        value = 0;
    } else if (value > 99) {
        value = 99;
    }
    editingItem.value.jumpLogic[index] = value;
}

function saveItem() {
    if (editingItem.value) {
        const index = items.value.findIndex(i => i.id === editingItem.value.id);
        if (index !== -1) {
            items.value[index] = { ...editingItem.value };
        }
    }
    showModal.value = false;
    editingItem.value = null;
    selectedType.value = '';
}


function addOption() {
    if (editingItem.value) {
        editingItem.value.options.push('');
        editingItem.value.jumpLogic.push(null);
    }
}

function removeOption(index) {
    if (editingItem.value) {
        editingItem.value.options.splice(index, 1);
        editingItem.value.jumpLogic.splice(index, 1);
    }
}


const saveItems = async () => {
    try {
        const questionMap = new Map();
        for (let i = 0; i < items.value.length; i++) {
            const item = items.value[i];
            switch (item.type) {
                case '单选':
                    item.typenumber = 0;
                    break;
                case '多选':
                    item.typenumber = 1;
                    break;
                case '判断':
                    item.typenumber = 2;
                    break;
                case '填空':
                    item.typenumber = 3;
                    break;
                default:
                    item.typenumber = -1;
                    console.error('未知的题目类型:', item.type);
                    break;
            }

            const question = {
                question_id: item.id,
                question_type: item.typenumber,
                question_text: item.name,
                required: item.isRequired,
                options: item.options,
                jumpLogic: item.jumpLogic,
                hasOther: item.hasOther
            };
            questionMap.set(item.id, question);
        }
        //头部带token

        const token = sessionStorage.getItem('token');

        const headers = {
            'Authorization': `Bearer ${token}`
        };
        console.log('headers:', headers);
        console.log('title:', title.value);
        console.log('description:', description.value);
        console.log('isPublic:', isPublic.value)
        console.log('publishTime:', publishTime.value);
        console.log('account:', sessionStorage.getItem('account'));
        console.log('rootQuestionId:', "1");
        console.log('questions:', questionMap);
        console.log('questionMap:', JSON.stringify(Array.from(questionMap.entries())));


        const response = await axios.post('/vote/newvote',
            {
                title: title.value,
                description: description.value,
                isPublic: isPublic.value,
                publishTime: publishTime.value,
                account: sessionStorage.getItem('account'),
                rootQuestionId: "1",
                questionMap: JSON.stringify(Array.from(questionMap.entries()))
            },
            { headers }
        );

        if (response.data.code === 20000) {
            // 保存成功处理
            console.log('问卷标题和说明保存成功');
            window.alert('问卷标题和说明保存成功!问卷ID:', response.data.data);
            router.push('/');
        } else {
            // 错误处理
            console.log('HTTP状态码:', response.status);
            console.log('业务逻辑状态码:', response.data.code);
            console.log('错误信息:', response.data.message);
            window.alert('保存失败，请检查输入信息');
        }
    } catch (error) {
        if (error.response) {
            window.alert('保存失败，请检查连接:', error);
        }
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
    flex-direction: row;
}

.content {
    height: 90%;
    width: 800px;
    margin: 0 auto;
    padding: 20px;
    background-color: #fff;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    overflow-y: auto;
}

.table {
    width: 100%;
    border-collapse: collapse;
}

tbody {
    width: 100%;
    border-collapse: collapse;
}

th,
td {
    text-align: left;
    padding: 10px;
    border-bottom: 1px solid #ddd;
}

th {
    background-color: #f2f2f2;
    font-weight: bold;
}

.actions {
    display: flex;
    justify-content: space-between;
    margin-top: 20px;
    display: flex;
    flex-direction: column;
}

.option {
    margin-bottom: 10px;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
}

.option-input {
    width: 60%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
    margin-right: 10px;
}

button {
    padding: 8px 12px;
    font-size: 14px;
    cursor: pointer;
    border: none;
    border-radius: 4px;
    color: #fff;
}

button.edit {
    background-color: #007bff;
}

button.delete {
    background-color: #dc3545;
}

button.newOp {
    background-color: #28a745;
}

button.back {
    background-color: #6c757d;
    font-size: 20px;
    margin-bottom: 20px;
    padding: 10px 20px;
}

button.new {
    background-color: #28a745;
    font-size: 20px;
    margin-bottom: 20px;
    padding: 10px 20px;
}

button.save {
    background-color: #17a2b8;
    font-size: 20px;
    margin-bottom: 20px;
    padding: 10px 20px;
}

button:hover {
    opacity: 0.8;
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

.radio-group {
    display: flex;
    flex-direction: column;
    margin-bottom: 20px;
}

.radio-group label {
    margin-bottom: 10px;
}
</style>
