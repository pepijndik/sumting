import ApiAdapter from "@/Services/ApiAdapter";
import AuthHeader from "@/Services/AuthHeader";
import BaseApi from "@/Services/BaseApi";

import Products from "@/Models/Product";
class ProductApiService extends ApiAdapter {
    constructor() {
        super('products');
        this.setHeader(AuthHeader());
    }

    /**
     * Finds a product by the given project id
     * @param id
     * @returns {Promise<*>}
     */
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