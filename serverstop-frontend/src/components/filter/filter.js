import React from 'react';
import style from './filter.module.css';
import {changeSelectedRate, changeSelectedChronicle, filterServers} from '../../redux/homePage/homePageReducer';

const Filter = (props) => {

    const selectedRate = React.createRef();
    const selectedChronicle = React.createRef();

    const filter = () => {
        props.filterServers();
    }

    const changeRate = () => {
        props.changeSelectedRate(selectedRate.current.value);
    }

    const changeChronicle = () => {
        props.changeSelectedChronicle(selectedChronicle.current.value);
    }


    return (
        <>
            <div className={style.filter}>
                <div className={style.dropdownMenuContainer}>
                    <select ref={selectedRate} className={style.dropdownMenu} onChange={changeRate} value={props.filterSelectedRate}>
                        <option>Все рейты</option>
                        {props.rates != null && props.rates.map(rate => <option key={rate}>{rate}</option>)}
                    </select>
                </div>
                <div>
                    <select ref={selectedChronicle} className={style.dropdownMenu} onChange={changeChronicle} value={props.filterSelectedChronicle}>
                        <option>Все хроники</option>
                        {props.chronicles != null && props.chronicles.map(chronicle => <option key={chronicle}>{chronicle}</option>)}
                    </select>
                </div>
                <button className={style.searchButton} onClick={filter}>Искать сервера</button>
                <button className={style.searchButton}>Обновить</button>
            </div>
        </>
    )

}

export default Filter;