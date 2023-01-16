import {shallowMount, mount} from '@vue/test-utils';
import SearchableDropdown from '@/Components/Form/SearchableDropdown.vue';
import {computed} from "vue"; // name of your Vue component

let wrapper;
const factory = (computed = {}) => {
    return shallowMount(SearchableDropdown, {
        props: {
            name: 'testdropdown',
            options: [
                {id: 1, name: 'test'},
                {id: 2, name: 'test2'},
                {id: 3, name: 'test3'},
            ],
            selectedItem: null,
            fields: ['name'],
            text: ['id', 'name'],
            primarykey: "id",
            maxItem: 10,
        },
    });
};
beforeEach(() => {
    wrapper = factory();
});

afterEach(() => {
    wrapper.unmount();
});

describe('Searchable dropdown', () => {
    test('ShouldRenderTheDefinedOptions', () => {
        wrapper = factory();
        expect(wrapper.props().options.length).toBe(3);
        expect(wrapper.findAll('.options').length).toBe(3);
    });
    test('CanOpenDropdownResultsInShownContent', () => {
        wrapper = factory();
        wrapper.find('input[name=testdropdown]').trigger('focus');
        expect(wrapper.vm.optionsShown).toBe(true);
        expect(wrapper.findAll('.options').length).toBe(3);
    });
    test("CanSelectAnOption", async () => {
        wrapper = factory();
        await wrapper.find('input[name=testdropdown]').trigger('focus');
        await wrapper.findAll('.options')[0].trigger('mousedown',
            {
                id: 1,
                name: 'test'
            });
        expect(wrapper.emitted().selected).toHaveLength(2);
        expect(wrapper.emitted().selected[1][0].id).toBe(1);
    });

    test("searchableDropdownFieldsAdjustAble", async () => {
        wrapper = factory();
        await wrapper.setProps({
            fields: ['id', 'name'],

        });
        const field = await wrapper.find('.options p');
        expect(field.text()).toBe('1 | test');
    });

    test("TextAdjustAble", async () => {
        wrapper = factory();
        await wrapper.setProps({
            text: ['id'],
        });
        await wrapper.findAll('.options')[0].trigger('mousedown',
            {
                id: 1,
                name: 'test'
            });
        const field = await wrapper.find('input[name=testdropdown]').element.value;
        expect(field).toBe('1 | ');
    });
});