import {createSelector} from "reselect";
import moment from "moment/moment";
import {dateFormatCurrent} from "../store";


export const getServers = (state) => {
    return state.homePage.servers;
};

export const getTitles = (state) => {
    return state.homePage.titles;
};

export const getRateTags = (state) => {
    return state.homePage.rateTags;
};

export const getChronicleTags = (state) => {
    return state.homePage.chronicleTags;
};

export const getFilterSelectedChronicle = (state) => {
    return state.homePage.filterSelectedChronicle;
};

export const getFilterSelectedRate = (state) => {
    return state.homePage.filterSelectedRate;
};

export const getFilteredServers = (state) => {
    return state.homePage.filteredServers;
};

export const getGroupedByDate = createSelector(getServers, getFilteredServers, getTitles, (stateServers, filteredServers, titles) => {

    let servers;

    if (filteredServers === null) {
        servers = stateServers;
    } else if (filteredServers.length === 0) {
        return filteredServers;
    } else {
        servers = filteredServers;
    }

    if (!servers)
        return null;

    let groupServers = [];
    titles.map(name => groupServers.push({title: name, servers:[]}));
    let startServer;
    // let currentDate = moment('24-08-2023', dateFormatCurrent);
    let currentDate = moment('2023-08-24T00:00:00', dateFormatCurrent).startOf('d');

    groupServers[0].servers = vipServersAlreadyOpened(servers, currentDate);
    groupServers[4].servers = vipServersWillOpen(servers, currentDate);

    let tomorrow = moment.duration(1, 'd');
    let yesterday = moment.duration(-1, 'd');
    let nextWeek = moment.duration(7, 'd');
    let lastWeek = moment.duration(-7, 'd');
    let today = moment.duration(0, 'd');

    servers.map(s => {
        startServer = moment(s.date, dateFormatCurrent).startOf('d');
        let daysDifference = startServer - currentDate;

        switch (daysDifference) {
            case today.valueOf():
                groupServers[5].servers.push(s);
                break;
            case tomorrow.valueOf():
                groupServers[6].servers.push(s);
                break;
            case yesterday.valueOf():
                groupServers[1].servers.push(s);
                break;
            default:
                if (daysDifference > today.valueOf()) {
                    if (daysDifference >= nextWeek.valueOf()) {
                        groupServers[8].servers.push(s);
                    } else {
                        groupServers[7].servers.push(s);
                    }
                } else {
                    if (daysDifference < lastWeek.valueOf()) {
                        groupServers[3].servers.push(s);
                    } else {
                        groupServers[2].servers.push(s);
                    }
                }
        }
    });


    return groupServers;
});

export const getGroupedByDate2 = createSelector(getServers, getFilteredServers, getTitles, (stateServers, filteredServers, titles) => {
    let servers;

    if(stateServers.length === 0) {
        return null;
    } else if (filteredServers == null) {
        servers = [...stateServers];
    } else if (filteredServers.length > 0) {
        servers = [...filteredServers];
    } else {
        return null;
    }

    let groupServers = [];
    titles.map(name => groupServers.push({title: name, servers:[]}));
    let startServer;
    let currentDate = moment('2023-08-24T00:00:00', dateFormatCurrent).startOf('d');

    groupServers[0].servers = vipServersAlreadyOpened(servers, currentDate);
    groupServers[4].servers = vipServersWillOpen(servers, currentDate);

    let tomorrow = moment.duration(1, 'd');
    let yesterday = moment.duration(-1, 'd');
    let nextWeek = moment.duration(7, 'd');
    let lastWeek = moment.duration(-7, 'd');
    let today = moment.duration(0, 'd');

    servers.map(s => {
        startServer = moment(s.date, dateFormatCurrent).startOf('d');
        let daysDifference = startServer - currentDate;

        switch (daysDifference) {
            case today.valueOf():
                groupServers[5].servers.push(s);
                break;
            case tomorrow.valueOf():
                groupServers[6].servers.push(s);
                break;
            case yesterday.valueOf():
                groupServers[1].servers.push(s);
                break;
            default:
                if (daysDifference > today.valueOf()) {
                    if (daysDifference >= nextWeek.valueOf()) {
                        groupServers[8].servers.push(s);
                    } else {
                        groupServers[7].servers.push(s);
                    }
                } else {
                    if (daysDifference < lastWeek.valueOf()) {
                        groupServers[3].servers.push(s);
                    } else {
                        groupServers[2].servers.push(s);
                    }
                }
        }
    });

    return groupServers;
});

const vipServersWillOpen = (servers, currentDate) => {
    let sortedServers = [];
    servers.map(s => {
        let startServer = moment(s.date, dateFormatCurrent).startOf('d');
        if (s.status === 'vip' && startServer.valueOf() >= currentDate.valueOf()) {
            sortedServers.push(s);
        }
    });
    return sortedServers;
}

const vipServersAlreadyOpened = (servers, currentDate) => {
    let sortedServers = [];
    servers.map(s => {
        let startServer = moment(s.date, dateFormatCurrent).startOf('d');
        if (s.status === 'vip' && startServer.valueOf() < currentDate.valueOf()) {
            sortedServers.push(s);
        }
    });
    return sortedServers;
}