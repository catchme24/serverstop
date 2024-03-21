import React, {useEffect} from 'react';
import style from './addServerForm.module.css';
import {useDispatch, useSelector} from "react-redux";
import {addFormSelector, addServerFetchStatusSelector} from "../../../redux/profilePage/profilePageSelector";
import {Controller, useForm} from "react-hook-form";
import {cacheAddForm, cacheEditForm, fetchAddServer} from "../../../redux/profilePage/profilePageReducer";
import TextField from "../../textField/textField";
import {validateDate} from "../validators";

const AddServerForm = (props) => {
    const cachedForm = useSelector(addFormSelector);
    const fetchStatus = useSelector(addServerFetchStatusSelector);
    const isFetching = fetchStatus === 'pending' ? true : false;

    const hasCachedState = cachedForm === null ? false : true;

    const dispatch = useDispatch();

    const formDefaultValues = {
        chronicle: '',
        date: '',
        rate: '',
        status: '',
        url: ''
    };

    const { control, handleSubmit, getValues, reset, trigger } = useForm({
        defaultValues: hasCachedState ? cachedForm : formDefaultValues,
        mode: "onChange"
    });

    useEffect(() => {
        return () => {
            dispatch(cacheAddForm(getValues()));
        };
    }, []);

    const addServer = (addingServer) => {
        dispatch(fetchAddServer(addingServer));
    }
    const clearForm = () => {
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
                        render={({ field: { value, onChange, onBlur}, fieldState: { error }}) => (
                            <TextField placeholder={'url'} onBlur={onBlur}
                                value={value} onChange={onChange} error={error !== undefined ? error : ''} disabled={isFetching}
                            />
                        )}
            />
            <Controller name={'chronicle'} control={control} rules={{
                required: {value: true, message: 'Обязательное поле'},
                maxLength: {value: 20, message: 'Длинна меньше 20 символов'}}}
                        render={({ field: { value, onChange}, fieldState: { error }}) => (
                            <TextField placeholder={'chronicle'}
                                value={value} onChange={onChange} error={error !== undefined ? error : ''} disabled={isFetching}
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
                                value={value} onChange={onChange} error={error !== undefined ? error : ''} disabled={isFetching}
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
                                    value={value} onChange={onChange} error={error !== undefined ? error : ''} disabled={isFetching}
                                />
                            </div>
                        )}
            />
            <Controller name={'status'} control={control} rules={{
                required: {value: true, message: 'Обязательное поле'},
                minLength: {value: 5, message: 'Длинна больше 5 символов'},
                maxLength: {value: 15, message: 'Длинна меньше 15 символов'}}}
                        render={({  field: { value, onChange}, fieldState: { error }}) =>
                            <TextField placeholder={'status'}
                                value={value} onChange={onChange} error={error !== undefined ? error : ''} disabled={isFetching}
                            />
                        }
            />
            <div className={style.buttonsContainer}>
                <button className={isFetching ? style.buttonSaveLoading : style.formButton}
                        type={'submit'} disabled={isFetching} onClick={handleSubmit(addServer)}>Добавить</button>
                <button className={style.formButton} type={'button'} onClick={clearForm}
                        disabled={isFetching}>Очистить</button>
            </div>
    </form>
    );
};

export default AddServerForm;