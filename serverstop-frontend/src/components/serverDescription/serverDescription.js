import React from 'react';
import style from './serverDescription.module.css';
import moment from "moment";
import {dateFormatCurrent, dateFormatTarget} from "../../redux/store";

const ServerDescription = (props) => {

    let handleStartDate = (date) => {
        if (moment(date, dateFormatCurrent).startOf('d').valueOf() === moment('2023-08-24T00:00:00', dateFormatCurrent).valueOf()) {
            return <div className={style.serverDateToday}>Сегодня</div>
        } else if (moment(date, dateFormatCurrent).startOf('d').subtract(1, 'd').valueOf() === moment('2023-08-24T00:00:00', dateFormatCurrent).valueOf()) {
            return <div className={style.serverDateTomorrow}>Завтра</div>
        } else if (moment(date, dateFormatCurrent).startOf('d').add(1, 'd').valueOf() === moment('2023-08-24T00:00:00', dateFormatCurrent).valueOf()) {
            return <div className={style.serverDateYesterday}>Вчера</div>
        }
        return <div className={style.serverDate}>{moment(props.date, dateFormatCurrent).format(dateFormatTarget)}</div>;
    }

    let handleStatus = (status) => {
        if (status === 'vip') {
            return <div className={`${style.serverStatus} ${style.serverStatusVip}`}>VIP</div>
        } else {
            return <div className={`${style.serverStatus} ${style.serverStatusNormal}`}>●</div>
        }
    }

    return (
      <>
          <div className={style.serverDescription}>
              {handleStatus(props.status)}
              <div className={style.serverUrl}>{props.url}</div>
              <div className={style.serverRate}>{props.rate}</div>
              <div className={style.serverChronicle}>{props.chronicle}</div>
              {handleStartDate(props.date)}
          </div>
      </>
    );
}

export default ServerDescription;