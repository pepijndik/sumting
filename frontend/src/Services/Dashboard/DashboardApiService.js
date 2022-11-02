import ApiAdapter from "@/Services/ApiAdapter";
import AuthHeader from "@/Services/AuthHeader";
class DashboardApiService extends ApiAdapter{

    constructor() {
        super('orderMonths');
        this.headers = AuthHeader()
    }

    async findByMonths(){
        return await this.findByMonths();
    }
    // Add custom methods here
}
export default DashboardApiService;