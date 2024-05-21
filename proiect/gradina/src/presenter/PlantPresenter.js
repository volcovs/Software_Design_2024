import axios from "axios";
import Select from "react-dropdown-select";
import React from "react";

class PlantPresenter {
    constructor(page) {
        this.page = page;
    }

    fetchData() {
        const { lang } = this.page.props;
        // Fetch plant data from Java Spring backend
        axios.get('http://localhost:8080/plant/list')
            .then(response => {
                this.page.setState({ plants: response.data });
            })
            .catch(error => {
                console.error('Error fetching plant data:', error);
            });

        axios.get(`http://localhost:8080/lang/get?lang=${lang}`)
            .then(response => {
                this.page.setState({ dict: response.data, lastLang: lang});
                console.log(response.data);
            })
            .catch(error => {
                console.error('Error fetching language info:', error);
            });
    }

    handleRowClick = (plantId) => {
        this.page.setState({ selectedRow: plantId });
    };

    handleDelete = (id) => {
        axios.delete(`http://localhost:8080/plant/deleteById?id=${id}`)
            .then(response => {
                console.log(response.data);
                this.page.setState(prevState => ({
                    plants: prevState.plants.filter(plant => plant.id !== id)
                }));
            })
            .catch(error => {
                console.error('Error deleting data:', error);
            });
    };

    handleChange = (e) => {
        const { value } = e.target;
        this.page.setState({ textToSearch: value });
    };

    handleEdit = (plantId) => {
        const { role, onChangePage, lang } = this.page.props;
        onChangePage(role, 4, plantId, 0, lang);
    };

    handleSearch() {
        axios.get(`http://localhost:8080/plant/findByName?name=${this.page.state.textToSearch}`)
            .then(response => {
                console.log(response.data);
                axios.get('http://localhost:8080/plant/list')
                    .then(responseFull => {
                        this.page.setState({ plants: responseFull.data.filter(plant => plant.id === response.data.id) });
                    })
                    .catch(error => {
                        console.error('Error fetching plant data:', error);
                    });
            })
            .catch(error => {
                console.error('Error deleting data:', error);
            });
    }

    showAll() {
        axios.get('http://localhost:8080/plant/list')
            .then(response => {
                this.page.setState({ plants: response.data });
            })
            .catch(error => {
                console.error('Error fetching plant data:', error);
            });
    }

    onChangeSort(values) {
        console.log(values);
        if (values === null || values.length === 0) {
            this.showAll();
        }
        else {
            axios.get(`http://localhost:8080/plant/sort?sortBy=${values[0].value}`)
                .then(response => {
                    this.page.setState({plants: response.data});
                })
                .catch(error => {
                    console.error('Error fetching plant data:', error);
                });
        }
    }

    onChangeFilter(values) {
        console.log(values);
        if (values === null || values.length === 0) {
            this.showAll();
        }
        else {
            axios.get(`http://localhost:8080/plant/filter?criterion=${values[0].value}&value=${this.page.state.textToSearch}`)
                .then(response => {
                    this.page.setState({plants: response.data});
                })
                .catch(error => {
                    console.error('Error fetching plant data:', error);
                });
        }
    }

    onChangeSave(values) {
        console.log(values);
        if (values === null || values.length === 0) {
            this.showAll();
        }
        else {
            axios.get(`http://localhost:8080/plant/save?format=${values[0].value}`)
                .then(response => {
                    console.log(response.data);
                })
                .catch(error => {
                    console.error('Error fetching plant data:', error);
                });

            window.open(`http://localhost:8080/plant/save?format=${values[0].value}`, '_blank');
        }
    }

    handleStats() {
        const { role, onChangePage, lang } = this.page.props;
        onChangePage(role, 6, 0, 0, lang);
    }

