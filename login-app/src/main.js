import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'

// Axios 全局配置
axios.defaults.baseURL = 'http://139.159.140.130:8080'
axios.defaults.timeout = 5000
axios.defaults.headers.common['Content-Type'] = 'application/json'

// 请求拦截器
axios.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 响应拦截器
axios.interceptors.response.use(
  response => {
    return response
  },
  error => {
    if (error.response) {
      switch (error.response.status) {
        case 401:
          // 未授权，清除 token 并跳转到登录页面
          localStorage.removeItem('token')
          router.push('/')
          break
        case 403:
          // 权限不足
          console.error('权限不足')
          break
        case 500:
          console.error('服务器错误')
          break
        default:
          console.error(error.response.data?.message || '请求失败')
      }
    }
    return Promise.reject(error)
  }
)

const app = createApp(App)

// 确保先挂载路由
app.use(router)

// 全局错误处理
app.config.errorHandler = (err, vm, info) => {
  console.error('Vue 错误:', err)
  console.error('错误信息:', info)
}

// 挂载应用
app.mount('#app')

// 开发环境下输出路由信息
if (process.env.NODE_ENV === 'development') {
  console.log('路由配置:', router.getRoutes())
}
