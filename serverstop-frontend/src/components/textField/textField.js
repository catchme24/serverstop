import React, {useState} from 'react';
import style from "../textField/textField.module.css";

const TextField = (props) => {

    const { error, value, onChange, disabled, placeholder, type } = props;

    const [isFocused, toggleIsFocused] = useState(false);

    const customStyle = {
        fontSize: '16px',
        linHeight: '1.5em'
    };

    const makeFocused = () => {
        toggleIsFocused(true);
    }

    const makeUnfocused = () => {
        toggleIsFocused(false);
    }

    const isEmpty = () => {
        return value ? style.inputContainerNotEmpty : style.inputContainer;
    }
    const haveError = () => {
        return error ? style.inputContainerError : isFocused ? style.inputContainerOkFocused : style.inputContainerOkUnfocused;
    }

    return(
        <div style={customStyle}>
            <div className={style.textFieldContainer}>
                <div onFocus={makeFocused} onBlur={makeUnfocused} className={[isEmpty(), haveError()].join(' ')} placeholder={placeholder}>
                    <input className={style.input} type={type} value={value} onChange={onChange} disabled={disabled}/>
                </div>
                {error && <div className={style.error}>{error.message}</div>}
            </div>
        </div>
    )
}

export default TextField;