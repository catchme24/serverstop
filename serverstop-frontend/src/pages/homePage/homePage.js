import React from 'react';
import ServersList from "../../components/serverList/serversList";
import style from './homePage.module.css';
import TagsBar from "../../components/tagsBar/tagsBar";
import Filter from "../../components/filter/filter";
import Seporator from "../../components/seporator/seporator";
import Advertisement from "../../components/advertisement/advertisement";
import {dateFormatCurrent} from "../../redux/store";
import moment from "moment";

const HomePage = (props) => {

    const compareDescending = (server1, server2) => {
        return (moment(server2.date, dateFormatCurrent) - moment(server1.date, dateFormatCurrent));
    }

    const compareAscending = (server1, server2) => {
        return (moment(server1.date, dateFormatCurrent) - moment(server2.date, dateFormatCurrent));
    }

    const sortServers = (servers, compareFunction) => {
        servers.map(group => {
            group.servers.sort(compareFunction);
        });
        return servers;
    };


    return (
        <>
            <div className={style.homePage}>
                <div className={style.homePageLeft}>
                    {
                        (props.serversWillOpen != null && props.serversWillOpen.length > 0) ?
                        <div className={style.homePageWillOpen}>
                            {sortServers(props.serversWillOpen, compareAscending)
                                .map(serverList => <ServersList servers={serverList.servers}
                                                                title={serverList.title}
                                                                key={serverList.title}
                                                    />
                                )}
                        </div> : <div className={style.homePageWillOpen}>Нету открывающихся серверов :(</div>
                    }
                    {
                        (props.serversAlreadyOpened != null && props.serversAlreadyOpened.length > 0) ?
                        <div className={style.homePageAlredyOpened}>
                            {sortServers(props.serversAlreadyOpened, compareDescending)
                                .map(serverList => <ServersList servers={serverList.servers}
                                                                title={serverList.title}
                                                                key={serverList.title}
                                                    />
                                )}
                        </div> : <div className={style.homePageAlredyOpened}>Нету открытых серверов :(</div>
                    }
                </div>
                <div className={style.homePageRight}>
                    <TagsBar
                        tags={props.chronicleTags}
                        changeFiltersByTag={props.changeFiltersByTag}
                    />
                    <Seporator />
                    <Filter
                        rates={props.rateTags}
                        chronicles={props.chronicleTags}
                        filterSelectedRate={props.filterSelectedRate}
                        filterSelectedChronicle={props.filterSelectedChronicle}

                        filterServers={props.filterServers}
                        changeSelectedRate={props.changeSelectedRate}
                        changeSelectedChronicle={props.changeSelectedChronicle}
                    />
                    <Seporator />
                    <Advertisement />
                    <Seporator />
                    <TagsBar
                        tags={props.rateTags}
                        changeFiltersByTag={props.changeFiltersByTag}
                    />
                    <Seporator />
                </div>
            </div>
        </>
    );
}

export default HomePage;