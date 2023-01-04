import ApiAdapter from "@/Services/ApiAdapter";
import AuthHeader from "@/Services/AuthHeader";
import axios from "axios";

// const API_URL = process.env.VUE_APP_API_URL + '/api/test/';
// const API_URL = process.env.VUE_APP_BACKEND_API_URL

class DashboardApiService extends ApiAdapter {
  constructor() {
    super("orderMonths");
    this.headers = AuthHeader();
  }

  // getAllProjectDescriptions(){
  //   return axios.get(this.resource + "orderMonths/projectDescriptions", {
  //     headers: AuthHeader()
  //   })
  // }

  // async getAllProjectDescriptions() {
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
  //   return Projects.map(project => ProjectDescription.copyConstructor(project));
  // }



}
export default DashboardApiService;
