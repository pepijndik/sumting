import ProjectType from "@/Models/ProjectType";
import moment from "moment";

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
    static project;

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

    /**
     * Makes a new Project object from a given object
     * @param entity
     * @returns {any|null}
     */
    static copyConstructor(entity){
        if(entity == null) return null;
        let project = Object.assign(new Project(),entity);
        project.createdAt = moment(entity.created_at);
        project.updatedAt = moment(entity.updated_at);

        project.type = ProjectType.copyConstructor(project.type);
        return project;
    }
}