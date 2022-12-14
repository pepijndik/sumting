import moment from "moment/moment";
import ProductType from "@/Models/ProductType";
import Project from "@/Models/Project";

export default class Product{
    id;
    name;
    description;
    price;
    project;
    type;
    createdAt;
    updatedAt;
    id_ext;
    active;
    constructor(id, name, description, price, project, type, createdAt, updatedAt, id_ext, active){
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.project = project;
        this.type = type;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.id_ext = id_ext;
        this.active = active;
    }
    static copyConstructor(product){
        if(product == null) return null;
        product = Object.assign(new Product(),product);
        product.createdAt = moment(product.createdAt);
        product.updatedAt = moment(product.updatedAt);
        product.type = ProductType.copyConstructor(product.type);
        product.project = Project.copyConstructor(product.project);
        return product;
    }
}