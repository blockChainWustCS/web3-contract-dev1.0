<template>
  <div class="flex h-screen w-full">
    <aside class="w-56 h-screen border-r-2 border-gray-500 flex flex-col">
      <div class="flex justify-center items-center h-16">
        <span class="text-2xl font-semibold tracking-widest">
          成绩上链系统
        </span>
      </div>
      <div class="w-full flex-grow">
        <ul class="menu bg-base-100 w-full">
          <li v-show="user.address" @click="changeActiveIndex(1)">
            <a>个人信息</a>
          </li>
          <li v-show="user.address" @click="changeActiveIndex(2)">
            <a>我的课程</a>
          </li>
          <li v-show="user.address" @click="changeActiveIndex(3)">
            <a>我的教学</a>
          </li>
          <li v-show="user.isAdmin" @click="changeActiveIndex(4)">
            <a>课程管理</a>
          </li>
          <li v-show="user.isAdmin" @click="changeActiveIndex(5)">
            <a>人员管理</a>
          </li>
        </ul>
      </div>
    </aside>
    <div class="flex-grow h-screen">
      <AccountsVue v-show="activeIndex == 0" @submit="login"></AccountsVue>
      <PersonalInfoVue
        v-show="activeIndex == 1"
        :info="user"
        @updateUser="getData()"
      ></PersonalInfoVue>
      <MyCourseStudentVue v-show="activeIndex == 2"></MyCourseStudentVue>
      <MyCourseTeacher
        v-show="activeIndex == 3"
        :address="user.address"
      ></MyCourseTeacher>
      <Courses v-show="activeIndex == 4"></Courses>
      <persons v-show="activeIndex == 5"></persons>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { request } from '../assets/api/request'
import AccountsVue from './Accounts.vue'
import PersonalInfoVue from './PersonalInfo.vue'
import MyCourseStudentVue from './MyCourseStudent.vue'
import MyCourseTeacher from './MyCourseTeacher.vue'
import Courses from './Courses.vue'
import Persons from './Persons.vue'

const activeIndex = ref(0)
const account = ref(null)
const user = reactive({
  address: null,
  isAdmin: false,
  num: null,
  name: null,
  age: null,
  url: null,
})
const login = async (content) => {
  account.value = content.account
  activeIndex.value = 1
  await getData()
  window.ethereum.on('accountsChanged', function (accounts) {
    if (accounts[0]) return
    //断开连接的操作
    activeIndex.value = 0
    account.value = null
    user.empty()
  })
}
const getData = async () => {
  const res = await request.backend.getUser(account.value) //先请求库，如果有该用户则获取到该用户的信息
  if (res) {
    //请求到用户信息，将用户信息填充到页面上
    Object.keys(res).forEach((key) => {
      user[key] = res[key]
    })
  }
  //未请求到用户信息，发送一个put，放入数据库
  else {
    //post一个新用户到库中
    await request.backend.newUser({
      address: account.value,
      isAdmin: false,
      name: null,
      num: null,
      age: null,
      url: null,
    })
    //获取这个新用户的信息填充到页面上
    await getData()
  }
}

const changeActiveIndex = (index) => {
  activeIndex.value = index
}

Array.prototype.empty = function () {
  for (let i = 0, len = this.length; i < len; i++) {
    this.shift()
  }
}
Object.prototype.empty = function () {
  Object.keys(this).forEach((key) => {
    if (!this[key]) return
    if (key === 'isAdmin') {
      this[key] = false
      return
    }
    this[key] = null
  })
}
</script>
