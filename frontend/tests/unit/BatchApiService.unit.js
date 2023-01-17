import BaseApi from '@/Services/BaseApi';
import Batch from "@/Models/Batch";
jest.mock('@/Services/BaseApi', () => {
    return {
        get: jest.fn(() => Promise.resolve({data: mockBatches})),
        post: jest.fn(() => Promise.resolve({data: mockBatch})),
        put: jest.fn(() => Promise.resolve({data: mockUpdateBatch}))
    }
});

import BatchApiService from '@/Services/Batch/BatchApiService';
import mockBatches from '../mockingData/Batch/listOfBatches.json';
import mockBatch from '../mockingData/Batch/batch.json';
import mockUpdateBatch from '../mockingData/Batch/updateBatch.json';
import moment from "moment";

describe('BatchApiService', () => {
    let batchApiService;
    beforeEach(() => {
        jest.clearAllMocks();
        batchApiService = new BatchApiService();
    });

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

    it('should create a batch', async () => {
        // Arrange
        const expectedBatch = {
            id: mockBatch.id,
            textPlanned: mockBatch.textPlanned,
            batchSize: mockBatch.batchSize,
            projectKey: mockBatch.projectKey,
            orderlines: mockBatch.orderlines,
            createdAt: mockBatch.createdAt
        };

        // Act
        const result = await batchApiService.create(
            mockBatch.textPlanned,
            mockBatch.batchSize,
            mockBatch.projectKey,
            mockBatch.orderlines
        );

        // Assert
        expect(result).toEqual(expectedBatch);
        expect(BaseApi.post).toHaveBeenCalledWith('/batch', {
            textPlanned: mockBatch.textPlanned,
            batchSize: mockBatch.batchSize,
            projectKey: mockBatch.projectKey,
            orderlines: mockBatch.orderlines,
            createdAt: moment().format("YYYY-MM-DD")
        });
    });

    it('should update an existing batch', async () => {
        // Arrange
        const expectedBatch = {
            id: mockBatch.id,
            textPlanned: "Batch from created that has been updated",
            batchSize: mockBatch.batchSize,
            orderlines: mockBatch.orderlines
        };

        // Act
        const result = await batchApiService.updateBatch(
            mockBatch.id,
            expectedBatch.textPlanned,
            expectedBatch.batchSize,
            expectedBatch.orderlines
        );

        // Assert
        expect(result).toMatchObject(expectedBatch);
        expect(BaseApi.put).toHaveBeenCalledWith(`${batchApiService.resource}/update`, {
            id: mockBatch.id,
            textPlanned: "Batch from created that has been updated",
            batchSize: mockBatch.batchSize,
            orderlines: mockBatch.orderlines
        });
    });
});
