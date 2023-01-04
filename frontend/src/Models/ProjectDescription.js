export default class ProjectDescription {
    description_long;
    constructor(description_long) {
        this.description_long = description_long;
    }
    static copyConstructor(entity){
        if(entity == null) return null;
        return Object.assign(new ProjectDescription(),entity);
    }
}