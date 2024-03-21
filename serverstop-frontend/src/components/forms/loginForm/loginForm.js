import React, {useEffect} from 'react';
import {Controller, useForm} from 'react-hook-form';
import {Button, TextField} from "@mui/material";
import style from './loginForm.module.css';
import {useNavigate} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {cacheLoginForm} from "../../../redux/loginPage/loginPageReducer";
import {loginFormSelector} from "../../../redux/loginPage/loginPageSelector";

const LoginForm = (props) => {
    const loginForm = useSelector(loginFormSelector);

    const methods = useForm({
        defaultValues: loginForm,
        mode: "onChange"
    });
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const { control, handleSubmit, getValues } = methods;

    useEffect(() => {
       return (() => {
           console.log("ЯКОБЫ ЗАКЕШИРОВАЛСЯ");
           dispatch(cacheLoginForm(getValues()));
       });
    }, [getValues]);

    const login = (data) => {
        console.log('В компоненте: ' + JSON.stringify(data));
        props.fetchLoginUser(data);
    }
    const navigateToRegistrationPage = () => {
        navigate('/registration', {replace: true})
    }

    return (
        <form onSubmit={handleSubmit(login)} className={style.container}>
            <div className={style.title}>Вход на сайт</div>
            <div className={style.error}>{ props.authenticationError }</div>

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
                <div className={props.authentictingFetchStatus === 'pending' ? style.buttonLoginLoading : style.formInput}>
                    <Button
                        type="submit"
                        fullWidth={true}
                        disabled={props.authentictingFetchStatus === 'pending' ? true : false}
                    >Войти</Button>
                </div>
                <div className={style.formInput}>
                    <Button fullWidth={true} onClick={navigateToRegistrationPage}>Регистрация</Button>
                </div>
            </div>
        </form>
    );
};

export default LoginForm;