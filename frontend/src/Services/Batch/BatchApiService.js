import ApiAdapter from "@/Services/ApiAdapter";
import AuthHeader from "@/Services/AuthHeader";
import BaseApi from "@/Services/BaseApi";
import {useToast} from 'vue-toast-notification';
import moment from "moment";
import Batch from "@/Models/Batch";

class BatchApiService extends ApiAdapter {
    constructor() {
        super("batch");
        this.headers = AuthHeader();
    }

    async create(
        textPlanned,
        batchSize,
        project,
        orderLines
    ) {
        const batch = new Batch();
        batch.textPlanned = textPlanned;
        batch.batchSize = batchSize;
        batch.project = project;
        batch.orderlines = orderLines;

        await this.save(batch).then((response) => {
            return response.data;
        }).catch((error) => {
           const $toast = useToast();
           $toast.error({
               message: "Can't create batch " + error.response.data.message,
               duration: 5000,
               dismissible: true,
           });
           throw error;
        });
    }
}

export default BatchApiService;
