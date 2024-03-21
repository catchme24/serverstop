import React from 'react';
import Language from "./language";
import {currentLanguageSelector, isNightModeSelector, languagesSelector} from "../../redux/language/languageSelector";
import {connect} from "react-redux";
import {changeLanguage, toggleNightMode} from "../../redux/language/languageReducer";

class LangugeContainer extends React.PureComponent {

    render() {
        return <Language {...this.props}/>;
    }
}

let mapStateToProps = (state) => {
    return {
        languages: languagesSelector(state),
        isNightMode: isNightModeSelector(state),
        currentLanguage: currentLanguageSelector(state)
    };
};

export default connect(mapStateToProps,
                        {
                                            changeLanguage,
                                            toggleNightMode
                                        }
)(LangugeContainer);
