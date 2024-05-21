import axios from "axios";
import React from "react";

class UserPresenter {
    constructor(page) {
        this.page = page;
    }

    fetchData() {
            axios.all([
                axios.get('http://localhost:8080/users/list'),
                axios.get('http://localhost:8080/employee/list')
            ])
                .then(axios.spread((usersResponse, employeesResponse) => {
                    this.page.setState({
                        users: usersResponse.data,
                        employees: employeesResponse.data
                    });
                }))
                .catch(error => {
                    console.error('Error fetching data:', error);
                });
        }

    fetchUpdNew() {
        const { userId } = this.page.props;
        if (userId) {
            // Fetch user data for editing if userId is provided
            axios.get(`http://localhost:8080/users/findById?id=${userId}`)
                .then(response => {
                    this.page.setState({ user: response.data });
                })
                .catch(error => {
                    console.error('Error fetching user data:', error);
                });

            axios.get(`http://localhost:8080/employee/findByUser?id=${userId}`)
                .then(response => {
                    this.page.setState({ employee: response.data });
                })
                .catch(error => {
                    console.error('Error fetching personal data:', error);
                });
        }
    }

    showEmp = () => {
        axios.get('http://localhost:8080/users/filter?status=no')
            .then(response => {
                this.page.setState({
                    users: response.data
                });
                axios.get('http://localhost:8080/employee/filter?status=no')
                    .then(resp => {
                        this.page.setState( {
                            employees :resp.data
                        });
                    })
                    .catch(error => {
                        console.error('Error fetching employee data:', error);
                    });
            })
            .catch(error => {
                console.error('Error fetching user data:', error);
            });
    };

    showAdmins = () => {
        axios.get('http://localhost:8080/users/filter?status=yes')
            .then(response => {
                this.page.setState({
                    users: response.data
                });
                axios.get('http://localhost:8080/employee/filter?status=yes')
                    .then(resp => {
                        this.page.setState( {
                            employees :resp.data
                        });
                    })
                    .catch(error => {
                        console.error('Error fetching employee data:', error);
                    });
            })
            .catch(error => {
                console.error('Error fetching user data:', error);
            });
    };


    handleRowClick = (userId) => {
        this.page.setState({ selectedRow: userId });
    };

    handleDelete = (id) => {
        axios.delete(`http://localhost:8080/users/deleteById?id=${id}`)
            .then(response => {
                console.log(response.data);
                this.page.setState(prevState => ({
                    users: prevState.users.filter(user => user.userID !== id)
                }));
            })
            .catch(error => {
                console.error('Error deleting data:', error);
            });

        axios.delete(`http://localhost:8080/employee/deleteByUser?id=${id}`)
            .then(response => {
                console.log(response.data);
                this.page.setState(prevState => ({
                    employees: prevState.employees.filter(emp => emp.id !== id)
                }));
            })
            .catch(error => {
                console.error('Error deleting data:', error);
            });
    };

    handleEdit = (userId) => {
        const { role, onChangePage, lang } = this.page.props;
        onChangePage(role, 5, 0, userId, lang);
    };

    showAll = () => {
        this.fetchData();
    };

    onButtonClickCancel = () => {
        const { role, onChangePage, lang } = this.page.props;
        onChangePage(role, 3, 0, 0, lang);
    };

    handleChangeUser = (e) => {
        const { name, value } = e.target;
        this.page.setState(prevState => ({
            user: {
                ...prevState.user,
                [name]: value
            }
        }));
    };

    handleChangeEmployee = (e) => {
        const { name, value } = e.target;
        this.page.setState(prevState => ({
            employee: {
                ...prevState.employee,
                [name]: value
            }
        }));
    };

    handleSubmit = (e) => {
        e.preventDefault();
        const { role, onChangePage, lang } = this.page.props;
        const { user, employee } = this.page.state;
        const { userId } = this.page.props;

        if (userId) {
            // Update existing data
            axios.put(`http://localhost:8080/users/update`, user)
                .then(response => {
                    console.log('User updated successfully:', response.data);
                    // Optionally redirect to another page or display a success message
                })
                .catch(error => {
                    console.error('Error updating user data:', error);
                });
        } else {
            // Add new data
            axios.post('http://localhost:8080/users/add', user)
                .then(response => {
                    console.log('New user added successfully:', response.data);
                    // Optionally redirect to another page or display a success message
                })
                .catch(error => {
                    console.error('Error adding new user:', error);
                });

            axios.post('http://localhost:8080/employee/add', employee)
                .then(response => {
                    console.log('New personal data added successfully:', response.data);
                    // Optionally redirect to another page or display a success message
                })
                .catch(error => {
                    console.error('Error adding new data:', error);
                });
        }

        onChangePage(role, 3, 0, 0, lang);
    };
    
