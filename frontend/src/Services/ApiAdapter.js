import BaseApi from "@/Services/BaseApi";
export default class ApiAdapter{

    constructor(resource) {
        this.resource = resource;
    }
    setHeader(Header = {}){
        BaseApi.defaults.headers = Header;
    }
    async findAll(){
     return BaseApi.get(`/${this.resource}`);
    }

    async findOne(id){
     return BaseApi.get(`/${this.resource}/${id}`);
    }

    async delete(id){
        return BaseApi.delete(`/${this.resource}/${id}`);
    }

    async save(data){
        return BaseApi.post(`/${this.resource}`, data);
    }
}