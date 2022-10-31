import ApiAdapter from "@/Services/ApiAdapter";
import BaseApi from "@/Services/BaseApi";
import AuthHeader from "@/Services/AuthHeader";
class ProjectApiService extends ApiAdapter {
    constructor() {
        super('projects');
        this.setHeader(AuthHeader());
    }

    async SearchableDropDown() {
        return await this.findAll();
    }

    // Add custom methods here
}

export default ProjectApiService;