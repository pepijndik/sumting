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

    /**
     * Creates a new batch
     * @param textPlanned
     * @param batchSize
     * @param projectKey
     * @param orderLines
     * @returns {Promise<void>}
     */
    async create(textPlanned,
                 batchSize,
                 projectKey,
                 orderLines) {

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

    /**
     * Finds all batches
     * @returns {Promise<*>}
     */
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
