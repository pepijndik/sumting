import {mount, shallowMount,flushPromises} from '@vue/test-utils'
import OrderCreate from "@/Pages/Order/OrderCreate.vue";
import Router from "@/Router/router";
import AuthenticationService from "@/Services/AuthenticationService";
import BaseApi from "@/Services/BaseApi";
import {reactive} from "vue";
import 'jest-canvas-mock';
import OrderApiService from "@/Services/Order/OrderApiService";
import ProjectApiService from "@/Services/Projects/ProjectApiService";
import UserApiService from "@/Services/User/UserApiService";
import Currency from "@/Services/Currency";

import Projects from "../mockingData/Project/Projects.json";
import LoginResponse from "../mockingData/Login/LoginResponse.json";
let wrapper;
let auth = new AuthenticationService();
beforeEach(async function() {
    // eslint-disable-next-line no-undef
    wrapper = mount(OrderCreate, {
        global: {
            plugins: [Router],
            provide: {
                Auth: reactive(auth),
                OrderApi: new OrderApiService(),
                ProjectApi: new ProjectApiService(),
                UserApi: new UserApiService(),
                CurrencyApi: new Currency(),
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

jest.mock('@/Services/BaseApi', () => {
    return {
        get: jest.fn(() => Promise.resolve({data: Projects})),
    }
});

describe('Order test', () => {

    it("CanCreateAOrderWithData", () => {
        expect(wrapper.find(".font-inter").text()).toBe("Client");
        jest.spyOn(BaseApi, 'get',).mockResolvedValue(Projects);
        wrapper.find("input[name=select_client]")
    });

});