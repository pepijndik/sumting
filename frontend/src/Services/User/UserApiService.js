import User from "@/Models/User";
import ApiAdapter from "@/Services/ApiAdapter";
import BaseApi from "@/Services/BaseApi";
import { useToast } from "vue-toast-notification";

class UserApiService extends ApiAdapter {
  constructor() {
    super("users");
    this.setHeader();
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
        const $toast = useToast();
        $toast.error({
          message: "Cant create client " + error.response.data.message,
          duration: 5000,
          dismissible: true,
        });
        return false;
      });
  }
  async updateUser(user) {
    if (
      !user.id ||
      !user.name ||
      !user.email ||
      !user.country ||
      !user.user_type
    ) {
      return false;
    }
    return await BaseApi.put(`${this.resource}`, user)
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        const $toast = useToast();
        $toast.error({
          message: "Cant update client " + error.response.data.message,
          duration: 5000,
          dismissible: true,
        });
        return false;
      });
  }
}
export default UserApiService;
