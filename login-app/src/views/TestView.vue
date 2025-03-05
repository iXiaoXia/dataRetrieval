<template>
  <div class="test-container">
    <h2>测试组件</h2>
    <p>如果您能看到这个文本，说明Vue组件正在正常工作。</p>
    <button @click="count++" class="test-button">点击了 {{ count }} 次</button>
  </div>
</template>

<script>
export default {
  name: 'TestView',
  data() {
    return {
      count: 0
    }
  }
}
</script>

<style scoped>
.test-container {
  max-width: 500px;
  margin: 0 auto;
  padding: 20px;
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.test-button {
  background-color: #4caf50;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
  margin-top: 20px;
}

.test-button:hover {
  background-color: #45a049;
}
</style> 