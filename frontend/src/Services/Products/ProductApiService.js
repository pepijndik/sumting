import ApiAdapter from "@/Services/ApiAdapter";
import AuthHeader from "@/Services/AuthHeader";
import BaseApi from "@/Services/BaseApi";
class ProductApiService extends ApiAdapter {
    constructor() {
        super('products');
        this.setHeader(AuthHeader());
    }

    async SearchableDropDown() {
        return await this.findAll();
    }

    async findProductByProjectId(id) {
        return await BaseApi.get('products/findByProjectId?id=' + id).then(response => {

            return response.data;
        }).catch(error => {
            throw error;
        })
    }

    // Add custom methods here
}

export default ProductApiService;