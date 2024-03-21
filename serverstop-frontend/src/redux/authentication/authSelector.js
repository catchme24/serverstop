
export const authFetchStatusSelector = (state) => {
    return state.authentication.authentictingFetchStatus;
}

export const registrFetchStatusSelector = (state) => {
    return state.authentication.registratingFetchStatus;
}

export const registrationMessOrErrorSelector = (state) => {
    return state.authentication.registrationMessOrError;
}

export const authErrorSelector = (state) => {
    return state.authentication.authenticationError;
}

export const authTokenSelector = (state) => {
    return state.authentication.authenticationToken;
}

export const authUsernameSelector = (state) => {
    return state.authentication.authenticatedUsername;
}

export const isAuthenticatedSelector = (state) => {
    return state.authentication.isAuthenticated;
}
