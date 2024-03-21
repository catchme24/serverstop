import React from 'react';
export const Outer = ({ children }) => {

    return (
        <div>
            Рисует внешний и снизу должен быть ребенок:
            {children}
        </div>
    );
}