<template>
  <div class="w-full h-screen p-10">
    <button class="btn btn-primary" @click="buttonClick">连接钱包账户</button>
  </div>
</template>

<script setup>
import { connectMetamask } from '../assets/login'
const emit = defineEmits(['submit'])

const buttonClick = async () => {
  const account = await connectMetamask()
  if (!account) {
    console.log('账户连接错误')
    return
  }
  emit('submit', { account })
}
</script>
