import {createSlice} from "@reduxjs/toolkit";

let initialState = {
    form: {
        username: '',
        password: ''
    }
}

const loginPageSlice = createSlice({
    name: 'loginPage',
    initialState,
    reducers: {
        cacheLoginForm(state, action) {
            state.form = action.payload;
        }
    }
});

export const { cacheLoginForm } = loginPageSlice.actions;

export default loginPageSlice.reducer;

