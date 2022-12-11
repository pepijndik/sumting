import ApiAdapter from "@/Services/ApiAdapter";
import AuthHeader from "@/Services/AuthHeader";
import BaseApi from "@/Services/BaseApi";
import Order from "@/Models/Order";
import Products from "@/Models/Order";
class ProductApiService extends ApiAdapter {
    constructor() {
        super('products');
        this.setHeader(AuthHeader());
    }

    async SearchableDropDown() {
        return await this.findAll();
    }

    async findProductByProjectId(id) {
        const Response = await BaseApi.get('products/findByProjectId?id=' + id).then(response => {
            return response.data;
        }).catch(error => {
            throw error;
        })
        return Response.map(product => Products.copyConstructor(product));
    }

    // Add custom methods here
}

export default ProductApiService;