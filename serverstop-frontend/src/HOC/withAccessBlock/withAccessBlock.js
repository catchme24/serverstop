import React from 'react';
import {isAuthenticatedSelector} from "../../redux/authentication/authSelector";
import {useSelector} from "react-redux";
import {Navigate} from "react-router-dom";

export const withAccessBlock = (Component) => {

    const AccessBlock = (props) => {
        const isAuthenticated = useSelector(isAuthenticatedSelector);
        if (isAuthenticated) {
            return <Navigate to={'/home'}/>;
        }
        return <Component {...props}/>;
    }
    return AccessBlock;
}


