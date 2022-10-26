// router/index.js
import {createRouter, createWebHistory} from "vue-router";
import Dashboard from "@/Pages/Dashboard";
import Login from "@/Pages/Auth/Login";
import ListProjects from "@/Pages/Projects/ListProjects";
import OrderCreate from "@/Pages/Order/OrderCreate";
import OrderView from "@/Pages/Order/OrderView";

const router = createRouter({
    history: createWebHistory(),
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
                title: 'Sign up',
            },
        },
        {
            path: '/',
            component: Dashboard,
            name: 'dashboard:home',
            meta: {
                layout: 'AdminLayout', // Not needed, but you can add it here DashboardLayout will automatically be used
                pageTitle: 'Dashboard',
                title: 'Graphs & Charts',
                subtitle: 'Statistics'
            },
        },
        {
            path: '/orders',
            name: 'admin:Order',
            meta: {
                pageTitle: 'Orders',
                title: 'Client order',
                subtitle: 'View'
            },
            component: OrderView
        },
        {
            path: '/orders/create',
            name: 'admin:OrderCreate',
            meta: {
                pageTitle: 'Create order',
                title: 'Client order',
                subtitle: 'Create'
            },
            component: OrderCreate
        },
        {
            path: '/projects',
            name: 'admin:projects',
            meta: {
                pageTitle: 'Projects',
                title: 'Project',
                subtitle: 'View'
            },
            component: ListProjects
        }
    ]
})

router.beforeEach((to, from, next) => {
    window.document.title = to.meta && to.meta?.pageTitle   ?  `${to.meta.pageTitle} | Sumting` : 'Sumting';
    next();
});

export default router