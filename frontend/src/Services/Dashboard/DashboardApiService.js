import ApiAdapter from "@/Services/ApiAdapter";
import AuthHeader from "@/Services/AuthHeader";
class DashboardApiService extends ApiAdapter{

    constructor() {
        super('orderMonths');
        this.headers = AuthHeader()
    }

    // async selectByMonths(){
    //     return await this.findByMonth();
    // }
    // Add custom methods here
}
export default DashboardApiService;