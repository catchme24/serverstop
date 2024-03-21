import React, {useRef} from 'react';
import {Link, useNavigate} from "react-router-dom";
import style from './header.module.css';


const Header = (props) => {
    const {isAuthenticated, username, deleteAuthentication} = props;
    const navigate = useNavigate();

    const logout = () => {
        deleteAuthentication();
        navigate("/home", { replace: true });
    };

    const handleAuthenticatedLinks = () => {
        if (isAuthenticated) {
            return  <div className={style.menuBlock}>
                        <Link className={style.menuLink} to={'/login'} >Логин</Link>
                        <Link className={style.menuLink} to={'/registration'} >Регистрация</Link>
                    </div>
        } else {
            return  <div className={style.menuBlock}>
                        <Link className={style.menuLink} to={'/profile'} >{username}</Link>
                        <button className={style.menuLink} onClick={logout}>Выйти</button>
                    </div>
        }
    }

    return (
        <header className={style.header}>
            <div className={style.headerMenu}>
                <menu className={style.menu}>
                    <div className={style.menuBlock}>
                        <Link className={style.menuLink} to={'/home'}>Главная</Link>
                        {/*<Link className={style.menuLink} to="/error" >Ошибка</Link>*/}
                    </div>
                    <div className={style.menuBlock}>
                        {!isAuthenticated && <Link className={style.menuLink} to="/login" >Логин</Link>}
                        {!isAuthenticated && <Link className={style.menuLink} to="/registration" >Регистрация</Link>}
                        {isAuthenticated && <Link className={style.menuLink} to={'/profile'} >{username}</Link>}
                        {isAuthenticated && <button className={style.menuLink} onClick={logout}>Выйти</button>}
                    </div>
                </menu>
            </div>
        </header>
    )
}


export default Header;