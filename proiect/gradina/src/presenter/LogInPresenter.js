import axios from "axios";
import React from "react";
import {useCookies} from "react-cookie";

class LogInPresenter {

    constructor(page) {
        this.page = page;
    }

    onButtonClickLogIn = () => {
        axios.get(`http://localhost:8080/users/login?username=${this.page.state.username}&password=${this.page.state.password}`)
            .then(response => {
                this.page.setState({ user: response.data });
                if (response.data) {
                    if (response.data.adminStatus === 'yes') {
                        this.page.props.onChangePage("administrator", 1, 0, 0, this.page.props.lang);
                    } else {
                        this.page.props.onChangePage("employee", 1, 0, 0, this.page.props.lang);
                    }
                } else {
                    this.page.setState({ error: false });
                }
            })
            .catch(error => {
                console.error('Error during login:', error);
            });
    }

    onButtonClickCancel = () => {
        this.page.props.onChangePage(this.page.props.role, 1, 0, 0, this.page.props.lang);
    }

    fetchLanguageInfo() {
        if (this.page.props.lang !== this.page.state.lastLang) {
            axios.get(`http://localhost:8080/lang/get?lang=${this.page.props.lang}`)
                .then(response => {
                    this.page.setState({ dict: response.data, lastLang: this.page.props.lang });
                    console.log(response.data);
                })
                .catch(error => {
                    console.error('Error fetching language info:', error);
                });
        }
    }

    render() {
        const { username, password, error } = this.page.state;
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
            <div className="mainContainer">
                <div>
                    <h1>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Log in page"] : "Login"}</h1>
                </div>
                <br />
                <div className="inputContainer">
                    <input
                        value={username}
                        placeholder={this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Username"] : "Enter username"}
                        onChange={(ev) => this.page.setState({ username: ev.target.value })}
                        className="inputBox"
                    />
                </div>
                <br />
                <div className="inputContainer">
                    <input
                        type="password"
                        value={password}
                        placeholder={this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Password"] : "Password"}
                        onChange= {(ev) => this.page.setState({ password: ev.target.value })}
                        className={'inputBox'}
                    />
                </div>
                <br />
                <div>
                    <button className="inputButton" onClick={this.onButtonClickCancel}>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Cancel"] : "Cancel"}</button>
                    <button className="inputButton" onClick={this.onButtonClickLogIn}>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Log in"] : "Log in"}</button>
                </div>

                <par hidden={!!error} style={{ color:'red', size: 24}}>{this.page.state.dict["lang"] ? this.page.state.dict["lang"]["Login error"] : "Error"}</par>
            </div>);
    }

}

export default LogInPresenter;