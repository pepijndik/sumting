import BaseApi from '@/Services/BaseApi';

/**
 * Returns a mock of the BaseApi class with the given data to be returned in a get request
 * @author Colin Laan
 */
jest.mock('@/Services/BaseApi', () => {
    return {
        get: jest.fn(() => Promise.resolve({data: mockProjects}))
    }
});

import ProjectApiService from '@/Services/Projects/ProjectApiService';
import mockProjects from '../mockingData/Project/Projects.json';

/**
 * Tests the ProjectApiService class
 */
describe('ProductApiService', () => {
    let projectApiService;
    beforeEach(() => {
        jest.clearAllMocks();
        projectApiService = new ProjectApiService();
    });

    /**
     * Tests the findAll method with pagination
     * Should return almost all the projects from the mock data,
     *  except for the one due to pagination giving 3 projects per page
     * @author Colin Laan
     */
    describe('getAllProducts', () => {
        it('should return all products', async () => {
            // Arrange
            const expectedProducts = mockProjects

            // Act
            const actualProducts = await projectApiService.findAll({
                page: 0,
                size: 3,
                pagination: true,
            });

            // Assert
            expect(actualProducts).toEqual(expectedProducts);
            expect(BaseApi.get).toHaveBeenCalled();
            expect(BaseApi.get).toHaveBeenCalledWith('/' + projectApiService.resource + '?size=3');
        });
    });
});
