import {mount, shallowMount} from '@vue/test-utils'
import App from '@/App.vue';
import Login from '@/Pages/Auth/Login.vue';
import Router from "@/Router/router";
import AuthenticationService from "@/Services/AuthenticationService";
import {reactive} from "vue";
let wrapper;
let auth = new AuthenticationService();
beforeEach(async function() {
    // eslint-disable-next-line no-undef
    wrapper = mount(App, {
        global: {
            plugins: [Router],
            provide: {
                Auth: reactive(auth),
            }
        }
    });

    await Router.isReady();
});
describe('Loads guest layout', () => {
    it('Login is shown', () => {
        const wrapper = shallowMount(Login,{
            global: {
                provide: {
                    Auth: reactive(auth),
                }
            }
        })
        const LoginComp = wrapper.findComponent({ name: 'login' })
        expect(LoginComp.exists()).toBe(true)
    })
})

