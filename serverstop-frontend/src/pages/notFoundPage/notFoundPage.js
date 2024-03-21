import React from 'react';
import {withAuthVerification} from "../../HOC/withAuthVerification/withAuthVerification";
import TextField from "../../components/textField/textField";
import style from './notFoundPage.module.css';
import {Controller, useForm} from "react-hook-form";
import {Link, Navigate, Route, Routes} from "react-router-dom";
import HomePageContainer from "../homePage/homePageContainer";
import LoginPageContainer from "../loginPage/loginPageContainer";
import RegistrationPageContainer from "../registrationPage/registrationPageContainer";
import ProfilePageContainer from "../profilePage/profilePageContainer";

const NotFoundPage = () => {

    return(
        <div className={style.notFoundPageContainer}>
            Такой станички не существует :(
            <Link to={'/home'}>
                перейти на домашнюю
            </Link>
        </div>
        );

}

// let withAuth = withAuthVerification(NotFoundPage);

export default NotFoundPage;