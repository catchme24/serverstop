import React from 'react';
import LanguageContainer from "../language/languageContainer";
import {Navigate, Route, Routes} from "react-router-dom";
import style from './pageLoader.module.css';
import HomePageContainer from "../../pages/homePage/homePageContainer";
import LoginPageContainer from "../../pages/loginPage/loginPageContainer";
import ProfilePageContainer from "../../pages/profilePage/profilePageContainer";
import RegistrationPageContainer from "../../pages/registrationPage/registrationPageContainer";
import NotFoundPage from "../../pages/notFoundPage/notFoundPage";

const PageLoader = () => {



    return (
        <main className={style.main}>
            <div className={style.mainComponents}>

                <LanguageContainer />

                <div>
                    <Routes>
                        <Route path={'/home'} element={<HomePageContainer />}/>
                        <Route path={'/login'} element={<LoginPageContainer />}/>
                        <Route path={'registration'} element={<RegistrationPageContainer />}/>
                        <Route path={'/profile'} element={<ProfilePageContainer />}/>
                        <Route path={'/error'} element={<NotFoundPage />}/>
                        <Route path={'/*'} element={<Navigate to={'/error'} />} />
                    </Routes>
                </div>

            </div>
        </main>
    )
}

export default PageLoader;