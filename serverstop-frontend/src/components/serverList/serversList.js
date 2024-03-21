import React from 'react';
import ServerDescription from "../serverDescription/serverDescription";
import style from './serversList.module.css';

const ServersList = (props) => {

    const handleServerList = (servers) => {
        return servers.length === 0 ? <div></div> :
            <div className={style.serverList}>
                <h3 className={style.serverListTitle}>{props.title}</h3>
                {props.servers.map(s => <ServerDescription
                    key={s.id}
                    status={s.status}
                    url={s.url}
                    rate={s.rate}
                    chronicle={s.chronicle}
                    date={s.date}
                />)}
            </div>;
    }


    return (
        <>
            {handleServerList(props.servers)}
        </>
    )
}

export default ServersList;