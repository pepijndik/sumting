import {shallowMount, mount} from '@vue/test-utils';
import OrderSubItem from '@/Components/Form/SubItems/OrderSubItem.vue';
import Product from "@/Models/Product";
import {computed} from "vue";
import Checkbox from "@/Components/Form/Checkbox.vue"; // name of your Vue component

let wrapper;

/**
 * Tests the OrderSubItem component
 * @param computed
 * @returns {*}
 * @author Colin Laan
 * Also sets up a product mock to work with
 */
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
        stubs: {
            'Checkbox': Checkbox,
        },
    });
};

/**
 * Creates a new wrapper before every test
 * @author Colin Laan
 */
beforeEach(() => {
    wrapper = factory();
});

/**
 * After every test, the wrapper is destroyed
 * @author Colin Laan
 */
afterEach(() => {
    wrapper.unmount();
});

/**
 * Tests the OrderSubItem component
 * @author Colin Laan
 */
describe('OrderSubItem', () => {
    /**
     * Tests if the component is created with the correct product in its props
     * @author Colin Laan
     */
    test('ShouldRenderTheDefinedProduct', () => {
        wrapper = factory();
        const expectedName = 'test';

        const productName = wrapper.props().product.name;

        expect(productName).toBe(expectedName);
    });

    /**
     * Tests if the component is created with the correct description in its html
     * @author Colin Laan
     */
    test('ShouldRenderProjectDescription', () => {
        wrapper = factory();
        const expectedDescription = 'Project: a test project'

        const paragraphText = wrapper.find('p').text()

        expect(paragraphText).toContain(expectedDescription);
    });
    /**
     * Tests if the component is created with the correct description in its html
     * @author Colin Laan
     */
    test("CanSelectCustomNote", async () => {
        wrapper = factory();
        const expectedNote = 'test product description';

        await wrapper.find('[name=custom_note]').trigger('update');

        expect(wrapper.vm.customNote).toBeTruthy();
        expect(wrapper.vm.product.notes).toBe(expectedNote);
    });
    /**
     * Tests if the component can remove a product from the order
     * @author Colin Laan
     */
    test("CanEmitDeleteSelf", async () => {
       wrapper = factory();
       const expectedLength = 1;

       await wrapper.find('close-icon-stub').trigger('click');

       expect(wrapper.emitted().deleteProduct).toHaveLength(expectedLength);
    });
    /**
     * Tests if the numeric input is prefilled with the correct value
     * @author Colin Laan
     */
    test("InputContainsCorrectPrefilledValue", async () => {
        wrapper = factory();
        const expectedName = "name=\"amount\"";
        const expectedInputData = "inputdata=\"1\""

        console.log(wrapper.find('input-component-numeric-stub').html());

        expect(wrapper.find('input-component-numeric-stub').html()).toContain(expectedName);
        expect(wrapper.find('input-component-numeric-stub').html()).toContain(expectedInputData);
    });

});