export default class ProductType {
    id;
    description;
    product_type_name;
    frequency;
    createdAt;

    constructor(id, description, product_type_name, frequency, createdAt) {
        this.id = id;
        this.description = description;
        this.product_type_name = product_type_name;
        this.frequency = frequency;
        this.createdAt = createdAt;
    }
    static copyConstructor(type){
        if(type == null) return null;
        return Object.assign(new ProductType(), type);
    }
}