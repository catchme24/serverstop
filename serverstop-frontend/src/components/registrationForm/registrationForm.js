import React, {useEffect} from 'react';
import style from './registrationForm.module.css';
import {useNavigate} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {Controller, useForm} from "react-hook-form";
import {Button, TextField} from "@mui/material";
import {registrationFormSelector} from "../../redux/registrationPage/registrationPageSelector";
import {cacheRegistrationForm} from "../../redux/registrationPage/registrationPageReducer";

const RegistrationForm = (props) => {
    const registrationForm = useSelector(registrationFormSelector);

    const methods = useForm({
        defaultValues: registrationForm,
        mode: "onChange"
    });
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const { control, handleSubmit, getValues } = methods;

    useEffect(() => {
        return (() => {
            dispatch(cacheRegistrationForm(getValues()));
        });
    }, [getValues]);

    const registr = (data) => {
        props.fetchRegistrUser(data);
    }


    return (
        <form onSubmit={handleSubmit(registr)} className={style.container}>
            <div className={style.title}>Регистрация</div>
            <div className={props.registratingFetchStatus === 'fulfilled' ? style.success : style.error}>{ props.registrationMessOrError }</div>

            <div className={style.inputsContainer}>
                <Controller
                    name={'username'}
                    control={control}
                    rules={{
                        required: {value: true, message: 'Обязательное поле'},
                        minLength: {value: 5, message: 'Длинна больше 5 символов'},
                        maxLength: {value: 15, message: 'Длинна меньше 15 символов'}}}
                    render={({ field: { value, onChange}, fieldState: { error }}) => (
                        <div className={style.formInput}>
                            <TextField
                                label={'username'} variant={'outlined'}
                                value={value}
                                onChange={onChange}
                                fullWidth={true}
                                error={error !== undefined}
                                helperText={error && error.message}
                            />
                        </div>
                    )}
                />
                <Controller
                    name={'password'}
                    control={control}
                    rules={{
                        required: {value: true, message: 'Обязательное поле'},
                        minLength: {value: 5, message: 'Длинна больше 5 символов'},
                        maxLength: {value: 15, message: 'Длинна меньше 15 символов'}}}
                    render={({ field: { value, onChange}, fieldState: { error }}) => (
                        <div className={style.formInput}>
                            <TextField
                                label={'password'} variant={'outlined'}
                                type={'password'}
                                value={value}
                                onChange={onChange}
                                fullWidth={true}
                                error={error !== undefined}
                                helperText={error && error.message}
                            />
                        </div>
                    )}
                />
                <div className={props.registratingFetchStatus === 'pending' ? style.buttonLoginLoading : style.formInput}>
                    <Button
                        type="submit"
                        fullWidth={true}
                        disabled={props.registratingFetchStatus === 'pending' ? true : false}
                    >Зарегистрироваться</Button>
                </div>
            </div>
        </form>
    );
}

export default RegistrationForm;