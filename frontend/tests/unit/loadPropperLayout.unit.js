import { mount } from '@vue/test-utils'
import App from '@/App.vue';
import Login from '@/Pages/Auth/Login.vue';
import Router from "@/Router/router";
let wrapper;

beforeEach(async function() {
    // eslint-disable-next-line no-undef
    wrapper = mount(App, {
        global: {
            plugins: [Router]
        }
    });
    await Router.isReady();
});
describe('Loads guest layout', () => {
    it('Login is shown', () => {
        //const wrapper = shallowMount(Login)
        expect(wrapper.find(Login).exists()).toBe(true);
    })
})

