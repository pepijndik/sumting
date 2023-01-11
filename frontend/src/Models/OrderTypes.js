export default class OrderTypes
{

    id;
    description;
    type;
    esExt;
    constructor(id,description,type,esExt)
    {
        this.id=id;
        this.description=description;
        this.type=type;
        this.esExt=esExt;
    }

    /**
     * Makes a new OrderTypes object from a given object
     * @param type
     * @returns {any|null}
     */
    static copyConstructor(type){
        if(type == null) return null;
        return Object.assign(new OrderTypes(), type);
    }

}