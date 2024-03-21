
export const userServersSelector = (state) => {
    return state.profilePage.userServers;
};

export const userServersFetchStatusSelector = (state) => {
    return state.profilePage.userServersFetchStatus;
}

export const savingServersSelector = (state) => {
    return state.profilePage.fetchingToUpdateOrDeleteServers;
}

export const editingServersSelector = (state) => {
    return state.profilePage.editingServers;
}


export const editFormsSelector = (serverId) => {
    return (state) => {
        return state.profilePage.editForms.find(editForm => editForm.id === serverId) || null;
    }
}

export const addFormSelector = (state) => {
    return state.profilePage.addForm;
}

export const addServerFetchStatusSelector = (state) => {
    return state.profilePage.addServerFetchStatus;
}

export const cachedEditFormsSelector = (state) => {
    return state.profilePage.editForms;
}


export const isFormEditingSelector = (serverId) => {
    return (state) => {
        let finded = state.profilePage.editingServers.find(id => serverId === id) || null;
        return finded === null ? false : true;
    }
}

export const isFormSavingSelector = (serverId) => {
    return (state) => {
        let finded = state.profilePage.fetchingToUpdateServers.find(id => serverId === id) || null;
        return finded === null ? false : true;
    }
}

