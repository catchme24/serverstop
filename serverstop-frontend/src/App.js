import React from 'react';
import Banner from "./components/banner/banner";
import Footer from "./components/footer/footer";
import PageLoader from "./components/pageLoader/pageLoader";
import HeaderContainer from "./components/header/headerContainer";

const App = () => {

    return (
        <>
            <div className="forAll">
                <HeaderContainer />
                <Banner />

                <div className="bottom">
                    <PageLoader />
                    <Footer />
                </div>
            </div>
        </>
    )
};


export default App;
