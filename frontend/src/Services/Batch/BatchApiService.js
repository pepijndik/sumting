import ApiAdapter from "@/Services/ApiAdapter";
import AuthHeader from "@/Services/AuthHeader";
import BaseApi from "@/Services/BaseApi";
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
     * @returns {Promise<T>}
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

        const response = await this.save(batch);
        return response.data;
    }

    /**
     * Updates an existing Batch
     * @param batchId
     * @param textPlanned
     * @param batchSize
     * @param orderLines
     * @returns {Promise<any>}
     */
    async updateBatch(batchId, textPlanned, batchSize, orderLines) {
        try {
            // Arrange
            const batch = new Batch();
            batch.id = batchId;
            batch.textPlanned = textPlanned;
            batch.batchSize = batchSize;
            batch.orderlines = orderLines;

            // Act
            const response = await BaseApi.put(`${this.resource}/update`, batch);

            // Assert
            return response.data;
        } catch (error) {
            throw new Error("Can't update batch " + error.response.data.message);
        }
    }

    /**
     * Gets all batches
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
