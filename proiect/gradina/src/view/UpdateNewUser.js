import React, { Component } from 'react';
import axios from 'axios';
import '../style/UpdateNewUser.css';
import UserPresenter from "../presenter/UserPresenter";

class UpdateNewUser extends Component {
    constructor(props) {
        super(props);
        this.state = {
            user: {
                username: '',
                person: '',
                password: '',
                adminStatus: ''
            },
            employee: {
                firstName: '',
                lastName: '',
                dateOfBirth: '',
                address: '',
                phoneNumber: '',
                email: ''
            },
            lastLang: null,
            dict: []
        };

        this.presenter = new UserPresenter(this);
    }

    componentDidMount() {
        this.presenter.fetchUpdNew();
    }

    render() {
        return this.presenter.renderUpdNew();
    }
}

export default UpdateNewUser;
