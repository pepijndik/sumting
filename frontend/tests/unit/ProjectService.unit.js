import BaseApi from '@/Services/BaseApi';
import Project from "@/Models/Project";
jest.mock('@/Services/BaseApi', () => {
    return {
        get: jest.fn(() => Promise.resolve({data: mockProjects}))
    }
});

import ProjectApiService from '@/Services/Projects/ProjectApiService';
import mockProjects from '../mockingData/Project/Projects.json';

describe('ProductApiService', () => {
    let projectApiService;
    beforeEach(() => {
        jest.clearAllMocks();
        projectApiService = new ProjectApiService();
    });

    describe('getAllProducts', () => {
        it('should return all products', async () => {
            // Arrange
            const expectedProducts = mockProjects.map(project => Project.copyConstructor(project));

            // Act
            const actualProducts = await projectApiService.findAll(0, 10, false);

            // Assert
            expect(actualProducts).toEqual(expectedProducts);
            expect(BaseApi.get).toHaveBeenCalled();
            expect(BaseApi.get).toHaveBeenCalledWith('/' + projectApiService.resource + '?all=true');
        });
    });
});
