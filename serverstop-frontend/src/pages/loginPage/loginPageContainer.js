import React from 'react';
import {connect} from "react-redux";
import LoginPage from "./loginPage";
import {fetchLoginUser} from "../../redux/authentication/authReducer";
import {authErrorSelector, authFetchStatusSelector} from "../../redux/authentication/authSelector";
import {withAccessBlock} from "../../HOC/withAccessBlock/withAccessBlock";

class LoginPageContainer extends React.Component {

    render() {

        return (
            <LoginPage
                authentictingFetchStatus={this.props.authentictingFetchStatus}
                authenticationError={this.props.authenticationError}

                fetchLoginUser={this.props.fetchLoginUser}
            />
        );
    }
}

let mapStateToProps = (state) => {

    return {
        authentictingFetchStatus: authFetchStatusSelector(state),
        authenticationError: authErrorSelector(state)
    }
}

let loginPageBlocked = withAccessBlock(LoginPageContainer);

export default connect(mapStateToProps,
                        {
                                            fetchLoginUser
                                        }
)(loginPageBlocked);