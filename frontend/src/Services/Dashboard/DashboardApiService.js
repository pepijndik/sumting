import ApiAdapter from "@/Services/ApiAdapter";
import AuthHeader from "@/Services/AuthHeader";
import axios from "axios";
class DashboardApiService extends ApiAdapter {
  constructor() {
    super("orderMonths");
    this.headers = AuthHeader();
  }

  getAllProjectDescriptions(){
    return axios.get(this.resource + "/projectDescriptions", {
      headers: AuthHeader()
    })
  }

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
