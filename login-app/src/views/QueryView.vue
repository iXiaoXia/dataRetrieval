<template>
  <div class="query-container">
    <header class="query-header">
      <h1>数据查询系统</h1>
    </header>

    <main class="query-main">
      <!-- 搜索区域 -->
      <div class="search-area">
        <div class="search-box">
          <input 
            type="text" 
            v-model="searchQuery" 
            placeholder="请输入查询关键字"
            @keyup.enter="handleSearch"
          />
          <button @click="handleSearch" :disabled="isSearching">
            {{ isSearching ? '查询中...' : '查询' }}
          </button>
        </div>
        <!-- 图片上传区域 -->
        <div class="upload-area" 
             @dragover.prevent 
             @drop.prevent="handleDrop"
             :class="{ 'dragging': isDragging }">
          <div class="upload-content">
            <i class="upload-icon">📁</i>
            <p>拖拽图片到这里</p>
            <p class="upload-hint">仅支持256k以下.ico.png.jpg格式文件</p>
            <input 
              type="file" 
              ref="fileInput" 
              accept=".ico,.png,.jpg"
              @change="handleFileSelect" 
              style="display: none"
            />
            <button @click="triggerFileInput">本地上传</button>
          </div>
        </div>
      </div>

      <!-- 查询语法提示 -->
      <div class="query-syntax">
        <h3>查询语法参考</h3>
        <div class="syntax-content">
          <p>关键字查询示例：</p>
          <ul>
            <li>domain="example.com"</li>
            <li>ip="1.1.1.1"</li>
            <li>port="80"</li>
          </ul>
        </div>
      </div>

      <!-- 查询结果 -->
      <div class="query-results" v-if="searchResults.length > 0">
        <h3>查询结果</h3>
        <div class="results-grid">
          <div v-for="(result, index) in searchResults" 
               :key="index" 
               class="result-card">
            <h4>{{ result.title }}</h4>
            <p>{{ result.description }}</p>
            <div class="result-meta">
              <span>IP: {{ result.ip }}</span>
              <span>端口: {{ result.port }}</span>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'QueryView',
  data() {
    return {
      searchQuery: '',
      isSearching: false,
      isDragging: false,
      searchResults: [],
      selectedFile: null
    };
  },
  methods: {
    async handleSearch() {
      if (!this.searchQuery.trim()) return;
      
      try {
        this.isSearching = true;
        // 这里替换为您的实际 API 端点
        const response = await axios.post('http://139.159.140.130:8080/api/search', {
          query: this.searchQuery
        });
        
        this.searchResults = response.data.results;
      } catch (error) {
        console.error('搜索错误:', error);
        alert('搜索失败，请重试');
      } finally {
        this.isSearching = false;
      }
    },
    
    triggerFileInput() {
      this.$refs.fileInput.click();
    },
    
    handleDrop(e) {
      this.isDragging = false;
      const file = e.dataTransfer.files[0];
      if (file) this.processFile(file);
    },
    
    handleFileSelect(e) {
      const file = e.target.files[0];
      if (file) this.processFile(file);
    },
    
    processFile(file) {
      if (!['image/png', 'image/jpeg', 'image/x-icon'].includes(file.type)) {
        alert('只支持 .ico, .png, .jpg 格式的文件');
        return;
      }
      
      if (file.size > 256 * 1024) {
        alert('文件大小不能超过 256KB');
        return;
      }
      
      this.selectedFile = file;
      // 这里可以添加文件上传逻辑
    }
  }
};
</script>

<style scoped>
.query-container {
  min-height: 100vh;
  background-color: #f5f5f5;
  padding: 20px;
}

.query-header {
  text-align: center;
  margin-bottom: 30px;
}

.query-header h1 {
  color: #333;
  font-size: 24px;
}

.search-area {
  max-width: 800px;
  margin: 0 auto;
}

.search-box {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}

.search-box input {
  flex: 1;
  padding: 12px;
  border: 2px solid #ddd;
  border-radius: 4px;
  font-size: 16px;
}

.search-box button {
  padding: 12px 24px;
  background-color: #4caf50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
}

.search-box button:disabled {
  background-color: #cccccc;
}

.upload-area {
  border: 2px dashed #ddd;
  border-radius: 8px;
  padding: 30px;
  text-align: center;
  background-color: white;
  transition: all 0.3s ease;
}

.upload-area.dragging {
  border-color: #4caf50;
  background-color: #f0f9f0;
}

.upload-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
}

.upload-icon {
  font-size: 40px;
  margin-bottom: 10px;
}

.upload-hint {
  color: #666;
  font-size: 14px;
}

.query-syntax {
  max-width: 800px;
  margin: 30px auto;
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.syntax-content {
  margin-top: 10px;
}

.syntax-content ul {
  list-style: none;
  padding-left: 20px;
}

.syntax-content li {
  margin: 5px 0;
  color: #666;
}

.query-results {
  max-width: 800px;
  margin: 30px auto;
}

.results-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  gap: 20px;
  margin-top: 20px;
}

.result-card {
  background-color: white;
  padding: 15px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.result-card h4 {
  margin: 0 0 10px 0;
  color: #333;
}

.result-meta {
  margin-top: 10px;
  display: flex;
  gap: 15px;
  font-size: 14px;
  color: #666;
}

@media (max-width: 600px) {
  .search-box {
    flex-direction: column;
  }
  
  .results-grid {
    grid-template-columns: 1fr;
  }
}
</style> 