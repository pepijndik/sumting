// router/index.js
import {createRouter, createWebHistory} from "vue-router";
import Dashboard from "@/Pages/Dashboard";
import Login from "@/Pages/Auth/Login";
import ListProjects from "@/Pages/Projects/ListProjects";
import OrderCreate from "@/Pages/Order/OrderCreate";

const router = createRouter({
    history: createWebHistory(),
    beforeEach(toRoute, fromRoute, next) {
        window.document.title = toRoute.meta && toRoute.meta.title ? toRoute.meta.title : 'Home';
        next();
    },
    routes: [
        {
            path: '/login',
            name: 'auth:login',
            component: Login,
            meta: {
                layout: 'AuthLayout', // we add new meta layout here to use it later
            },
        },
        {
            path: '/sign-up',
            name: 'auth:sign_up',
            meta: {
                layout: 'AuthLayout', // same here
            },
        },
        {
            path: '/',
            component: Dashboard,
            meta: {
                layout: 'AdminLayout', // Not needed, but you can add it here DashboardLayout will automatically be used
            },
            name: 'dashboard:home',
        },
        {
            path: '/admin/projects',
            name: 'admin:projects',
            component: ListProjects
        },
        {
            path: '/admin/orders',
            name: 'admin:Order',
            component: OrderCreate
        }
    ]
})

export default router