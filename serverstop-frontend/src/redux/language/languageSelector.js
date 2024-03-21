
export const languagesSelector = (state) => {
    return state.language.languages;
}

export const currentLanguageSelector = (state) => {
    return state.language.currentLanguage;
}

export const isNightModeSelector = (state) => {
    return state.language.isNightMode;
}
