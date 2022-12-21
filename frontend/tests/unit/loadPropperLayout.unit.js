import {mount, shallowMount} from '@vue/test-utils'
import App from '@/App.vue';
import Login from '@/Pages/Auth/Login.vue';
import Router from "@/Router/router";
import AuthenticationService from "@/Services/AuthenticationService";
import BaseApi from "@/Services/BaseApi";
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
                axios: BaseApi
            },
            localStorage: {
                state: {
                    'token': ''
                },
                setItem (key, item) {
                    this.state[key] = item
                },
                getItem (key) {
                    return this.state[key]
                }
            }
        }
    });
    await wrapper.vm.$router.isReady();

});

afterEach( function(){
    wrapper.unmount();
    wrapper = null;
})
describe('Loads layout', () => {
    it("User is not logged in", () => {
        expect(AuthenticationService.isLoggedIn()).toBe(false);
    });

    it('Login is shown when not authenticated', () => {
        expect(AuthenticationService.isLoggedIn()).toBe(false);
        const LoginComp = wrapper.findComponent({ name: 'login' })
        console.log(wrapper.html())
        expect(LoginComp.exists()).toBe(true)
    });
})

