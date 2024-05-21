import React, { Component } from 'react';
import axios from 'axios';
import '../style/UpdateNewPlant.css';
import PlantPresenter from "../presenter/PlantPresenter";

class UpdateNewPlant extends Component {
    constructor(props) {
        super(props);
        this.state = {
            plant: {
                plantName: '',
                species: '',
                plantType: '',
                carnivorous: '',
                endangered: '',
                location: '',
                img: '', // Assuming imageData is a base64-encoded string for simplicity
            },
            lastLang: null,
            dict: []
        };

        this.presenter = new PlantPresenter(this);
    }

    componentDidMount() {
        this.presenter.fetchUpd();
    }

    render() {
        return this.presenter.renderUpd();
    }
}

export default UpdateNewPlant;
