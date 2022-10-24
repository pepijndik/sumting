import BaseApi from "@/Services/BaseApi";

export class ApiAdapter {
    constructor(resource) {
        this.resource = resource;
        this.api = BaseApi()
    }

    async findAll(){
     this.api.get(`/${this.resource}`);
    }
}