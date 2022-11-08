import ApiAdapter from "@/Services/ApiAdapter";
import AuthHeader from "@/Services/AuthHeader";
class DashboardApiService extends ApiAdapter{

    constructor() {
        super('orderMonths');
        this.headers = AuthHeader()
    }

    // Add custom methods here
}
export default DashboardApiService;