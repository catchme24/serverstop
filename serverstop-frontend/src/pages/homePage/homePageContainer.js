import React from 'react';
import {connect} from "react-redux";
import HomePage from "./homePage";
import {
    changeFiltersByTag,
    changeSelectedChronicle,
    changeSelectedRate,
    filterServers,
    setChronicleTags,
    setRateTags,
    setServers
} from "../../redux/homePage/homePageReducer";
import serverService from "../../services/serversService";
import {
    getChronicleTags,
    getFilteredServers,
    getFilterSelectedChronicle,
    getFilterSelectedRate,
    getGroupedByDate2,
    getRateTags,
    getServers,
    getTitles
} from "../../redux/homePage/homePageSelector";

class HomePageContainer extends React.Component {

    componentDidMount() {
        if (this.props.servers.length === 0) {
            serverService.getAll().then(data => {
                data = data.map(server => {
                    return {
                        id: server.id,
                        url: server.domain,
                        rate: server.serverRate,
                        chronicle: server.chronicle,
                        date: server.serverStart,
                        status: server.status
                    };
                });
                this.props.setServers(data);
            });
        }
        if (this.props.rateTags == null) {
            serverService.getAllRates().then(data => {
                this.props.setRateTags(data);
            });
        }
        if (this.props.chronicleTags == null) {
            serverService.getAllChronicles().then(data => {
                this.props.setChronicleTags(data);
            });
        }
    }

    render() {

        return (
            <div>
                <HomePage
                    serversWillOpen={this.props.groupedServer == null ? null : this.props.groupedServer.slice(4, 9)}
                    serversAlreadyOpened={this.props.groupedServer == null ? null : this.props.groupedServer.slice(0,4)}

                    chronicleTags={this.props.chronicleTags}
                    rateTags={this.props.rateTags}
                    chronicleTags={this.props.chronicleTags}
                    filterSelectedRate={this.props.filterSelectedRate}
                    filterSelectedChronicle={this.props.filterSelectedChronicle}

                    filterServers={this.props.filterServers}
                    changeSelectedRate={this.props.changeSelectedRate}
                    changeSelectedChronicle={this.props.changeSelectedChronicle}
                    changeFiltersByTag={this.props.changeFiltersByTag}
                />
            </div>
        );
    }
}

let mapStateToProps = (state) => {
    return {
        servers: getServers(state),
        groupedServer: getGroupedByDate2(state),
        titles: getTitles(state),
        rateTags: getRateTags(state),
        chronicleTags: getChronicleTags(state),
        filterSelectedChronicle: getFilterSelectedChronicle(state),
        filterSelectedRate: getFilterSelectedRate(state),
        filteredServers: getFilteredServers(state)
    }
}

export default connect(mapStateToProps,
                        {
                                            setRateTags,
                                            setChronicleTags,
                                            setServers,
                                            filterServers,
                                            changeSelectedRate,
                                            changeSelectedChronicle,
                                            changeFiltersByTag
                                        }
)(HomePageContainer);