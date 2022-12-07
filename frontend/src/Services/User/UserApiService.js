import User from "@/Models/User";
import ApiAdapter from "@/Services/ApiAdapter";

class UserApiService extends ApiAdapter {
  constructor() {
    super("users");
  }
}
export default UserApiService;
