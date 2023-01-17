import { shallowMount } from '@vue/test-utils';
import BatchRow from "@/Components/Batch/BatchRow";
import moment from 'moment';

describe('BatchRow', () => {
    let wrapper;
    let batch = {
        id: 1,
        batchSize: 100,
        date: new Date()
    }
    beforeEach(() => {
        wrapper = shallowMount(BatchRow, {
            propsData: { batch },
            mocks: {
                $router: {
                    push: jest.fn()
                }
            }
        });
        wrapper.vm.redirectToEdit = jest.fn();
    });

    it('renders the batch id in the template', () => {
        // Arrange
        const expectedId = `ID ${batch.id}`;

        // Act
        const idElement = wrapper.find('.bId');

        // Assert
        expect(idElement.text()).toBe(expectedId);
    });

    it('renders the batch size in the template', () => {
        // Arrange
        const expectedSize = batch.batchSize.toString();

        // Act
        const sizeElement = wrapper.find('.bSize');

        // Assert
        expect(sizeElement.text()).toBe(expectedSize);
    });

    it('renders the formatted batch date in the template', () => {
        // Arrange
        const expectedDate = moment(batch.date).format('DD/MM/YY');

        // Act
        const dateElement = wrapper.find('.bDate');

        // Assert
        expect(dateElement.text()).toBe(expectedDate);
    });

    it('calls confirmDelete method when delete button is clicked', () => {
        // Arrange
        const deleteBtn = wrapper.find('.bDelete');
        wrapper.vm.confirmDelete = jest.fn();

        // Act
        deleteBtn.trigger('click');

        // Assert
        expect(wrapper.vm.confirmDelete).toHaveBeenCalled();
    });

    it('redirects to the edit page when the edit button is clicked', () => {
        // Arrange
        const editBtn = wrapper.find('.bEdit');

        // Act
        editBtn.trigger('click');

        // Assert
        expect(wrapper.vm.redirectToEdit).toHaveBeenCalled();
    });
});
