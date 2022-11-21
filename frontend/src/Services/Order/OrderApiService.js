import ApiAdapter from "@/Services/ApiAdapter";
import AuthHeader from "@/Services/AuthHeader";
class OrderApiService extends ApiAdapter{

    constructor() {
        super('orders');
        this.headers = AuthHeader()
    }

    // Add custom methods here
}
export default OrderApiService;