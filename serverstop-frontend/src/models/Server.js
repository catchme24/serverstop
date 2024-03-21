import {dateFormatCurrent, dateFormatTarget} from "../redux/store";
import moment from "moment";
import {ServerDto} from "./ServerDto";

export class Server {

    id;
    date;
    chronicle;
    rate;
    url;
    status;

    constructor() {

    }

    initServer(serverDto) {
        this.id = serverDto.id;
        this.date = moment(serverDto.serverStart, dateFormatCurrent).format(dateFormatTarget);
        this.chronicle = serverDto.chronicle;
        this.rate = serverDto.serverRate;
        this.url = serverDto.domain;
        this.status = serverDto.status;
    }

    toServer() {
        let serverDto = new ServerDto();
        serverDto.initServerDto(this);
        return serverDto;
    }
}