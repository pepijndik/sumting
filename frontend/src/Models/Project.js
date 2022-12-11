import ProductType from "@/Models/ProductType";
import ProjectType from "@/Models/ProjectType";

export default class Project {
    id;
    description;
    description_long;
    name;

    createdAt;
    updatedAt;
    latitude;
    longitude;
    image;
    type;

    constructor(id, description, description_long, name, createdAt, updatedAt, latitude, longitude, image, type) {
        this.id = id;
        this.description = description;
        this.description_long = description_long;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
    }
    static copyConstructor(project){
        if(project == null) return null;
        project = Object.assign(new Project(),project);
        project.type = ProjectType.copyConstructor(project.type);
        return project;
    }
}