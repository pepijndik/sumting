import BaseApi from '@/Services/BaseApi';
import Product from "@/Models/Product";
jest.mock('@/Services/BaseApi', () => {
    return {
        get: jest.fn(() => Promise.resolve({data: mockProducts}))
    }
});

import ProductApiService from "@/Services/Products/ProductApiService";
import mockProducts from '../mockingData/Product/listOfProducts.json'

describe('ProductApiService', () => {
    let productApiService;
    beforeEach(() => {
        jest.clearAllMocks();
        productApiService = new ProductApiService();
    });

    describe('getAllProducts', () => {
        it('should return all products', async () => {
            // Act
            const actualProducts = await productApiService.getAllBatches();

            // Assert
            expect(actualProducts).toEqual(mockProducts.map(product => Product.copyConstructor(product)));
            expect(BaseApi.get).toHaveBeenCalled();
            expect(BaseApi.get).toHaveBeenCalledWith(productApiService.resource);
        });
    });
});