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
      <div class="flex">
        <div class="flex items-center w-20 justify-end">
          <span>学生:</span>
        </div>
        <input
          type="text"
          placeholder="请输入学生地址"
          class="input input-bordered w-full max-w-xs m-2"
          v-model="studentAddress"
        />
      </div>
      <button
        class="btn btn-primary m-2"
        @click="query"
      >
        查询
      </button>

    </div>
    <div class="overflow-x-auto">
      <table class="table w-full">
        <!-- head -->
        <thead>
          <tr>
            <th></th>
            <th>课程号</th>
            <th>学生地址</th>
            <th>分数</th>
          </tr>
        </thead>
        <tbody>
          <template v-for="(student, index) in students">
            <tr>
              <th>{{ index + 1 }}</th>
              <th>{{ student.courseID.courseID }}</th>
              <th>{{ student.user.address }}</th>
              <th>{{ student.score }}</th>
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
const students = reactive([])
const courseId = ref('')
const studentAddress = ref('')

const query = async () => {
  const res = await request.backend.getScore(courseId.value,studentAddress.value)
  if (!res) {
    ElMessage({
      showClose: true,
      message: h('p', null, '未查询到该学生成绩，请检查填写是否有误 '),
    })
    return
  }
  for (let i = 0, len = students.length; i < len; i++) {
    if (students[i].studentAddress == studentAddress.value) {
      ElMessage({
        showClose: true,
        message: h('p', null, '该学生成绩已在列表中'),
      })
      return
    }
  }
  students.push(res)
}

</script>
