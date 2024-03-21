import React from 'react';
import EditServerForm from "./editServerForm";

class EditServerFormContainer extends React.Component {

    componentDidMount() {

    }

    componentWillUnmount() {

    }

    render() {

        return (
            <div>
                <EditServerForm
                    server={this.props.server}
                    isSaving={this.props.isSaving}
                    isEditing={this.props.isEditing}

                    addEditingServer={this.props.addEditingServer}
                    removeEditingServer={this.props.removeEditingServer}
                    addFetchingToUpdateServer={this.props.addFetchingToUpdateServer}
                    removeFetchingToUpdateServer={this.props.removeFetchingToUpdateServer}
                />
            </div>
        );
    }
}

export default EditServerFormContainer;