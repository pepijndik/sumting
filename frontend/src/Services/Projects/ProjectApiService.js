import ApiAdapter from "@/Services/ApiAdapter";

import Project from "@/Models/Project";
import BaseApi from "@/Services/BaseApi";

class ProjectApiService extends ApiAdapter {
    constructor() {
        super('projects');
    }

    /**
     * Finds all projects
     * @param page
     * @param size
     * @param pagination
     * @returns {Promise<AxiosResponse<any>>}
     */
    async findAll({page = 0, size = 10, pagination = false}) {
        let url = `/${this.resource}`;
        if (pagination === false) {
            url = this.updateQueryStringParameter(url, 'all', true);
        } else {
            if (page > 0) {
                url = this.updateQueryStringParameter(url, 'page', page);
            }
            if (size < 10 && size > 0 || size > 10) {
                url = this.updateQueryStringParameter(url, 'size', size);
            }
        }
        let projects = await BaseApi.get(`${url}`)
            .then((response) => {
                return response.data;
                //return response.data;
            })
            .catch((error) => {
                // You can handle the error, like show a notification to the user

                // don't forget to re-throw the error, otherwise the promise will resolve successfully
                throw error;
            });
        if (pagination === false) {
            projects = projects.map(project => Project.copyConstructor(project));
        } else {
            projects.data = projects.projects.map(project => Project.copyConstructor(project));
        }

        return projects
    }

    /**
     * Finds all projects without pagination
     * @returns {Promise<AxiosResponse<*>|*>}
     * @constructor
     */
    async SearchableDropDown() {
        return await this.findAll({
            pagination: false
        });
    }

    // Add custom methods here
}

export default ProjectApiService;