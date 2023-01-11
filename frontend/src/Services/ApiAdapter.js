import BaseApi from "@/Services/BaseApi";
import AuthHeader from "@/Services/AuthHeader";

export default class ApiAdapter {
    constructor(resource) {
        this.resource = resource;
        this.header = {};
    }

    /**
     * Sets the header for the request
     * @param Header
     */
    setHeader(Header = {}) {
        BaseApi.defaults.headers = {
            ...AuthHeader(),
            ...Header,
        };
    }

    /**
     * Gets the current header
     * @returns {{Authorization: string}}
     */
    getHeader() {
        return {
            ...AuthHeader(),
        }
    }

    /**
     * Finds all entities
     * @returns {Promise<AxiosResponse<any>>}
     */
    async findAll() {
        return BaseApi.get(`/${this.resource}`)
            .then((response) => {
                return response.data;

                //return response.data;
            })
            .catch((error) => {
                // You can handle the error, like show a notification to the user

                // don't forget to re-throw the error, otherwise the promise will resolve successfully
                throw error;
            });
    }

    /**
     * Updates the query string parameters
     * @param uri
     * @param key
     * @param value
     * @returns {*|string}
     */
    updateQueryStringParameter(uri, key, value) {
        const re = new RegExp("([?&])" + key + "=.*?(&|$)", "i");
        const separator = uri.indexOf('?') !== -1 ? "&" : "?";
        if (uri.match(re)) {
            return uri.replace(re, '$1' + key + "=" + value + '$2');
        } else {
            return uri + separator + key + "=" + value;
        }
    }

    /**
     * finds all entities with the given order month
     * @param orderMonths
     * @returns {Promise<AxiosResponse<any>>}
     */
    async findByMonth(orderMonths) {
        return BaseApi.get(`/${this.resource}/${orderMonths}`)
            .then((response) => {
                return response.data;

                //return response.data;
            })
            .catch((error) => {
                // You can handle the error, like show a notification to the user

                // don't forget to re-throw the error, otherwise the promise will resolve successfully
                throw error;
            });
    }

    /**
     * Finds one of the entities at the resource point
     * @param id
     * @returns {Promise<AxiosResponse<any>>}
     */
    async findOne(id) {
        return BaseApi.get(`/${this.resource}/${id}`);
    }

    /**
     * Deletes the entity at the resource point
     * @param id
     * @returns {Promise<AxiosResponse<any>>}
     */
    async delete(id) {
        return BaseApi.delete(`/${this.resource}/${id}`);
    }

    /**
     * Creates a new entity at the resource point
     * @param data
     * @returns {Promise<AxiosResponse<any>>}
     */
    async save(data) {
        return BaseApi.post(`/${this.resource}`, data);
    }

    /**
     * Updates the entity at the resource point
     * @param id
     * @returns {Promise<AxiosResponse<any>>}
     */
    async update(id) {
        return BaseApi.post(`/${this.resource}/${id}`);
    }
}
