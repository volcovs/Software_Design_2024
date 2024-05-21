import React, { Component } from 'react';
import axios from 'axios';
import "../style/LogInPage.css"
import LogInPresenter from "../presenter/LogInPresenter";
import { useCookies } from 'react-cookie';

class Login extends Component {
    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: '',
            usernameError: '',
            passwordError: '',
            error: true,
            dict: [],
            lastLang: null
        };

        this.presenter = new LogInPresenter(this);
    }

    componentDidMount() {
        this.presenter.fetchLanguageInfo();
    }

    render() {
        return this.presenter.render();
    }
}

export default Login;
