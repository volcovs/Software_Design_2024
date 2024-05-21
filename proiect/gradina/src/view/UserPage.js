import React, { Component } from 'react';
import axios from 'axios';
import '../style/UserPage.css'; // Import CSS file
import UserPresenter from "../presenter/UserPresenter";

class UserPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            users: [],
            employees: [],
            selectedRow: null,
            lastLang: null,
            dict: []
        };

        this.presenter = new UserPresenter(this);
    }

    componentDidMount() {
        this.presenter.fetchData();
    }

    componentWillUnmount() {
        // Cancel any pending axios requests when the component unmounts
        this.cancelRequests();
    }

    cancelRequests() {
    }


    render() {
        return this.presenter.render();
    }
}

export default UserPage;
