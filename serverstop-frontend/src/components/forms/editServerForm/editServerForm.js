import React, {useEffect, useState} from 'react';
import style from './editServerForm.module.css';
import {useDispatch, useSelector} from "react-redux";
import {Controller, useForm} from "react-hook-form";
import TextField from '../../textField/textField';
import {editFormsSelector, savingServersSelector} from "../../../redux/profilePage/profilePageSelector";
import {cacheEditForm, fetchDeleteServer, fetchUpdateServer} from "../../../redux/profilePage/profilePageReducer";
import {validateDate} from "../validators";

const EditServerForm = (props) => {
    const cachedForm = useSelector(editFormsSelector(props.server.id));
    const savingServers = useSelector(savingServersSelector);
    const isSavingOrDeliting = (savingServers.find(id => id === props.server.id) || null) === null ? false : true;


    const hasCachedState = cachedForm === null ? false : true;

    const [isEditing, toggleEditing ] = useState(hasCachedState ? cachedForm.isEditing : false);
    const dispatch = useDispatch();

    const formDefaultValues = {
        chronicle: props.server.chronicle,
        date: props.server.date,
        rate: props.server.rate,
        status: props.server.status,
        url: props.server.url
    };

    const { control, handleSubmit, getValues, reset, trigger } = useForm({
        defaultValues: hasCachedState ? cachedForm.form : formDefaultValues,
        mode: "onChange"
    });


    useEffect(() => {
        trigger();
        return () => {
            dispatch(cacheEditForm({id: props.server.id, form: getValues(), isEditing, isSavingOrDeliting}));
        };
    }, [isEditing]);

    const updateServer = (updatingServer) => {
        dispatch(fetchUpdateServer({...updatingServer, id: props.server.id}));
    }
    const deleteServer = () => {
        dispatch(fetchDeleteServer(props.server.id));
    }
    const editServer = () => {
        toggleEditing(true);
    }
    const cancelEditServer = () => {
        toggleEditing(false);
        reset(formDefaultValues);
    }


    return (
        <form className={style.container}>
            <div className={style.title}>Описание сервера</div>
            {/*<div className={style.error}>{ 'error' }</div>*/}

                <Controller name={'url'} control={control} rules={{
                        required: {value: true, message: 'Обязательное поле'},
                        minLength: {value: 5, message: 'Длинна больше 5 символов'},
                        maxLength: {value: 20, message: 'Длинна меньше 20 символов'}}}
                    render={({ field: { value, onChange}, fieldState: { error }}) => (
                        <TextField placeholder={'url'}
                            value={value} onChange={onChange} error={error !== undefined ? error : ''} disabled={isSavingOrDeliting || !isEditing}
                        />
                    )}
                />
                <Controller name={'chronicle'} control={control} rules={{
                    required: {value: true, message: 'Обязательное поле'},
                    maxLength: {value: 20, message: 'Длинна меньше 20 символов'}}}
                            render={({ field: { value, onChange}, fieldState: { error }}) => (
                                <TextField placeholder={'chronicle'}
                                    value={value} onChange={onChange} error={error !== undefined ? error : ''} disabled={isSavingOrDeliting || !isEditing}
                                />
                            )}
                />
                <Controller name={'rate'} control={control} rules={{
                    required: {value: true, message: 'Обязательное поле'},
                    minLength: {value: 2, message: 'Длинна больше 2 символов'},
                    maxLength: {value: 20, message: 'Длинна меньше 20 символов'},
                    pattern: {value: /^x{1}[0-9]{1,}$/, message: 'Примеры: x1, x55, x100000'}}}
                            render={({ field: { value, onChange}, fieldState: { error }}) => (
                                <TextField placeholder={'rate'}
                                    value={value} onChange={onChange} error={error !== undefined ? error : ''} disabled={isSavingOrDeliting || !isEditing}
                                />
                            )}
                />
                <Controller name={'date'} control={control} rules={{
                        required: {value: true, message: 'Обязательное поле'},
                        validate: validateDate,
                        pattern: {value: /^[0-3]{1}[0-9]{1}[.][0-1]{1}[0-9]{1}[.][0-9]{4}$/, message: 'Форматы даты: ДД.ММ.ГГГГ'}}}
                    render={({ field: { value, onChange}, fieldState: { error }}) => (
                        <div className={style.formInput}>
                            <TextField placeholder={'date'}
                                value={value} onChange={onChange} error={error !== undefined ? error : ''} disabled={isSavingOrDeliting || !isEditing}
                            />
                        </div>
                    )}
                />
                <Controller name={'status'} control={control} rules={{
                    required: {value: true, message: 'Обязательное поле'},
                    minLength: {value: 3, message: 'Длинна больше 3 символов'},
                    maxLength: {value: 15, message: 'Длинна меньше 15 символов'}}}
                            render={({ field: { value, onChange}, fieldState: { error }}) => (
                                <TextField placeholder={'status'}
                                    value={value} onChange={onChange} error={error !== undefined ? error : ''} disabled={isSavingOrDeliting || !isEditing}
                                />
                            )}
                />
            <div className={style.buttonsContainer}>
                {!isEditing &&
                    <button className={style.formButton} type={'button'}
                            disabled={isSavingOrDeliting} onClick={editServer}>Изменить</button>}
                {!isEditing &&
                    <button className={isSavingOrDeliting ? style.buttonSaveLoading : style.formButton} type={'button'}
                            disabled={isSavingOrDeliting} onClick={deleteServer}>Удалить</button>}
                {isEditing &&
                    <button className={isSavingOrDeliting ? style.buttonSaveLoading : style.formButton} type={'submit'}
                            disabled={isSavingOrDeliting} onClick={handleSubmit(updateServer)}>Сохранить</button>}
                {isEditing &&
                    <button className={style.formButton} type={'button'} onClick={cancelEditServer}
                            disabled={isSavingOrDeliting}>Отменить</button>}
            </div>
        </form>
    );
};

export default EditServerForm;