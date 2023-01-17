import BaseApi from '@/Services/BaseApi';
import Batch from "@/Models/Batch";
jest.mock('@/Services/BaseApi', () => {
    return {
        get: jest.fn(() => Promise.resolve({data: mockBatches})),
        post: jest.fn(() => Promise.resolve({data: mockBatch}))
    }
});

import BatchApiService from '@/Services/Batch/BatchApiService'
import mockBatches from '../mockingData/Batch/listOfBatches.json'
import mockBatch from '../mockingData/Batch/batch.json'
import moment from "moment";

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

    describe('create', () => {
        it('creates a batch', async () => {
            const expectedBatch = {
                id: mockBatch.id,
                textPlanned: mockBatch.textPlanned,
                batchSize: mockBatch.batchSize,
                projectKey: mockBatch.projectKey,
                orderlines: mockBatch.orderlines,
                createdAt: mockBatch.createdAt
            };

            const result = await batchApiService.create(
                mockBatch.textPlanned,
                mockBatch.batchSize,
                mockBatch.projectKey,
                mockBatch.orderlines
            );

            expect(result).toEqual(expectedBatch);
            expect(BaseApi.post).toHaveBeenCalledWith('/batch', {
                textPlanned: mockBatch.textPlanned,
                batchSize: mockBatch.batchSize,
                projectKey: mockBatch.projectKey,
                orderlines: mockBatch.orderlines,
                createdAt: moment().format("YYYY-MM-DD")
            });
        });
    });
});
