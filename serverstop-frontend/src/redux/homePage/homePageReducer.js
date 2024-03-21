const FILTER_SERVERS = 'FILTER_SERVERS';
const CHANGE_SELECTED_RATE = 'CHANGE_SELECTED_RATE';
const CHANGE_SELECTED_CHRONICLE = 'CHANGE_SELECTED_CHRONICLE';
const CHANGE_FILTERS_BY_TAG = 'CHANGE_FILTERS_BY_TAG';
const SET_SERVERS = 'SET_SERVERS';
const SET_RATE_TAGS = 'SET_RATE_TAGS';
const SET_CHRONICLE_TAGS = 'SET_CHRONICLE_TAGS';

let initialState = {
    titles: [
        'УЖЕ ОТКРЫЛИСЬ',
        'ВЧЕРА',
        'ПРЕДЫДУШИЕ 7 ДНЕЙ',
        'НЕДЕЛЮ НАЗАД И БОЛЕЕ',
        'СКОРО ОТКРОЮТСЯ',
        'СЕГОДНЯ',
        'ЗАВТРА',
        'БЛИЖАЙШИЕ 7 ДНЕЙ',
        'ЧЕРЕЗ НЕДЕЛЮ И БОЛЕЕ',
    ],
    servers: [],
    filteredServers: null,
    chronicleTags: null,
    rateTags: null,
    filterSelectedRate: 'Все рейты',
    filterSelectedChronicle: 'Все хроники'
}

export const homePageReducer = (state = initialState, action = {}) => {

    switch (action.type) {
        case FILTER_SERVERS:
            let filteredServers;
            if (state.filterSelectedChronicle === 'Все хроники' && state.filterSelectedRate === 'Все рейты') {
                return {...state, filteredServers: null};
            } else if (state.filterSelectedChronicle === 'Все хроники') {
                filteredServers = state.servers.filter(s => s.rate === state.filterSelectedRate);
                return {...state, filteredServers};
            } else if (state.filterSelectedRate === 'Все рейты') {
                filteredServers = state.servers.filter(s => s.chronicle === state.filterSelectedChronicle);
                return {...state, filteredServers};
            }
            filteredServers = state.servers.filter(s => s.chronicle === state.filterSelectedChronicle && s.rate === state.filterSelectedRate)
            return {
                ...state, filteredServers
            };

        case CHANGE_SELECTED_RATE:
            return {...state, filterSelectedRate: action.rate};

        case CHANGE_SELECTED_CHRONICLE:
            return {...state, filterSelectedChronicle: action.chronicle};

        case CHANGE_FILTERS_BY_TAG:
            let filteredServersByTag;
            if (state.rateTags.includes(action.rateOrChronicle)) {
                filteredServersByTag = state.servers.filter(s => s.rate === action.rateOrChronicle);
            } else {
                filteredServersByTag = state.servers.filter(s => s.chronicle === action.rateOrChronicle);
            }
            return {...state, filteredServers: filteredServersByTag};

        case SET_SERVERS:
            return {...state, servers: action.servers};

        case SET_RATE_TAGS:
            return {...state, rateTags: action.rateTags};

        case SET_CHRONICLE_TAGS:
            return {...state, chronicleTags: action.chronicleTags};

        default:
            return state;
    }
    return state;
}

export const filterServers = (rate, chronicle) => {
    return {type: FILTER_SERVERS, rate, chronicle}
}

export const changeSelectedRate = (rate) => {
    return {type: CHANGE_SELECTED_RATE, rate}
}

export const changeSelectedChronicle = (chronicle) => {
    return {type: CHANGE_SELECTED_CHRONICLE, chronicle}
}

export const changeFiltersByTag = (rateOrChronicle) => {
    return {type: CHANGE_FILTERS_BY_TAG, rateOrChronicle}
}

export const setServers = (servers) => {
    return {type: SET_SERVERS, servers}
}

export const setRateTags = (rateTags) => {
    return {type: SET_RATE_TAGS, rateTags}
}

export const setChronicleTags = (chronicleTags) => {
    return {type: SET_CHRONICLE_TAGS, chronicleTags}
}