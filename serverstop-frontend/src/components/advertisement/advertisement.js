import React from 'react';
import style from './advertisement.module.css';

const Advertisement = (props) => {


    return (
        <>
            <a href="https://www.cook.ru">
                <img className={style.advertisement} src="https://en.l2oops.com/_next/image?url=https%3A%2F%2Fadmin.l2oops.com%2Ftop-banners%2Fhmgu5j1RHDNJi_ZEWSUHe4Apn4dpy-V.jpg&w=256&q=75"/>
            </a>
        </>
    )

}

export default Advertisement;