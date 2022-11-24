// router/axios.ts
import {createRouter, createWebHistory} from "vue-router";
import Dashboard from "@/Pages/Dashboard";
import Login from "@/Pages/Auth/Login";
import ListProjects from "@/Pages/Projects/ListProjects";
import OrderCreate from "@/Pages/Order/OrderCreate";
import OrderView from "@/Pages/Order/OrderView";
import AuthenticationService from "@/Services/AuthenticationService";
import VerifyCode from "@/Components/Auth/Twofactor/VerifyCode";
import Profile from "@/Pages/Account/Profile";
import Settings from "@/Components/User/2fa/Settings";
const IsAuthenticated = (to, from, next) => {

    if (to.name !== 'auth:login' && !AuthenticationService.isLoggedIn()) next({ name: 'auth:login' })
    else next()
}

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
            path: '/auth/verify',
            name: 'auth:verify',
            component: VerifyCode,
            meta: {
                layout: 'AuthLayout', // we add new meta layout here to use it later,
                title: 'Verify twofactor',
            },
        },
        {
            path: '/account/me',
            name: 'user:me',
            component: Profile,
            meta: {
                title: 'Profile',
            },
        },
        {
            path: '/account/2fa/settings',
            name: 'user:2fa',
            beforeEnter: IsAuthenticated,
            component: Settings,
            meta: {
                pageTitle: 'Account | Twofactor',
                title: 'Two factor settings',
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
            name: 'dashboard',
            beforeEnter: IsAuthenticated,
            meta: {
                layout: 'AdminLayout', // Not needed, but you can add it here DashboardLayout will automatically be used
                pageTitle: 'Dashboard',
                title: 'Graphs & Charts',
                subtitle: 'Statistics'
            },
        },
        {
            path: '/orders',
            beforeEnter: IsAuthenticated,
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
            beforeEnter: IsAuthenticated,
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
            beforeEnter: IsAuthenticated,
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