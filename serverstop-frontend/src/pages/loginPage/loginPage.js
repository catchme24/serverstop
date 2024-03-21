import React from 'react';
import style from './loginPage.module.css';
import LoginForm from "../../components/forms/loginForm/loginForm";

const LoginPage = (props) => {

    return (
        <div className={style.loginPage}>
            <div className={style.loginPageInner}>
                <div className={style.loginForm}>
                    <LoginForm
                        authentictingFetchStatus={props.authentictingFetchStatus}
                        authenticationError={props.authenticationError}

                        fetchLoginUser={props.fetchLoginUser}
                    />
                </div>
            </div>
        </div>
    );
}

export default LoginPage;