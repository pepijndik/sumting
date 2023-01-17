import BaseApi from '@/Services/BaseApi';
import Batch from "@/Models/Batch";
jest.mock('@/Services/BaseApi', () => {
    return {
        get: jest.fn(() => Promise.resolve({data: mockBatches}))
    }
});

import BatchApiService from '@/Services/Batch/BatchApiService'
import mockBatches from '../mockingData/Batch/listOfBatches.json'

describe('BatchApiService', () => {
    let batchApiService;
    beforeEach(() => {
        jest.clearAllMocks();
        batchApiService = new BatchApiService();
    });

    describe('getAllBatches', () => {
        it('should return all batches', async () => {
            // Arrange
            const expectedBatches = mockBatches.map(batch => Batch.copyConstructor(batch));

            // Act
            const actualBatches = await batchApiService.getAllBatches();

            // Assert
            expect(actualBatches).toEqual(expectedBatches);
            expect(BaseApi.get).toHaveBeenCalled();
            expect(BaseApi.get).toHaveBeenCalledWith(batchApiService.resource);
        });
    });
});
