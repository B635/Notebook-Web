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
        component: () => import('../view/home')
    },
    {
        path: '/note/list',
        name: 'Note',
        component: () => import('../view/note')
    },
    {
        path: '/note/write',
        name: 'Write',
        component: () => import('../view/writePage')
    },
    {
        path: '/note/category',
        name: 'Category',
        component: () => import('../view/category')
    },
    {
        path: '/note/tag',
        name: 'Tag',
        component: () => import('../view/tag')
    },
    {
        path: '/attachment',
        name: 'Attachment',
        component: () => import('../view/attachment')
    },
    {
        path: '/profile',
        name: 'Profile',
        component: () => import('../view/profile')
    },
]

const router = new VueRouter({
    routes
})

export default router
