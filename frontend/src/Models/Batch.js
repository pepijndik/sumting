import moment from "moment";
export default class Batch
{
    id; // Number
    createdAt; // LocalDate
    updatedAt; // LocalDate
    textPlanned; // String
    textCompleted; // String
    batchSize; // Integer
    projectKey; // Integer
    batchInvoiceKey; // Integer
    project; // Project
    orderlines = []; // Orderline[]

    constructor() {}

    static copyConstructor(batch) {
        if (batch == null) return null;

        let b = Object.assign(new Batch(), batch);
        b.createdAt = moment(batch.createdAt);
        // b.projectKey = b.project.id;

        return b;
    }
}
