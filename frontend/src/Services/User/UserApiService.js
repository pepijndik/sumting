import ApiAdapter from "@/Services/ApiAdapter";
import User from "@/Models/User";
import AuthHeader from "@/Services/AuthHeader";

class UserApiService extends ApiAdapter {
    constructor() {
        super('users');
        this.setHeader(AuthHeader());
    }

    async GetAllUsers(){
        return await this.findAll();
    }

}
export default UserApiService;