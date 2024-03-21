import {createSlice} from "@reduxjs/toolkit";

let initialState = {
    form: {
        username: '',
        password: ''
    }
}

const registrationPageSlice = createSlice({
    name: 'registrationPage',
    initialState,
    reducers: {
        cacheRegistrationForm(state, action) {
            state.form = action.payload;
        }
    }
});

export const { cacheRegistrationForm } = registrationPageSlice.actions;

export default registrationPageSlice.reducer;
