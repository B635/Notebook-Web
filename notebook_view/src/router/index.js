import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from "@/view/Login";


Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Login',
        component: Login
    },
    {
        path: '/home',
        name: 'Home',
        component: () => import('../view/homePage')
    },
    {
        path: '/user',
        name: 'User',
        component: () => import('../view/userPage')
    },
    {
        path: '/admin',
        name: 'Admin',
        component: () => import('../view/adminPage')
    }
]

const router = new VueRouter({
    routes
})

export default router
