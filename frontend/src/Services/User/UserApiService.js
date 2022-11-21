import ApiAdapter from "@/Services/ApiAdapter";
import User from "@/Models/User";

class UserApiService extends ApiAdapter {
    constructor() {
        super('users');
    }

}
export default UserApiService;