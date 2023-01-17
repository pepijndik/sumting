import { shallowMount } from '@vue/test-utils'
import BatchRow from '@/Components/Batch/BatchRow'
import moment from 'moment'

describe('BatchRow', () => {
    let wrapper;

    beforeEach(() => {
        wrapper = shallowMount(BatchRow, {
            propsData: {
                batch: {
                    id: '1',
                    batchSize: '10',
                    createdAt: '2022-01-01'
                }
            }
        });
    });

    it('displays the batch id', () => {
        expect(wrapper.find('.bId').text()).toContain('ID 1');
    });

    it('displays the batch size', () => {
        expect(wrapper.find('.bSize').text()).toContain('10');
    });

    it('displays the batch date', () => {
        expect(wrapper.find('.bDate').text()).toContain(wrapper.vm.batchDate);
    });

    it('displays the edit button', () => {
        expect(wrapper.find('img[alt="Edit icon"]').exists()).toBe(true);
    });
});
