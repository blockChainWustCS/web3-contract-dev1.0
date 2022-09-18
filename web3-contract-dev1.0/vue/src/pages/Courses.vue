<template>
  <div class="p-5">
    <div class="flex items-center">
      <div class="flex">
        <div class="flex items-center w-20 justify-end">
          <span>课程号:</span>
        </div>
        <input
          type="text"
          placeholder="请输入课程号"
          class="input input-bordered w-full max-w-xs m-2"
          v-model="courseId"
        />
      </div>
      <div class="flex" v-show="addCourseIndex">
        <div class="flex items-center w-20 justify-end">
          <span>课程名:</span>
        </div>
        <input
          type="text"
          placeholder="请输入课程名"
          class="input input-bordered w-full max-w-xs m-2"
          v-model="courseName"
        />
      </div>
      <div class="flex" v-show="addCourseIndex">
        <div class="flex items-center w-20 justify-end">
          <span class="w-20">任课老师:</span>
        </div>
        <input
          type="text"
          placeholder="请输入任课老师地址"
          class="input input-bordered w-full max-w-xs m-2"
          v-model="courseTeacher"
        />
      </div>
      <button
        class="btn btn-primary m-2"
        @click="query"
        v-show="!addCourseIndex"
      >
        查询
      </button>
      <button
        class="btn btn-primary m-2"
        @click="addCourse"
        v-show="!addCourseIndex"
      >
        编辑课程
      </button>
      <button
        class="btn btn-primary m-2"
        @click="editCourseTeacher"
        v-show="addCourseIndex"
      >
        更换老师
      </button>
      <button
        class="btn btn-primary m-2"
        @click="emitCourse"
        v-show="addCourseIndex"
      >
        新课程
      </button>
      <button class="btn btn-primary m-2" @click="back" v-show="addCourseIndex">
        返回
      </button>
    </div>
    <div class="overflow-x-auto">
      <table class="table w-full">
        <!-- head -->
        <thead>
          <tr>
            <th></th>
            <th>课程号</th>
            <th>课程名</th>
            <th>任课老师</th>
          </tr>
        </thead>
        <tbody>
          <template v-for="(course, index) in courses">
            <tr>
              <th>{{ index + 1 }}</th>
              <th>{{ course.courseID }}</th>
              <th>{{ course.name }}</th>
              <th>{{ course.teacherAddress }}</th>
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
import { ElMessage } from 'element-plus'
import { contractManager } from '../assets/api/ContractManager'
const courses = reactive([])
const courseId = ref('')
const courseName = ref('')
const courseTeacher = ref('')
const addCourseIndex = ref(false)

const query = async () => {
  const res = await request.backend.getCourse(courseId.value)
  if (!res) {
    ElMessage({
      showClose: true,
      message: h('p', null, '未查询到该课程，请检查填写是否有误 '),
    })
    return
  }
  for (let i = 0, len = courses.length; i < len; i++) {
    if (courseId.value == res.courseId) {
      ElMessage({
        showClose: true,
        message: h('p', null, '该课程已在列表中 '),
      })
      return
    }
  }
  courses.push(res)
}
const addCourse = () => {
  addCourseIndex.value = true
}
const back = () => {
  addCourseIndex.value = false
}
const emitCourse = async () => {
  const ifCourseExist = await request.backend.getCourse(courseId.value)
  if (ifCourseExist) {
    ElMessage({
      showClose: true,
      message: h('p', null, '该课程已在库中 '),
    })
    return
  }
  //没有填写老师，只需要向后端发请求
  if (!courseTeacher.value) {
    let res = await request.backend.newCourse({
      courseID: courseId.value,
      name: courseName.value,
      teacherAddress: null,
    })
  } else {
    //填写老师，需要向链端和后端发请求
    let res = await request.backend.newCourse({
      courseID: courseId.value,
      name: courseName.value,
      teacherAddress: courseTeacher.value,
    })
    await contractManager.setCourseTeacher(courseTeacher.value, courseId.value)
  }
}
const editCourseTeacher = async () => {
  const ifCourseExist = await request.backend.getCourse(courseId.value)
  if (!ifCourseExist) {
    ElMessage({
      showClose: true,
      message: h('p', null, '该课程未在库中 '),
    })
    return
  }
  await contractManager.setCourseTeacher(courseTeacher.value, courseId.value)
}
</script>
