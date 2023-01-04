import {mount, shallowMount} from '@vue/test-utils'
import App from '@/App.vue';
import Login from '@/Pages/Auth/Login.vue';
import Router from "@/Router/router";
import AuthenticationService from "@/Services/AuthenticationService";
import BaseApi from "@/Services/BaseApi";
import {reactive} from "vue";
let wrapper;
let auth = new AuthenticationService();
import LoginResponse from "../mockingData/Login/LoginResponse.json";
beforeEach(async function() {
    // eslint-disable-next-line no-undef
    wrapper = mount(App, {
        global: {
            plugins: [Router],
            provide: {
                Auth: reactive(auth),
                axios: BaseApi,
            },
        },
        mocks: {

        }
    });
    await wrapper.vm.$router.isReady();

});

afterEach( function(){
    wrapper.unmount();
    wrapper = null;
})


jest.spyOn(BaseApi, 'post',).mockResolvedValue(LoginResponse);
describe('Loads layout', () => {

    it("User is not logged in", () => {
        expect(AuthenticationService.isLoggedIn()).toBe(false);
    });
    it('AuthLayout layout is shown when not authenticated', () => {
        expect(AuthenticationService.isLoggedIn()).toBe(false);
        expect(wrapper.findComponent({ name: 'AuthLayout' }).exists()).toBe(true)
    });

    it('Admin layout is shown when authenticated', () => {
        this.Auth.login("test", "test", false);
        expect(AuthenticationService.isLoggedIn()).toBe(true);
        expect(wrapper.findComponent({ name: 'DashboardLayout' }).exists()).toBe(true)
    });
})

