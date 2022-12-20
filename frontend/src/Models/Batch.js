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
        return Object.assign(new Batch(), batch);
    }
}
