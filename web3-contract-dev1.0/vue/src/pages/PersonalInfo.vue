<template>
  <div class="w-full h-screen p-5">
    <div v-show="!editor">
      <div class="m-2">
        <span>欢迎您，</span>
        <span v-if="props.info.isAdmin">管理员</span>
        <span>{{ props.info.name }}</span>
      </div>
      <div class="m-2">
        <span>地址:</span>
        <span>{{ props.info.address }}</span>
      </div>
      <div class="m-2">
        <span>姓名:</span>
        <span>{{ props.info.name }}</span>
      </div>
      <div class="m-2">
        <span>编号:</span>
        <span>{{ props.info.num }}</span>
      </div>
      <div class="m-2">
        <span>年龄:</span>
        <span>{{ props.info.age }}</span>
      </div>
    </div>
    <div>
      <button class="btn btn-primary" v-show="!editor" @click="openEditor">
        修改信息
      </button>
      <div v-show="editor">
        <form>
          <div class="flex">
            <div class="flex items-center w-20 justify-end">
              <span>姓名:</span>
            </div>
            <input
              type="text"
              placeholder="请输入姓名"
              class="input input-bordered w-full max-w-xs m-2"
              v-model="user.name"
            />
          </div>
          <div class="flex">
            <div class="flex items-center w-20 justify-end">
              <span>年龄:</span>
            </div>
            <input
              type="text"
              placeholder="请输入年龄"
              class="input input-bordered w-full max-w-xs m-2"
              v-model="user.age"
            />
          </div>
          <div class="flex">
            <div class="flex items-center w-20 justify-end">
              <span>头像链接:</span>
            </div>
            <input
              type="text"
              placeholder="请输入头像链接"
              class="input input-bordered w-full max-w-xs m-2"
              v-model="user.url"
            />
          </div>
        </form>
        <div class="flex w-96 justify-center">
          <button class="btn btn-primary m-2" @click="emitUser">提交</button>
          <button class="btn btn-primary m-2" @click="back">返回</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, watch } from 'vue'
import { request } from '../assets/api/request'
const props = defineProps(['info'])
const updateUser = defineEmits(['updateUser'])

const user = reactive({
  name: null,
  age: null,
  url: null,
})

watch(props.info, (info) => {
  user.name = info.name
  user.age = info.age
  user.url = info.url
})

const editor = ref(false)
const openEditor = () => {
  editor.value = true
}
const emitUser = async () => {
  await request.backend.updateUser(props.info.address, {
    address: props.info.address,
    isAdmin: props.info.isAdmin,
    name: user.name,
    age: user.age,
    url: user.url,
  })

  updateUser('updateUser')
  editor.value = false
}
const back = () => {
  editor.value = false
  user.name = props.info.name
  user.age = props.info.age
  user.url = props.info.url
}
</script>
