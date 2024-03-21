import React from 'react';
import {connect} from "react-redux";
import Header from "./header";
import {deleteAuth} from "../../redux/authentication/authReducer";
import {authUsernameSelector, isAuthenticatedSelector} from "../../redux/authentication/authSelector";

class HeaderContainer extends React.PureComponent {

    render() {

        return (
            <Header
                isAuthenticated={this.props.isAuthenticated}
                username={this.props.username}

                deleteAuthentication={this.props.deleteAuth}
            />
        );
    }
}

let mapStateToProps = (state) => {
    return {
            isAuthenticated: isAuthenticatedSelector(state),
            username: authUsernameSelector(state)
    }
}

export default connect(mapStateToProps,
                        {
                                            deleteAuth
                                        }
)(HeaderContainer);