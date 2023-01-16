import ProductApiService from "@/Services/Products/ProductApiService";

let pApi = new ProductApiService();

describe('AuthenticationService', () => {
    it("Can retrieve all products", async () => {
        let products = await pApi.findAll();
        expect(products.length).toBe(2);
    });
});