import React, { Component } from 'react';
import axios from 'axios';
import '../style/PlantPage.css'; // Import CSS file
import Select from 'react-dropdown-select';
import PlantPresenter from "../presenter/PlantPresenter";

class PlantPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            plants: [],
            dict: [],
            selectedRow: null,
            textToSearch: '',
            lastLang: null
        };

        this.presenter = new PlantPresenter(this);
    }

    componentDidMount() {
       this.presenter.fetchData();
    }

   render() {
        return this.presenter.render();
   }
}

export default PlantPage;
