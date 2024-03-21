import {createSlice} from "@reduxjs/toolkit";

let initialState = {
    languages: ['EN', 'RU'],
    currentLanguage: 'RU',
    isNightMode: false
}

const languageSlice = createSlice({
    name: 'language',
    initialState: initialState,
    reducers: {
        toggleNightMode(state) {
            state.isNightMode = state.isNightMode ? false : true;
        },
        changeLanguage(state, action) {
            state.currentLanguage = action.payload.language;
        }

    }
});

export const { toggleNightMode, changeLanguage } = languageSlice.actions;
export default languageSlice.reducer;