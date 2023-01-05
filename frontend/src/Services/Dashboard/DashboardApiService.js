import ApiAdapter from "@/Services/ApiAdapter";
import AuthHeader from "@/Services/AuthHeader";
import BaseApi from "@/Services/BaseApi";

// const API_URL = process.env.VUE_APP_API_URL + '/api/test/';
// const API_URL = process.env.VUE_APP_BACKEND_API_URL

class DashboardApiService extends ApiAdapter {
  constructor() {
    super("orderMonths");
    this.headers = AuthHeader();
  }

  // findDescriptions(){
  //   return axios.get(this.resource + "/projectDescriptions", {
  //     headers: AuthHeader()
  //   })
  // }

  // async findDescriptions() {
  //   const Projects = await BaseApi.get(this.resource + "/projectDescriptions").then((response) => {
  //     return response.data;
  //   })
  //       .catch((error) => {
  //         // You can handle the error, like show a notificaiton to the user
  //
  //         // dont forget to re-throw the error, otherwise the promise will resolve successfully
  //         throw error;
  //       });
  //
  //   return Response.map(project => ProjectDescription.copyConstructor(project));
  // }

  async findDescriptions() {
    return await BaseApi.get(this.resource + '/projectDescriptions').then(response => {
      return response.data;
    }).catch(error => {
      throw error;
    });
  }

  // async findByDescription() {
  //   const projects = await super.findDescription();
  //   return projects.map(order => ProjectDescription.copyConstructor(order));
  // }

}
export default DashboardApiService;
