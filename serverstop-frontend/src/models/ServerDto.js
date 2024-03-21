import {dateFormatCurrent, dateFormatTarget} from "../redux/store";
import moment from "moment";
import {Server} from "./Server";

export class ServerDto {

    id;
    serverStart;
    chronicle;
    serverRate;
    domain;
    status;

    constructor() {

    }

    initServerDto(server) {
        this.id = server.id;
        this.serverStart = moment(server.date, dateFormatTarget).format(dateFormatCurrent);
        this.chronicle = server.chronicle;
        this.serverRate = server.rate;
        this.domain = server.url;
        this.status = server.status;
    }

    toServer() {
        let server = new Server();
        server.initServer(this);
        return server;
    }
}