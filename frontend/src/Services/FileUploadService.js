import BaseApi from "@/Services/BaseApi";
import ApiAdapter from "@/Services/ApiAdapter";

export default class FileUploadService extends ApiAdapter {
    constructor(resource = "") {
        super(resource);
    }

    /**
     * Uploads a profile image for the given user
     * @param id The id of the user
     * @param img The image to upload
     * @returns {Promise<AxiosResponse<any>>}
     */
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
