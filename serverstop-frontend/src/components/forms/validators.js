import moment from "moment";
import {dateFormatTarget} from "../../redux/store";

export const validateDate = (checkingDate) => {
    let date = moment(checkingDate, dateFormatTarget);
    return date.isValid() ? true : 'Форматы даты: ДД.ММ.ГГГГ';
}