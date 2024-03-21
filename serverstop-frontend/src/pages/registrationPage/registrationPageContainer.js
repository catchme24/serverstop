import React from 'react';
import {connect} from "react-redux";
import {fetchRegistrUser} from "../../redux/authentication/authReducer";
import RegistrationPage from "./registrationPage";
import {withAccessBlock} from "../../HOC/withAccessBlock/withAccessBlock";

class RegistrationPageContainer extends React.Component {

    render() {
        return (
            <RegistrationPage
                registrationMessOrError={this.props.registrationMessOrError}
                registratingFetchStatus={this.props.registratingFetchStatus}

                fetchRegistrUser={this.props.fetchRegistrUser}
            />
        );
    }
}

let mapStateToProps = (state) => {
    return {
        registratingFetchStatus: state.authentication.registratingFetchStatus,
        registrationMessOrError: state.authentication.registrationMessOrError
    }
}

let registrationPageBlocked = withAccessBlock(RegistrationPageContainer);

export default connect(mapStateToProps,
                        {
                                            fetchRegistrUser
                                        }
)(registrationPageBlocked);