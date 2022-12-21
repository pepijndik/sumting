import ApiAdapter from "@/Services/ApiAdapter";

import Project from "@/Models/Project";
class ProjectApiService extends ApiAdapter {
    constructor() {
        super('projects');
    }

    async findAll() {
        const projects = await super.findAll();
        return projects.map(project => Project.copyConstructor(project));
    }
    async SearchableDropDown() {
        return await this.findAll();
    }

    // Add custom methods here
}

export default ProjectApiService;