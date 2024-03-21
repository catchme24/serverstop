import axios from "axios";

class AuthenticationService {
    #API_URL = 'http://localhost:8080/api/auth/';

    #httpClient;

    constructor() {
        this.#httpClient = axios.create({
            withCredentials: false,
            baseURL: this.#API_URL,
        });
    }

    login(user) {
        return this.#httpClient.post('login', user)
            .then(response => response);
    }

    registration(newUser) {
        return this.#httpClient.post('registration', newUser)
            .then(response => response);
    }
}

const authenticationService = new AuthenticationService();

export default authenticationService;