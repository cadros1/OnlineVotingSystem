<template>
    <div class="background">
        <div class="container">
            <div class="left">
                <Sidebar></Sidebar>
            </div>
            <div class="right">
                <div class="buttons">
                    <div class="actions">
                        <button class="back" @click="$router.push('/ask')">Back</button>
                        <button class="new" @click="addItem">New</button>
                        <button class="save" @click="saveItems">Save</button>
                    </div>
                </div>
                <div class="content">
                    <!-- 新建和保存按钮 -->
                    <table class="table">
                        <thead>
                            <tr>
                                <th>题号</th>
                                <th>问题</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="item in items" :key="item.id">
                                <td>{{ item.id }}</td>
                                <td>{{ item.name }}</td>
                                <td>
                                    <button class="edit" @click="editItem(item)">Edit</button>
                                    <button class="delete" @click="deleteItem(item)">Delete</button>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <router-view />
        </div>
    </div>
</template>

<script setup>
import Sidebar from './sidebar.vue';
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

const items = ref([]);

// 模拟数据加载
onMounted(() => {
    // 这里可以调用API获取数据
    items.value = [
        { id: 1, name: 'Item 1', time: '2024-07-20' },
        { id: 2, name: 'Item 2', time: '2024-07-19' },
        // 更多数据...
    ];
});

function editItem(item) {
    // 使用 Vue Router 导航到编辑页面
    router.push({ path: 'EditQuestion', params: { questionId: item.id } });
}

function deleteItem(item) {
    // 删除功能实现
    const index = items.value.findIndex(i => i.id === item.id);
    if (index !== -1) {
        items.value.splice(index, 1);
    }
}

function addItem() {
    // 新增功能实现
    items.value.push({ id: Date.now(), name: '' });
}

function saveItems() {
    // 保存功能实现
    console.log('Saving items:', items.value);
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

button {
    padding: 8px 16px;
    font-size: 14px;
    cursor: pointer;
    border: none;
    border-radius: 4px;
    color: #fff;
}

button.edit {
    background-color: #007bff;
    margin-right: 10px;
}

button.delete {
    background-color: #dc3545;
}

button.back {
    background-color: #6c757d;
    margin-bottom: 10px;
    margin-left: 10px;
}

button.new {
    background-color: #28a745;
    margin-bottom: 10px;
    margin-left: 10px;
}

button.save {
    background-color: #17a2b8;
    margin-bottom: 10px;
    margin-left: 10px;
}

button:hover {
    opacity: 0.8;
}
</style>