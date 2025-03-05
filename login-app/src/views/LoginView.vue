<template>
  <div class="login-container">
    <div class="login-form">
      <div class="tabs">
        <div 
          class="tab" 
          :class="{ active: activeTab === 'login' }" 
          @click="activeTab = 'login'"
        >
          登录
        </div>
        <div 
          class="tab" 
          :class="{ active: activeTab === 'register' }" 
          @click="activeTab = 'register'"
        >
          注册
        </div>
      </div>
      
      <!-- 登录表单 -->
      <form v-if="activeTab === 'login'" @submit.prevent="handleLogin">
        <div class="form-group">
          <label for="username">用户名:</label>
          <input 
            type="text" 
            id="username" 
            v-model="loginForm.username" 
            required 
            placeholder="请输入用户名"
          />
        </div>
        <div class="form-group">
          <label for="password">密码:</label>
          <input 
            type="password" 
            id="password" 
            v-model="loginForm.password" 
            required 
            placeholder="请输入密码"
          />
        </div>
        <div class="form-options">
          <label class="remember-me">
            <input type="checkbox" v-model="loginForm.remember" />
            <span>记住我</span>
          </label>
          <a href="#" class="forgot-password">忘记密码?</a>
        </div>
        <div class="error-message" v-if="loginError">{{ loginError }}</div>
        <button type="submit" :disabled="isLoading">
          {{ isLoading ? '登录中...' : '登录' }}
        </button>
      </form>
      
      <!-- 注册表单 -->
      <form v-if="activeTab === 'register'" @submit.prevent="handleRegister">
        <div class="form-group">
          <label for="reg-username">用户名:</label>
          <input 
            type="text" 
            id="reg-username" 
            v-model="registerForm.username" 
            required 
            placeholder="请输入用户名"
          />
        </div>
        <div class="form-group">
          <label for="reg-email">邮箱:</label>
          <input 
            type="email" 
            id="reg-email" 
            v-model="registerForm.email" 
            required 
            placeholder="请输入邮箱"
          />
        </div>
        <div class="form-group">
          <label for="reg-password">密码:</label>
          <input 
            type="password" 
            id="reg-password" 
            v-model="registerForm.password" 
            required 
            placeholder="请输入密码"
          />
        </div>
        <div class="form-group">
          <label for="reg-confirm-password">确认密码:</label>
          <input 
            type="password" 
            id="reg-confirm-password" 
            v-model="registerForm.confirmPassword" 
            required 
            placeholder="请再次输入密码"
          />
        </div>
        <div class="error-message" v-if="registerError">{{ registerError }}</div>
        <button type="submit" :disabled="isLoading">
          {{ isLoading ? '注册中...' : '注册' }}
        </button>
      </form>
      
      <div class="social-login" v-if="activeTab === 'login'">
        <p>或者使用以下方式登录</p>
        <div class="social-icons">
          <button class="social-icon" title="微信登录">
            <i class="icon-wechat">微信</i>
          </button>
          <button class="social-icon" title="QQ登录">
            <i class="icon-qq">QQ</i>
          </button>
          <button class="social-icon" title="微博登录">
            <i class="icon-weibo">微博</i>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'LoginView',
  data() {
    return {
      activeTab: 'login',
      isLoading: false,
      loginError: '',
      registerError: '',
      loginForm: {
        username: '',
        password: '',
        remember: false
      },
      registerForm: {
        username: '',
        email: '',
        password: '',
        confirmPassword: ''
      }
    };
  },
  methods: {
    async handleLogin() {
      try {
        this.isLoading = true;
        this.loginError = '';
        
        // 添加请求头和错误处理
        const response = await axios.post('http://139.159.140.130:8080/api/auth/login', {
          username: this.loginForm.username,
          password: this.loginForm.password
        }, {
          headers: {
            'Content-Type': 'application/json'
          },
          timeout: 5000 // 5秒超时
        });
        
        console.log('登录响应:', response.data); // 调试日志
        
        // 检查响应格式
        if (response.data && response.data.token) {
          // 存储token
          localStorage.setItem('token', response.data.token);
          if (this.loginForm.remember) {
            localStorage.setItem('rememberedUser', this.loginForm.username);
          }
          
          console.log('准备跳转到查询页面');
          // 使用 await 确保导航完成
          await this.$router.push({
            name: 'query'
          });
          console.log('导航完成');
        } else {
          this.loginError = '登录失败：服务器响应格式错误';
          console.error('响应格式错误:', response.data);
        }
      } catch (error) {
        console.error('完整错误信息:', error);
        
        if (error.response) {
          // 服务器响应了错误状态码
          this.loginError = `登录失败: ${error.response.data?.message || error.response.statusText || '服务器错误'}`;
        } else if (error.request) {
          // 请求发出但没有收到响应
          this.loginError = '登录失败: 无法连接到服务器，请检查网络连接';
        } else {
          // 请求配置出错
          this.loginError = '登录失败: ' + error.message;
        }
      } finally {
        this.isLoading = false;
      }
    },
    async handleRegister() {
      try {
        // 验证两次密码是否一致
        if (this.registerForm.password !== this.registerForm.confirmPassword) {
          this.registerError = '两次输入的密码不一致';
          return;
        }
        
        this.isLoading = true;
        this.registerError = '';
        
        // 模拟注册成功
        setTimeout(() => {
          alert('注册成功！请登录');
          this.activeTab = 'login';
          this.loginForm.username = this.registerForm.username;
          
          // 清空注册表单
          this.registerForm.username = '';
          this.registerForm.email = '';
          this.registerForm.password = '';
          this.registerForm.confirmPassword = '';
        }, 1000);
        
      } catch (error) {
        this.registerError = '注册失败: ' + (error.response?.data?.message || '未知错误');
      } finally {
        this.isLoading = false;
      }
    }
  }
};
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.login-form {
  width: 400px;
  background-color: white;
  border-radius: 10px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.tabs {
  display: flex;
  border-bottom: 1px solid #eee;
}

.tab {
  flex: 1;
  text-align: center;
  padding: 15px 0;
  font-weight: 600;
  color: #666;
  cursor: pointer;
  transition: all 0.3s ease;
}

.tab.active {
  color: #4caf50;
  border-bottom: 2px solid #4caf50;
}

form {
  padding: 30px;
}

.form-group {
  margin-bottom: 20px;
}

label {
  display: block;
  margin-bottom: 8px;
  font-weight: 500;
  color: #333;
}

input[type="text"],
input[type="password"],
input[type="email"] {
  width: 100%;
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
  transition: border 0.3s ease;
  box-sizing: border-box;
}

input[type="text"]:focus,
input[type="password"]:focus,
input[type="email"]:focus {
  border-color: #4caf50;
  outline: none;
}

.form-options {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.remember-me {
  display: flex;
  align-items: center;
  cursor: pointer;
}

.remember-me input {
  margin-right: 5px;
}

.forgot-password {
  color: #4caf50;
  text-decoration: none;
  font-size: 14px;
}

.forgot-password:hover {
  text-decoration: underline;
}

button {
  width: 100%;
  padding: 12px;
  background-color: #4caf50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
  font-weight: 600;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #45a049;
}

button:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}

.error-message {
  color: #f44336;
  margin: 10px 0;
  font-size: 14px;
}

.social-login {
  padding: 0 30px 30px;
  text-align: center;
}

.social-login p {
  color: #666;
  margin-bottom: 15px;
  position: relative;
}

.social-login p::before,
.social-login p::after {
  content: "";
  position: absolute;
  top: 50%;
  width: 30%;
  height: 1px;
  background-color: #eee;
}

.social-login p::before {
  left: 0;
}

.social-login p::after {
  right: 0;
}

.social-icons {
  display: flex;
  justify-content: center;
  gap: 15px;
}

.social-icon {
  width: auto;
  padding: 8px 15px;
  border-radius: 4px;
  background-color: #f5f5f5;
  color: #333;
  font-size: 14px;
  transition: all 0.3s ease;
}

.social-icon:hover {
  background-color: #e0e0e0;
}

@media (max-width: 480px) {
  .login-form {
    width: 90%;
  }
}
</style> 