import React from 'react';
import Banner from "./components/banner/banner";
import Footer from "./components/footer/footer";
import PageLoader from "./components/pageLoader/pageLoader";
import HeaderContainer from "./components/header/headerContainer";
import styled from 'styled-components';
import {Outer} from "./forTest/Outer";
import {Inner} from "./forTest/Inner";

const App = () => {

    return (
        <>
            <div className="forAll">
                <Outer >
                    <Inner />
                </Outer>
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
