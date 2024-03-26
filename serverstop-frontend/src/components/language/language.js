import React from 'react';
import style from './language.module.css';
import {Link} from "react-router-dom";


const Language = (props) => {


    const isNightMode = () => {
        if (props.isNightMode) {
            return <div className={`${style.buttonMode} ${style.buttonModeNight}`} onClick={toggleNightMode}>Режим</div>;
        } else {
            return <div className={`${style.buttonMode} ${style.buttonModeDay}`} onClick={toggleNightMode}>Режим</div>;
        }
    }

    const isCurrentLanguage = (language) => {
        if (language == props.currentLanguage) {
            return style.buttonLanguageSelected;
        } else {
            return style.buttonLanguage;
        }
    }


    const toggleNightMode = () => {
        props.toggleNightMode();
    }

    const changeLanguage = (e) => {
        props.changeLanguage({language: e.target.innerText});
    }

    return (
        <div>
            <div className={style.language}>
                <p className={style.title}>АНОНСЫ СЕРВЕРОВ LINEAGE 2</p>
                <div className={style.buttons}>
                    {isNightMode()}
                    <div className={style.languageButtons}>
                        <div
                            className={`${style.buttonLanguage} ${style.buttonLanguageLeft} ${isCurrentLanguage(props.languages[0])}`}
                            onClick={changeLanguage}>{props.languages[0]}
                        </div>
                        <div
                            className={`${style.buttonLanguage} ${style.buttonLanguageRight} ${isCurrentLanguage(props.languages[1])}`}
                            onClick={changeLanguage}>{props.languages[1]}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Language;