    renderUpdNew() {
        const { user, employee } = this.page.state;
        const { userId } = this.page.props;
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
                <h2>{userId ? (this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Edit"] : "Edit user"): (this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Add user"] : "Add user")}</h2>
                <form onSubmit={this.handleSubmit} >
                    <label>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["First name"] : "First name"}:</label>
                    <input type="text" name="firstName" value={employee.firstName} onChange={this.handleChangeEmployee} disabled={!!userId} />
                    <label>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Last name"] : "Last name"}:</label>
                    <input type="text" name="lastName" value={employee.lastName} onChange={this.handleChangeEmployee} disabled={!!userId} />
                    <label>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["DOB"] : "Date of birth"}:</label>
                    <input type="text" name="dateOfBirth" value={employee.dateOfBirth} onChange={this.handleChangeEmployee} disabled={!!userId} />
                    <label>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Address"] : "Address"}:</label>
                    <input type="text" name="address" value={employee.address} onChange={this.handleChangeEmployee} disabled={!!userId} />
                    <label>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Phone"] : "Phone number"}:</label>
                    <input type="text" name="phoneNumber" value={employee.phoneNumber} onChange={this.handleChangeEmployee} disabled={!!userId} />
                    <label>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Email"] : "Email"}:</label>
                    <input type="text" name="email" value={employee.email} onChange={this.handleChangeEmployee} disabled={!!userId} />
                    <label>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Username"] : "Username"}:</label>
                    <input type="text" name="username" value={user.username} onChange={this.handleChangeUser} />
                    <label>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Password"] : "Password"}:</label>
                    <input type="text" name="password" value={user.password} onChange={this.handleChangeUser} />
                    <label>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Status"] : "Status"}:</label>
                    <input type="text" name="adminStatus" value={user.adminStatus} onChange={this.handleChangeUser} />
                    <div>
                        <button onClick={this.onButtonClickCancel}>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Cancel"] : "Cancel"}</button>
                        <button type="submit">{userId ? (this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Update"] : "Update") : (this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Add user"] : "Add user")}</button>
                    </div>
                </form>
            </div>
        );
    }

    render() {
        const { users, employees, selectedRow } = this.page.state;
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
        <div className="UserPage">
            <h1>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["User page"] : "Users"}</h1>
            <div>
                <button className="filterB" onClick={this.showAll}>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Show"] : "Show all"}</button>
                <button className="filterB" onClick={this.showAdmins}>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Show admin"] : "Show only administrators"}</button>
                <button className="filterB" onClick={this.showEmp}>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Show emp"] : "Show only employees"}</button>
            </div>
            <table>
                <thead>
                <tr>
                    <th>No.</th>
                    <th>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["First name"] : "First name"}</th>
                    <th>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Last name"] : "Last name"}</th>
                    <th>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["DOB"] : "Date of birth"}</th>
                    <th>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Address"] : "Address"}</th>
                    <th>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Phone"] : "Phone number"}</th>
                    <th>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Email"] : "Email"}</th>
                    <th>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Username"] : "Username"}</th>
                    <th>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Password"] : "Password"}</th>
                    <th>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Status"] : "Admin status"}</th>
                    <th>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Actions"] : "Actions"}</th>
                </tr>
                </thead>
                <tbody>
                {users.map( (user, i) => (
                    <tr key={user.userID}
                        onClick={() => this.handleRowClick(user.userID)}
                        className={selectedRow === user.userID ? 'selected' : ''}>
                        <td>{user.userID}</td>
                        <td>{employees[i]?.firstName}</td>
                        <td>{employees[i]?.lastName}</td>
                        <td>{employees[i]?.dateOfBirth}</td>
                        <td>{employees[i]?.address}</td>
                        <td>{employees[i]?.phoneNumber}</td>
                        <td>{employees[i]?.email}</td>
                        <td>{user.username}</td>
                        <td>{user.password}</td>
                        <td>{user.adminStatus}</td>
                        <td>
                            <button onClick={() => this.handleDelete(user.userID)}>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Delete"] : "Delete"}</button>
                            <button onClick={() => this.handleEdit(user.userID)}>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Edit"] : "Edit"}</button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}
}

export default UserPresenter;