import React from 'react';
import style from './registrationPage.module.css';
import RegistrationForm from "../../components/registrationForm/registrationForm";

const RegistrationPage = (props) => {

    return (
        <div className={style.registrationPage}>
            <div className={style.registrationPageInner}>
                <div className={style.registrationForm}>
                    <RegistrationForm
                        registrationMessOrError={props.registrationMessOrError}
                        registratingFetchStatus={props.registratingFetchStatus}

                        fetchRegistrUser={props.fetchRegistrUser}
                    />
                </div>
            </div>
        </div>
    );
}

export default RegistrationPage;