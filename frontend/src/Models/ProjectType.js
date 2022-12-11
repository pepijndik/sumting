export default class ProjectType{
    id;
    description;
    constructor(id, description){
        this.id = id;
        this.description = description;
    }
    static copyConstructor(type){
        if(type == null) return null;
        return Object.assign(new ProjectType(), type);
    }
}