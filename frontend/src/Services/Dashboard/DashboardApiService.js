import ApiAdapter from "@/Services/ApiAdapter";
import AuthHeader from "@/Services/AuthHeader";
import BaseApi from "@/Services/BaseApi";

class DashboardApiService extends ApiAdapter {
  constructor() {
    super("orderMonths");
    this.headers = AuthHeader();
  }
  async findDescriptions() {
    return await BaseApi.get(this.resource + '/projectDescriptions').then(response => {
      return response.data;
    }).catch(error => {
      throw error;
    });
  }

  async ordersByMonth(month) {
    return await BaseApi.get(this.resource + "/" + month).then(response => {
      return response.data;
    }).catch(error => {
      throw error;
    })
  }

}
export default DashboardApiService;
