import axios from "axios";
import authenticationTokenService from "./authenticationTokenService";

class ServersService {
    #API_URL = 'http://localhost:8080/api/servers';

    #httpClient;

    constructor() {
        this.#httpClient = axios.create({
            withCredentials: false,
            baseURL: this.#API_URL,
            headers: {

            }
        });
    }

    getAll() {
        return this.#httpClient.get('')
            .then(response => response.data);
    }

    getById(id) {
        return this.#httpClient.get(`/${id}`, {headers: {Authorization: 'Bearer ' + authenticationTokenService.getToken()}})
                                .then(response => response.data);
    }

    add(server) {
        return this.#httpClient.post('', server , {headers: {Authorization: 'Bearer ' + authenticationTokenService.getToken()}})
                                .then(response => response.data);
    }

    update(server) {
        return this.#httpClient.put('', server, {headers: {Authorization: 'Bearer ' + authenticationTokenService.getToken()}})
                                .then(response => response.data);
    }

    delete(serverId) {
        return this.#httpClient.delete(`/${serverId}`, {headers: {Authorization: 'Bearer ' + authenticationTokenService.getToken()}})
            .then(response => response.data);
    }

    getMyServers() {
        return this.#httpClient.get('/me', {headers: {Authorization: 'Bearer ' + authenticationTokenService.getToken()}})
            .then(response => response.data);
    }

    getAllRates() {
        return this.#httpClient.get('/allrates', {headers: authenticationTokenService.getToken()})
            .then(response => response.data);
    }

    getAllChronicles() {
        return this.#httpClient.get('/allchronicles', {headers: authenticationTokenService.getToken()})
            .then(response => response.data);
    }
}

const serversService = new ServersService();

export default serversService;