<template>
  <div>
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
        <span>学生地址:</span>
      </div>
      <input
        type="text"
        placeholder="请输入学生地址"
        class="input input-bordered w-full max-w-xs m-2"
        v-model="address"
      />
    </div>
    <div class="flex">
      <div class="flex items-center w-20 justify-end">
        <span>分数:</span>
      </div>
      <input
        type="text"
        placeholder="请输入分数"
        class="input input-bordered w-full max-w-xs m-2"
        v-model="score"
      />
    </div>
    <div>
      <button class="btn btn-primary m-2" @click="emitScore">
        提交
      </button>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, h } from 'vue'
import { request } from '../assets/api/request'
import { ElMessage } from 'element-plus'
import { contractManager } from '../assets/api/ContractManager'

const props = defineProps(['address'])
const courseId = ref('')
const address = ref('')
const score = ref('')

const emitScore = async () => {
  let course = await request.backend.getCourse(courseId.value)
  if (!course) {
    ElMessage({
      showClose: true,
      message: h('p', null, '未查询到该课程，请检查填写是否有误 '),
    })
    return
  }
  if (course.teacherAddress != props.address) {
    ElMessage({
      showClose: true,
      message: h('p', null, '你不是这门课的任课老师'),
    })
    return
  }
  if (!(courseId.value && address.value)) {
    ElMessage({
      showClose: true,
      message: h('p', null, '请至少填写课程号和学生地址'),
    })
    return
  }
  await contractManager.setScore(address.value, courseId.value, score.value)
}
</script>
