import User from "@/Models/User";
import ApiAdapter from "@/Services/ApiAdapter";
import BaseApi from "@/Services/BaseApi";

class UserApiService extends ApiAdapter {
  constructor() {
    super("users");
  }

  async createUser(name, email, country, type) {
    if (!name || !email || !country || !type) return false;

    return await BaseApi.post(`auth/${this.resource}`, {
      name,
      email,
      country,
      type,
    })
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        console.log(error);
        return false;
      });
  }
}
export default UserApiService;
