<template>
  <div class="p-5">
    <div class="flex items-center">
      <div class="flex">
        <div class="flex items-center w-20 justify-end">
          <span>地址:</span>
        </div>
        <input
          type="text"
          placeholder="请输入地址"
          class="input input-bordered w-full max-w-xs m-2"
          v-model="address"
        />
      </div>
      <button class="btn btn-primary m-2" @click="query">查询</button>
      <!-- <button class="btn btn-primary m-2" @click="setAdmin">
        设为管理员(上链)
      </button> -->
    </div>
    <div class="overflow-x-auto">
      <table class="table w-full">
        <!-- head -->
        <thead>
          <tr>
            <th></th>
            <th>姓名</th>
            <th>地址</th>
            <th>编号</th>
            <th>年龄</th>
            <th>是否为管理员</th>
          </tr>
        </thead>
        <tbody>
          <template v-for="(user, index) in users">
            <tr>
              <th>{{ index + 1 }}</th>
              <td>{{ user.name }}</td>
              <td>{{ user.address }}</td>
              <td>{{ user.num }}</td>
              <td>{{ user.age }}</td>
              <td>{{ user.isAdmin }}</td>
            </tr>
          </template>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, h } from 'vue'
import { request } from '../assets/api/request'
import { contractManager } from '../assets/api/ContractManager'
import { ElMessage } from 'element-plus'
const users = reactive([])
const address = ref(null)
const query = async () => {
  const res = await request.backend.getUser(address.value)
  if (!res) {
    ElMessage({
      showClose: true,
      message: h('p', null, '未查询到该用户，请检查地址是否有误 '),
    })
    return
  }
  for (let i = 0, len = users.length; i < len; i++) {
    if (users[i].address == res.address) {
      ElMessage({
        showClose: true,
        message: h('p', null, '该用户已在列表中 '),
      })
      return
    }
  }
  users.push(res)
}
const setAdmin = async () => {
  const res = await request.backend.getUser(address.value)
  await contractManager.setAdmin(address.value)
}
</script>
