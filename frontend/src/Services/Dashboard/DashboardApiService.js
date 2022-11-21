import ApiAdapter from "@/Services/ApiAdapter";
import AuthHeader from "@/Services/AuthHeader";
class DashboardApiService extends ApiAdapter {
  constructor() {
    super("orderMonths");
    this.headers = AuthHeader();
  }

  async findByMonth(orderMonths) {
    return this.get(`/${this.resource}/${orderMonths}`)
      .then((response) => {
        return response.data;

        //return response.data;
      })
      .catch((error) => {
        // You can handle the error, like show a notificaiton to the user

        // dont forget to re-throw the error, otherwise the promise will resolve successfully
        throw error;
      });
  }
}
export default DashboardApiService;