    render() {
        if (this.page.props.lang !== this.page.state.lastLang) {
            axios.get(`http://localhost:8080/lang/get?lang=${this.page.props.lang}`)
                .then(response => {
                    this.page.setState({dict: response.data, lastLang: this.page.props.lang });
                    console.log(response.data);
                })
                .catch(error => {
                    console.error('Error fetching language info:', error);
                });
        }

        const sortOptions = [
            {
                value: 'Tip',
                label: this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Type"] : "Type"
            },
            {
                value: 'Specie',
                label: this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Species"] : "Species"
            }
        ];

        const filterOptions = [
            {
                value: 'Tip',
                label: this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Type"] : "Type"
            },
            {
                value: 'Specie',
                label: this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Species"] : "Species"
            },
            {
                value: 'Carnivore',
                label: this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Carnivorous"] : "Carnivorous"
            },
            {
                value: 'In pericol de disparitie',
                label: this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Endangered"] : "Endangered"
            },
            {
                value: 'Locatie',
                label: this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Location"] : "Location"
            }
        ];

        const saveOptions = [
            {
                value: 'csv',
                label: 'csv'
            },
            {
                value: 'json',
                label: 'json'
            },
            {
                value: 'xml',
                label: 'xml'
            },
            {
                value: 'docx',
                label: 'doc'
            }
        ];

        return (
            <div className="PlantPage">
                <h1>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Plant page"] : "Plants"}</h1>
                <div>
                    <div className="combobox" style={{ width: 300, display: "flex" }}>
                        <label style={{margin: 9}}>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Sort"] : "Sort by: "}: </label>
                        <Select
                            multi
                            options={sortOptions}
                            onChange={(values) => this.onChangeSort(values)}
                        />
                    </div>
                    <div className="combobox" style={{ width: 300, display: "flex"}}>
                        <label style={{margin: 6}}>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Filter"] : "Filter by: "}: </label>
                        <Select
                            multi
                            options={filterOptions}
                            onChange={(values) => this.onChangeFilter(values)}
                        />
                    </div>
                    {this.page.props.role === 'employee' &&
                        <div className="combobox" style={{ width: 300, display: "flex"}}>
                            <label style={{margin: 6}}>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Format"] : "Save as: "}: </label>
                            <Select
                                multi
                                options={saveOptions}
                                onChange={(values) => this.onChangeSave(values)}
                            />
                        </div>}
                </div>
                <div>
                    <input type="text" value={this.page.state.textToSearch} style={{ width: 500 }} onChange={this.handleChange}/>
                    <button className="statistics" onClick={() => this.handleSearch()}>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Search"] : "Search"}</button>
                    <button className="statistics" onClick={() => this.showAll()}>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Show"] : "Show all"}</button>
                </div>
                <table>
                    <thead>
                    <tr>
                        <th>No.</th>
                        <th>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Plant name"] : "Plant name"}</th>
                        <th>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Species"] : "Species"}</th>
                        <th>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Type"] : "Type"}</th>
                        <th>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Plant alim"] : "Carnivorous"}</th>
                        <th>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Endangered"] : "Endangered"}</th>
                        <th>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Location"] : "Location"}</th>
                        <th>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Image"] : "Image"}</th>
                        {this.page.props.role === 'employee' && <th>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Actions"] : "Actions"}</th>}
                    </tr>
                    </thead>
                    <tbody>
                    {this.page.state.plants.map(plant => (
                        <tr key={plant.id}
                            onClick={() => this.handleRowClick(plant.id)}
                            className={this.page.state.selectedRow === plant.id ? 'selected' : ''}>
                            <td>{plant.id}</td>
                            <td>{plant.plantName}</td>
                            <td>{plant.species}</td>
                            <td>{plant.plantType}</td>
                            <td>{plant.carnivorous}</td>
                            <td>{plant.endangered}</td>
                            <td>{plant.location}</td>
                            <td><img src={`data:image/png;base64,${plant.img}`} alt="Plant" style={{ width: '200px', height: '200px' }} /></td>
                            {this.page.props.role === 'employee' &&
                                <td>
                                    <button onClick={() => this.handleDelete(plant.id)}>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Delete"] : "Type"}</button>
                                    <button onClick={() => this.handleEdit(plant.id)}>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Edit"] : "Type"}</button>
                                </td>}
                        </tr>
                    ))}
                    </tbody>
                </table>

                {this.page.props.role === 'employee' && <button className="statistics" onClick={() => this.handleStats()}>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Statistics"] : "View statistics"}</button>}
            </div>
        );
    }

    onButtonClickCancel = () => {
        const { role, onChangePage, lang } = this.page.props;
        onChangePage(role, 1, 0, 0, lang);
    };

    handleChangeUpd = (e) => {
        const { name, value } = e.target;
        this.page.setState(prevState => ({
            plant: {
                ...prevState.plant,
                [name]: value
            }
        }));
    };

    handleSubmit = (e) => {
        e.preventDefault();
        const { role, onChangePage, lang, plant } = this.page.props;
        const { plantId } = this.page.props;

        if (plantId) {
            // Update existing plant data
            axios.put(`http://localhost:8080/plant/update`, plant)
                .then(response => {
                    console.log('Plant updated successfully:', response.data);
                    // Optionally redirect to another page or display a success message
                })
                .catch(error => {
                    console.error('Error updating plant data:', error);
                });
        } else {
            // Add new plant data
            axios.post('http://localhost:8080/plant/add', plant)
                .then(response => {
                    console.log('New plant added successfully:', response.data);
                    // Optionally redirect to another page or display a success message
                })
                .catch(error => {
                    console.error('Error adding new plant:', error);
                });
        }

        onChangePage(role, 1, 0, 0, lang);
    };

    async getBase64(file) {
        return new Promise((resolve, reject) => {
            const reader = new FileReader()
            reader.readAsDataURL(file)
            reader.onload = () => {
                resolve(reader.result)
            }
            reader.onerror = reject
        })
    }

    updatePhoto = async (e) => {
        const file = e.target.files[0];
        if (file) {
            try {
                let formData = new FormData();

                //update or add image
                await this.getBase64(file) // `file` your img file
                    .then(res => {
                        this.page.setState(prevState => ({
                            plant: {
                                ...prevState.plant,
                                img: res.split(',')[1]
                            }
                        }));
                        formData.append('id', this.page.props.plantId); // Assuming plantId is accessible here
                        formData.append('file', res.split(',')[1]);

                        axios.post(`http://localhost:8080/plant/photo`, formData)
                            .then(response => {
                                console.log('New photo added successfully:', response.data);
                                // Optionally redirect to another page or display a success message
                            })
                            .catch(error => {
                                console.error('Error adding photo:', error);
                            });
                        console.log(res);
                    })
                    .catch(err => console.log(err))
            } catch (error) {
                console.log(error);
            }
        }
    };

    fetchUpd() {
        const { plantId } = this.page.props;
        if (plantId) {
            // Fetch plant data for editing if plantId is provided
            axios.get(`http://localhost:8080/plant/findById?id=${plantId}`)
                .then(response => {
                    this.page.setState({ plant: response.data });
                })
                .catch(error => {
                    console.error('Error fetching plant data:', error);
                });
        }
    }

    renderUpd() {
        const { plant } = this.page.state;
        const { plantId } = this.page.props;
        if (this.page.props.lang !== this.page.state.lastLang) {
            axios.get(`http://localhost:8080/lang/get?lang=${this.page.props.lang}`)
                .then(response => {
                    this.page.setState({dict: response.data, lastLang: this.page.props.lang });
                    console.log(response.data);
                })
                .catch(error => {
                    console.error('Error fetching language info:', error);
                });
        }

        return (
            <div>
                <h2>{plantId ? (this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Edit"] : "Edit plant") : (this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Add plant"] : "Add plant")}</h2>
                <form onSubmit={this.handleSubmit}>
                    <label>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Plant name"] : "Plant name"}:</label>
                    <input type="text" name="name" value={plant.plantName} onChange={this.handleChangeUpd} />
                    <label>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Plant species"] : "Species"}:</label>
                    <input type="text" name="species" value={plant.species} onChange={this.handleChangeUpd} />
                    <label>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Plant type"] : "Plant type"}:</label>
                    <input type="text" name="type" value={plant.plantType} onChange={this.handleChangeUpd} />
                    <label>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Plant alim"] : "Carnivorous"}:</label>
                    <input type="text" name="carnivorous" value={plant.carnivorous} onChange={this.handleChangeUpd} />
                    <label>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Plant end"] : "Endangered"}:</label>
                    <input type="text" name="endangered" value={plant.endangered} onChange={this.handleChangeUpd} />
                    <label>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Plant location"] : "Plant location"}:</label>
                    <input type="text" name="location" value={plant.location} onChange={this.handleChangeUpd} />
                    <label>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Image"] : "Image"}:</label>
                    <div align={"center"}>
                        <img src={`data:image/png;base64,${plant.img}`} alt="Plant" style={{ width: '200px', height: '200px', alignContent: "center"}} />
                    </div>
                    <div align={"center"}>
                        <input type='file' onChange={this.updatePhoto} name='file' accept='image/*' />
                    </div>
                    <div>
                        <button onClick={this.onButtonClickCancel}>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Cancel"] : "Cancel"}</button>
                        <button type="submit">{plantId ? (this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Update"] : "Update plant") : (this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Add plant"] : "Add plant")}</button>
                    </div>
                </form>
            </div>
        );
    }

