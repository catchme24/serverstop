export default class User {

    #id;
    #username;
    #password;
    #roles;


    constructor(id, username, password, roles) {
        this.#id = id;
        this.#username = username;
        this.#password = password;
        this.#roles = roles;
    }

    get id() {
        return this.#id;
    }

    set id(value) {
        this.#id = value;
    }

    get username() {
        return this.#username;
    }

    set username(value) {
        this.#username = value;
    }

    get password() {
        return this.#password;
    }

    set password(value) {
        this.#password = value;
    }

    get roles() {
        return this.#roles;
    }

    set roles(value) {
        this.#roles = value;
    }
}