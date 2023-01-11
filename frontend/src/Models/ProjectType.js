export default class ProjectType{
    id;
    description;
    constructor(id, description){
        this.id = id;
        this.description = description;
    }

    /**
     * Makes a new ProjectType object from a given object
     * @param type
     * @returns {any|null}
     */
    static copyConstructor(type){
        if(type == null) return null;
        return Object.assign(new ProjectType(), type);
    }
}