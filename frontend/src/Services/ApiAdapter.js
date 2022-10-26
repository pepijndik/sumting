import BaseApi from "@/Services/BaseApi";

export class ApiAdapter extends BaseApi{
    constructor(resource) {
        super();
        this.resource = resource;
    }

    async findAll(){
     return this.get(`/${this.resource}`);
    }

    async findOne(id){
     return this.get(`/${this.resource}/${id}`);
    }
}