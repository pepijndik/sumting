// router/index.js
import {createRouter, createWebHistory} from "vue-router";
import Dashboard from "@/Pages/Dashboard";
import Login from "@/Pages/Auth/Login";
import ListProjects from "@/Pages/Projects/ListProjects";
import OrderCreate from "@/Pages/Order/OrderCreate";
import OrderView from "@/Pages/Order/OrderView";

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
                layout: 'AuthLayout', // we add new meta layout here to use it later,
                title: 'Login',
            },
        },
        {
            path: '/sign-up',
            name: 'auth:sign_up',
            meta: {
                layout: 'AuthLayout', // same here
                title: 'Signup',
            },
        },
        {
            path: '/',
            component: Dashboard,
            meta: {
                layout: 'AdminLayout', // Not needed, but you can add it here DashboardLayout will automatically be used
                title: 'Dashboard',
            },
            name: 'dashboard:home',
        },
        {
            path: '/orders',
            name: 'admin:Order',
            meta: {

                title: 'OrderView',
            },
            component: OrderView
        },
        {
            path: '/orders/create',
            name: 'admin:OrderCreate',
            meta: {

                title: 'CreateOrder',
            },
            component: OrderCreate
        },
        {
            path: '/admin/projects',
            name: 'admin:projects',
            meta: {

                title: 'Projects',
            },
            component: ListProjects
        }
    ]
})

export default router