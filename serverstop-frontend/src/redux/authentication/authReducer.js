import authenticationService from "../../services/authenticationService";
import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import {deleteUserInfo} from "../profilePage/profilePageReducer";

let initialState = {
    authentictingFetchStatus: 'idle',
    registratingFetchStatus: 'idle',
    registrationMessOrError: '',
    authenticationError: '',
    authenticationToken: null,
    authenticatedUsername: null,
    isAuthenticated: false,
}

if (localStorage.getItem('token') !== null) {
    initialState = {
        authentictingFetchStatus: 'idle',
        registratingFetchStatus: 'idle',
        registrationMessOrError: '',
        authenticationError: '',
        authenticationToken: localStorage.getItem('token'),
        authenticatedUsername: localStorage.getItem('username'),
        isAuthenticated: true,
    }
}

const authSlice = createSlice({
    name: 'authentication',
    initialState,
    reducers: {
        deleteAuthentication(state) {
            debugger;
            localStorage.removeItem('token');
            localStorage.removeItem('username');
            state.isAuthenticated = false;
            state.authenticationToken = null;
        }
    },
    extraReducers: (builder) => {
        builder
            .addCase(fetchLoginUser.pending, (state, action) => {
                console.log('Пендинг: ' + JSON.stringify(action.meta.arg));
                state.authentictingFetchStatus = 'pending';
                state.authenticationError = '';
            })
            .addCase(fetchLoginUser.fulfilled, (state, action) => {
                state.authentictingFetchStatus = 'fulfilled';
                localStorage.setItem('token', action.payload.data[0]);
                localStorage.setItem('username', action.meta.arg.username);
                state.isAuthenticated = true;
                state.authenticationToken = action.payload.data[0];
                state.authenticatedUsername = action.meta.arg.username;
                state.authenticationError = '';
                state.registrationMessOrError = '';
            })
            .addCase(fetchLoginUser.rejected, (state, action) => {
                console.log('Реджектед: ' + JSON.stringify(action.meta.arg));
                state.authentictingFetchStatus = 'rejected';
                state.authenticationError = 'Неверный логин или пароль';
            })
            .addCase(fetchRegistrUser.pending, (state, action) => {
                state.registratingFetchStatus = 'pending';
                state.registrationMessOrError = '';
            })
            .addCase(fetchRegistrUser.fulfilled, (state, action) => {
                debugger;
                state.registrationMessOrError = 'Успешная регистрация!';
                state.registratingFetchStatus = 'fulfilled';

            })
            .addCase(fetchRegistrUser.rejected, (state, action) => {
                state.registratingFetchStatus = 'rejected';
                state.registrationMessOrError = 'Такой пользователь уже существует!';
            })
        ;
    },
});

export const { deleteAuthentication } = authSlice.actions;

export default authSlice.reducer;

export const fetchLoginUser = createAsyncThunk(
    'authentication/fetchLoginUser',
    async (loginData) => {
        console.log('В санке: ' + JSON.stringify(loginData));
        const responce = await authenticationService.login(loginData);
        return responce;
    }
);

export const fetchRegistrUser = createAsyncThunk(
    'authentication/fetchRegistrUser',
    async (registrationData) => {
        debugger;
        const responce = await authenticationService.registration(registrationData);
        return responce;
    }
);

export const deleteAuth = () => {
    let kek = new Object();
    debugger;
    return (dispatch) => {
        dispatch(deleteAuthentication());
        dispatch(deleteUserInfo());
    }
}