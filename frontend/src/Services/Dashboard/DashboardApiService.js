import ApiAdapter from "@/Services/ApiAdapter";
import AuthHeader from "@/Services/AuthHeader";
import axios from "axios";
import Order from "@/Models/Order";
import ProjectDescription from "@/Models/ProjectDescription";
import BaseApi from "@/Services/BaseApi";
import Products from "@/Models/Product";
import Project from "@/Models/Project";

// const API_URL = process.env.VUE_APP_API_URL + '/api/test/';
// const API_URL = process.env.VUE_APP_BACKEND_API_URL

class DashboardApiService extends ApiAdapter {
  constructor() {
    super("orderMonths");
    this.headers = AuthHeader();
  }

  findDescriptions(){
    return axios.get(this.resource + "/projectDescriptions", {
      headers: AuthHeader()
    })
  }

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

  // async findDescriptions() {
  //   const Response = await BaseApi.get(this.resource + '/projectDescriptions').then(response => {
  //     return response.data;
  //   }).catch(error => {
  //     throw error;
  //   })
  //   return Response.map(product => Project.copyConstructor(product));
  // }

  // async findByDescription() {
  //   const projects = await super.findDescription();
  //   return projects.map(order => ProjectDescription.copyConstructor(order));
  // }

}
export default DashboardApiService;
