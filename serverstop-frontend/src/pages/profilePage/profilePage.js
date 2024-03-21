import React from 'react';
import style from './profilePage.module.css';
import Seporator from "../../components/seporator/seporator";
import image from "../../img/avatar.png";
import AddServerForm from "../../components/forms/addServerForm/addServerForm";
import EditServerForm from "../../components/forms/editServerForm/editServerForm";

const ProfilePage = (props) => {

    const editableOrDisabledForm = (server) => {
        return (
            <div key={server.id} className={style.editServerFormContainer}>
                <EditServerForm
                    server={server}

                    addEditingServer={props.addEditingServer}
                    removeEditingServer={props.removeEditingServer}
                    addFetchingToUpdateServer={props.addFetchingToUpdateServer}
                    removeFetchingToUpdateServer={props.removeFetchingToUpdateServer}
                />
            </div>
        )
    };

    return (
        <div className={style.container}>
            <div className={style.profilePageInner}>
                <div className={style.headerWrapper}>
                    <div className={style.userdataWrapper}>
                        <div className={style.avatarAndButton}>
                            <div className={style.avatarContainer}>
                                <img className={style.image} src={image}></img>
                            </div>
                            <button className={style.changeButton}>Сменить аватар</button>
                        </div>
                        <div className={style.nameContainer}>
                            <div>{'ЗАГЛУША'}</div>
                            <button className={style.changeButton}>Сменить имя</button>
                        </div>
                    </div>

                    <div className={style.addServerWrapper}>
                        <div className={style.addServerFormContainer}>
                            <AddServerForm />
                        </div>
                    </div>
                </div>
                <Seporator />
                {props.servers && props.servers.map(s => {
                        return editableOrDisabledForm(s);
                    })
                }
            </div>
        </div>
    );
}

export default ProfilePage;