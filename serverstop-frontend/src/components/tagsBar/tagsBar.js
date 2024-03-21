import React from 'react';
import style from './tagsBar.module.css';

const TagsBar = (props) => {

    const setFilterState = (e) => {
        props.changeFiltersByTag(e.target.innerText);
    }

    return (
        <>
           <div className={style.tagsBar}>
               {props.tags != null && props.tags.map(tag =>
                   <div
                       key={tag}
                       className={style.searchButton} onClick={setFilterState}>
                       {tag}
                   </div>
               )}
           </div>
        </>
    )
}

export default TagsBar;