import store from '../index';

class AuthenticationTokenService {

    getToken() {
        return store.getState().authentication.authenticationToken;
    }

}

const authenticationTokenService = new AuthenticationTokenService();

export default authenticationTokenService;