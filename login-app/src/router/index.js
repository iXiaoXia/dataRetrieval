import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue'
import QueryView from '../views/QueryView.vue'

const routes = [
  {
    path: '/',
    name: 'login',
    component: LoginView
  },
  {
    path: '/query',
    name: 'query',
    component: QueryView
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 导航守卫
router.beforeEach((to, from, next) => {
  console.log('Navigation to:', to.path); // 添加导航日志
  const token = localStorage.getItem('token')
  
  if (to.path === '/query' && !token) {
    console.log('No token found, redirecting to login');
    next('/')
  } else {
    console.log('Proceeding with navigation');
    next()
  }
})

export default router 