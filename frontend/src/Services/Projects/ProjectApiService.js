import {ApiAdapter} from "@/Services/ApiAdapter";

class ProjectApiService extends ApiAdapter {
    constructor() {
        super('projects');
    }

    async SearchableDropDown() {
        return await this.findAll().then(response => {
            return response.data.map((project) => {
                return {
                    id: project.id,
                    name: project.description,
                };
            })
        }).catch(error => {
            // You can handle the error, like show a notificaiton to the user

            // dont forget to re-throw the error, otherwise the promise will resolve successfully
            throw error
        });
    }

    // Add custom methods here
}

export default ProjectApiService;