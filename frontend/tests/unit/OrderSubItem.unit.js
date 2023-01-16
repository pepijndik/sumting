import {shallowMount, mount} from '@vue/test-utils';
import OrderSubItem from '@/Components/Form/SubItems/OrderSubItem.vue';
import Product from "@/Models/Product";
import {computed} from "vue"; // name of your Vue component

let wrapper;
const factory = (computed = {}) => {
    return shallowMount(OrderSubItem, {
        props: {
            product: Product.copyConstructor({
                    id: 1,
                    name: 'test',
                    notes: "test product description",
                    price: 1,
                    project: {
                        id: "1",
                        name: "test project",
                        description: "a test project",
                        description_long: "a long description",
                        createdAt: "2019-01-01T00:00:00.000Z",
                        updatedAt: "2019-01-01T00:00:00.000Z",
                        longitude: 1,
                        latitude: 1,
                        image: null
                    },
                    type: {
                        id: 1,
                        product_type_name: "test product name",
                        description: "test product description",
                        frequency: 1,
                        createdAt: "2019-01-01T00:00:00.000Z"
                    },
                    createdAt: "2019-01-01T00:00:00.000Z",
                    updatedAt: "2019-01-01T00:00:00.000Z",
                    id_ext: null,
                    active: true
                }),
            index: 0,
        },
        stubs: ['Checkbox'],
    });
};
beforeEach(() => {
    wrapper = factory();
});

afterEach(() => {
    wrapper.unmount();
});

describe('OrderSubItem', () => {
    test('ShouldRenderTheDefinedProduct', () => {
        wrapper = factory();
        expect(wrapper.props().product.name).toBe('test');
    });
    test('ShouldRenderProjectDescription', () => {
        wrapper = factory();

        console.log(wrapper.html());

        expect(wrapper.find('p').text()).toContain('Project: a test project');
    });
    test("CanSelectCustomNote", async () => {
        wrapper = factory();

        await wrapper.find('[name=custom_note]').trigger('update');

        expect(wrapper.vm.customNote).toBeTruthy();
    });
});