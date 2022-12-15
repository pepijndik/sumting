import AuthHeader from "@/Services/AuthHeader";
import BaseApi from "@/Services/BaseApi";
import ApiAdapter from "@/Services/ApiAdapter";
export default class FileUploadService  extends ApiAdapter{
  constructor(resource ="") {
    super(resource);
  }
  async uploadIMG(id, img) {
    this.setHeader({
      "Content-Type": "multipart/form-data",
    });
    let formData = new FormData();

    formData.append("file", img);

    return BaseApi.post(`users/${id}/profile-picture`, formData)
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        console.log(error);
      });
  }
}
