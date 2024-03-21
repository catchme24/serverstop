import languageReducer from "./language/languageReducer";
import authReducer from "./authentication/authReducer";

import {homePageReducer} from "./homePage/homePageReducer";
import loginPageReducer from "./loginPage/loginPageReducer";
import profilePageReducer from "./profilePage/profilePageReducer";
import registrationPageReducer from "./registrationPage/registrationPageReducer";

import {configureStore} from "@reduxjs/toolkit";

export const dateFormatCurrent = 'YYYY-MM-DDThh:mm:ss';
export const dateFormatTarget = 'DD.MM.YYYY';

const store = configureStore({
    reducer: {
        language: languageReducer,
        authentication: authReducer,

        homePage: homePageReducer,
        loginPage: loginPageReducer,
        registrationPage: registrationPageReducer,
        profilePage: profilePageReducer
    },
    middleware: (getDefaultMiddleware) =>
        getDefaultMiddleware({
            serializableCheck: false,
        })
});

window.store = store;

export default store;