    fetchDataStats = async () => {
        try {
            const pieResponse = await fetch('http://localhost:8080/plant/pie');
            const bar1Response = await fetch('http://localhost:8080/plant/bar1');
            const bar2Response = await fetch('http://localhost:8080/plant/bar2');

            if (!pieResponse.ok || !bar1Response.ok || !bar2Response.ok) {
                throw new Error('Failed to fetch data');
            }

            const pieData = await pieResponse.json();
            const bar1Data = await bar1Response.json();
            const bar2Data = await bar2Response.json();

            const pieChartData = {
                labels: pieData.filter((_, index) => index % 2 !== 0),
                datasets: [{
                    data: pieData.filter((_, index) => index % 2 === 0),
                    backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56'],
                    hoverBackgroundColor: ['#FF6384', '#36A2EB', '#FFCE56']
                }]
            };

            const barChartData1 = {
                labels: ['No', 'Yes'],
                datasets: [{
                    label: 'Plants by alimentation:',
                    data: bar1Data,
                    backgroundColor: 'rgba(255, 99, 132, 0.2)',
                    borderColor: 'rgba(255, 99, 132, 1)',
                    borderWidth: 1
                }]
            };

            const barChartData2 = {
                labels: bar2Data.filter((_, index) => index % 2 !== 0),
                datasets: [{
                    label: 'Plants by location:',
                    data: bar2Data.filter((_, index) => index % 2 === 0),
                    backgroundColor: 'rgba(54, 162, 235, 0.2)',
                    borderColor: 'rgba(54, 162, 235, 1)',
                    borderWidth: 1
                }]
            };

            this.page.setState({ chartData: { pieChartData, barChartData1, barChartData2 } });
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    };

    renderStats() {
        if (this.page.props.lang !== this.page.state.lastLang) {
            axios.get(`http://localhost:8080/lang/get?lang=${this.page.props.lang}`)
                .then(response => {
                    this.page.setState({dict: response.data, lastLang: this.page.props.lang });
                    console.log(response.data);
                })
                .catch(error => {
                    console.error('Error fetching language info:', error);
                });
        }

        return (
            <div>
                <h1>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Statistics"] : "Statistics"}</h1>
                <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                    <h2>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Stat1"] : "Plants by type"}:</h2>
                    <div style={{ width: '50%' }}>
                        <canvas id="pieChart" ref={this.page.pieChartRef}></canvas>
                    </div>
                    <div>
                        <h1></h1>
                    </div>
                    <h2>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Stat2"] : "Plants by alimentation"}:</h2>
                    <div style={{ width: '50%' }}>
                        <canvas id="barChart1" ref={this.page.barChart1Ref}></canvas>
                    </div>
                    <div>
                        <h1></h1>
                    </div>
                    <h2>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Stat3"] : "Plants by location"}:</h2>
                    <div style={{ width: '50%' }}>
                        <canvas id="barChart2" ref={this.page.barChart2Ref}></canvas>
                    </div>
                </div>
            </div>
        );
    }

}


export default PlantPresenter;