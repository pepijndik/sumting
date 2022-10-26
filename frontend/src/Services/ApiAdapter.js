import BaseApi from "@/Services/BaseApi";

export class ApiAdapter {
    constructor(resource) {
        this.resource = resource;
    }

    async findAll(){
     return BaseApi.get(`/${this.resource}`);
    }

    async findOne(id){
     return BaseApi.get(`/${this.resource}/${id}`);
    }
}