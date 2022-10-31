import ApiAdapter from "@/Services/ApiAdapter";
import BaseApi from "@/Services/BaseApi";
import AuthHeader from "@/Services/AuthHeader";
class ProjectApiService extends ApiAdapter {
    constructor() {
        super('projects');
        this.setHeader(AuthHeader());
    }

    async SearchableDropDown() {
        return await this.findAll().then(response => {
            return response.data.map((project) => {
                return project;
            })
             //return response.data;
        }).catch(error => {
            // You can handle the error, like show a notificaiton to the user

            // dont forget to re-throw the error, otherwise the promise will resolve successfully
            throw error
        });
    }

    // Add custom methods here
}

export default ProjectApiService;