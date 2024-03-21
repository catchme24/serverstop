import serversService from "../../services/serversService";
import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import {ServerDto} from "../../models/ServerDto";
import {Server} from "../../models/Server";

let initialState = {
    userServers: null,
    userServersFetchStatus: 'idle',
    editForms: [],
    editingServers: [],
    fetchingToUpdateOrDeleteServers: [],
    addForm: null,
    addServerFetchStatus: 'idle'
}

const profilePageSlice = createSlice({
    name: 'profilePage',
    initialState: initialState,
    reducers: {
        cacheEditForm(state, action) {
            state.editForms = state.editForms.filter(form => form.id !== action.payload.id);
            state.editForms.push(action.payload);
        },
        cacheAddForm(state, action) {
            state.addForm = null;
            state.addForm = action.payload;
        },
        deleteUserInfo(state, action) {
            state.userServers = null;
            state.editForms = [];
            state.editingServers = [];
            state.addForm = null;
        }
    },
    extraReducers: (builder) => {
        builder
            .addCase(fetchUpdateServer.pending, (state, action) => {
                state.fetchingToUpdateOrDeleteServers.push(action.meta.arg.id);
            })
            .addCase(fetchUpdateServer.fulfilled, (state, action) => {
                state.fetchingToUpdateOrDeleteServers = state.fetchingToUpdateOrDeleteServers.filter(id => id !== action.payload[0].id);
                state.userServers = state.userServers.filter(server => server.id !== action.payload[0].id);
                let updatedServer = new Server();
                updatedServer.initServer(action.payload[0]);
                state.userServers.push(updatedServer);
                state.editingServers = state.editingServers.filter(id => id !== action.meta.arg.id);
            })
            .addCase(fetchUpdateServer.rejected, (state, action) => {
                state.fetchingToUpdateOrDeleteServers = state.fetchingToUpdateOrDeleteServers.filter(id => id !== action.meta.arg.id);
            })
            .addCase(fetchUserServers.pending, (state, action) => {
                state.userServersFetchStatus = 'pending';
            })
            .addCase(fetchUserServers.fulfilled, (state, action) => {
                const data = action.payload
                    .map(serverDto => {
                        let server = new Server();
                        server.initServer(serverDto);
                        return server;
                    });
                state.userServers = data;
                state.userServersFetchStatus = 'fulfilled';
            })
            .addCase(fetchUserServers.rejected, (state, action) => {
                state.userServersFetchStatus = 'rejected';
            })
            .addCase(fetchDeleteServer.pending, (state, action) => {
                state.fetchingToUpdateOrDeleteServers.push(action.meta.arg);
            })
            .addCase(fetchDeleteServer.fulfilled, (state, action) => {
                state.fetchingToUpdateOrDeleteServers = state.fetchingToUpdateOrDeleteServers.filter(id => id !== action.meta.arg);
                state.userServers = state.userServers.filter(server => server.id !== action.meta.arg);
            })
            .addCase(fetchDeleteServer.rejected, (state, action) => {
                state.fetchingToUpdateOrDeleteServers = state.fetchingToUpdateOrDeleteServers.filter(id => id !== action.meta.arg.id);
            })
            .addCase(fetchAddServer.pending, (state, action) => {
                state.addServerFetchStatus = 'pending';
            })
            .addCase(fetchAddServer.fulfilled, (state, action) => {
                let addedServer = new Server();
                addedServer.initServer(action.payload[0]);
                state.userServers.push(addedServer);
                state.addServerFetchStatus = 'fulfilled';
            })
            .addCase(fetchAddServer.rejected, (state, action) => {
                state.addServerFetchStatus = 'rejected';
            });
    }
});

export const {  addEditingServer, removeEditingServer,
                addFetchingToUpdateServer, removeFetchingToUpdateServer,
                cacheEditForm, cacheAddForm, removeEditFormFromCache, deleteUserInfo } = profilePageSlice.actions;

export default profilePageSlice.reducer;

export const fetchUserServers = createAsyncThunk(
    'profilePage/fetchUserServers',
    async () => {
        const responce = await serversService.getMyServers();
        return responce;
    }
);

export const fetchUpdateServer = createAsyncThunk(
    'profilePage/fetchUpdateServer',
    async (server) => {
        let updatedServer = new ServerDto();
        updatedServer.initServerDto(server);
        const responce = await serversService.update(updatedServer);
        return responce;
    }
);

export const fetchDeleteServer = createAsyncThunk(
    'profilePage/fetchDeleteServer',
    async (serverId) => {
        const responce = await serversService.delete(serverId);
        return responce;
    }
);

export const fetchDeleteServerTRUE = (serverId) => {

    return (dispatch) => {
        debugger;
        serversService.delete(serverId)
            .then((responce) => {
                debugger;
                console.log(responce);
            })
            .catch((error) => {
                debugger;
                console.log(error);
            });
        debugger;
    }
}

export const fetchAddServer = createAsyncThunk(
    'profilePage/fetchAddServer',
    async (server) => {
        let addingServer = new ServerDto();
        addingServer.initServerDto(server);
        const responce = await serversService.add(addingServer);
        return responce;
    }
);
