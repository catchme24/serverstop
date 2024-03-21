import React from 'react';
import {connect} from "react-redux";
import ProfilePage from "./profilePage";
import {fetchDeleteServerTRUE, fetchUserServers} from "../../redux/profilePage/profilePageReducer";
import {userServersSelector} from "../../redux/profilePage/profilePageSelector";
import {withAuthVerification} from "../../HOC/withAuthVerification/withAuthVerification";

class ProfilePageContainer extends React.Component {

    componentDidMount() {
        if (this.props.servers === null) {
            this.props.fetchUserServers();
        }
    }

    render() {
        return (
                <ProfilePage
                    servers={this.props.servers}

                    fetchDeleteServerTRUE={this.props.fetchDeleteServerTRUE}
                />
        );
    }
}

let mapStateToProps = (state) => {
    return {
        servers: userServersSelector(state)
    }
}

let withAuthBlock = withAuthVerification(ProfilePageContainer);

export default connect(mapStateToProps,
                        {
                                            fetchUserServers
                                        }
)(withAuthBlock);