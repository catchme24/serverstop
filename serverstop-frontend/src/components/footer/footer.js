import React from 'react';
import {Link} from 'react-router-dom';
import style from './footer.module.css'
import Seporator from "../seporator/seporator";

const Header = () => {

    return (
        <footer className={style.footer}>
            <div className={style.footerDescription}>
                <h2 className={style.descriptionTitle}>Новые сервера л2 с большим онлайном</h2>
                <p className={style.descriptionLine}>Анонсы серверов Lineage 2 всех рейтов и хроник. Не пропустите открытие серверов л2 уже сегодня!</p>
                <p className={style.descriptionLine}>Здесь размещаются наиболее качественные проекты. Вверху списка закреплены Премиум сервера L2. Начиная играть на таком проекте вы можете быть уверены в его стабильности и хорошем онлайне.</p>
                <p className={style.descriptionLine}>На нашем сайте вы сможете найти сервера ла2 различных концепций, таких как: PvP, крафт, low rate, GvE, RvR, пвп-крафт, мультикрафт, мультипрофа, классической версии игры и с дополнениями.</p>
                <p className={style.descriptionLine}>Оставайтесь с нами и вы будете в курсе новых открытий!</p>
            </div>
            <div className={style.footerInformation}>
                <h2 className={style.informationTitle}>
                    <Link className={style.footerLink} to="www.topsite.com" >Инфомарция:</Link>
                </h2>
                <div className={style.informationContacts}>
                    <a className={style.footerLink} href="www.topsite.com">404-567-101</a>
                    <a className={style.footerLink} href="www.topsite.com">ВХОД В ЧАТ</a>
                </div>
            </div>
            <Seporator />
            <div className={style.footerCopyright}>
                <p>©2012-2023 L2oops.com</p>
                <a className={style.footerLink} href="www.topsite.com" >mady by Catchme</a>
            </div>
        </footer>
    )
}


export default Header;