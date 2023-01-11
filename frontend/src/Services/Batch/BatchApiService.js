import ApiAdapter from "@/Services/ApiAdapter";
import AuthHeader from "@/Services/AuthHeader";
import BaseApi from "@/Services/BaseApi";
import {useToast} from 'vue-toast-notification';
import moment from "moment";
import Batch from "@/Models/Batch";
import OrderLine from "@/Models/OrderLine";

class BatchApiService extends ApiAdapter {
    constructor() {
        super("batch");
        this.headers = AuthHeader();
    }

    async create(
        textPlanned,
        batchSize,
        projectKey,
        orderLines
    ) {
        const batch = new Batch();
        batch.textPlanned = textPlanned;
        batch.batchSize = batchSize;
        batch.projectKey = projectKey;
        batch.orderlines = orderLines;
        batch.createdAt = moment().format("YYYY-MM-DD");

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

    async updateBatch(
        batchId,
        textPlanned,
        batchSize,
        orderLines
    ) {
        const batch = new Batch();
        batch.id = batchId;
        batch.textPlanned = textPlanned;
        batch.batchSize = batchSize;
        batch.orderlines = orderLines;
        batch.updatedAt = moment().format("YYYY-MM-DD");

        return await BaseApi.put(`${this.resource}/update`, batch)
            .then((response) => {
                return response.data;
            })
            .catch((error) => {
                const $toast = useToast();
                $toast.error({
                    message: "Can't update batch " + error.response.data.message,
                    duration: 5000,
                    dismissible: true,
                });
                return false;
            });
    }

    async getAllBatches() {
        const batches = await BaseApi.get(this.resource)
            .then((response) => {
                return response.data;
            })
            .catch((error) => {
                throw error;
            })
        return batches.map(batch => Batch.copyConstructor(batch));
    }
}

export default BatchApiService;
