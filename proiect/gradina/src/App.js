import './App.css';
import React, { useState } from "react";
import Login from "./view/LogInPage";
import PlantPage from "./view/PlantPage";
import UserPage from "./view/UserPage";
import UpdateNewPlant from "./view/UpdateNewPlant";
import UpdateNewUser from "./view/UpdateNewUser";
import MenuBar from "./view/Menu";
import Statistics from "./view/Statistics";

class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            currentPage: 1,
            plantId: 0,
            userId: 0,
            role: 'visitor',
            lang: 'romana'
        };
    }

    onChangePage = (role, number, plant, user, lang) => {
        //const [cookies, setCookie] = useCookies(['role'])
        this.setState({ currentPage: number, role: role, plantId: plant, userId: user, lang: lang});
    }

    render() {
        return (
            <div className="App">
                <div>
                    <MenuBar role={this.state.role} onChangePage={this.onChangePage} lang={this.state.lang}/>
                </div>
                {this.state.currentPage === 1 && (
                    <div>
                        <PlantPage role={this.state.role} onChangePage={this.onChangePage} lang={this.state.lang}/>
                    </div>
                )}

                {this.state.currentPage === 2 && (
                    <div>
                        <Login role={this.state.role} onChangePage={this.onChangePage} lang={this.state.lang}/>
                    </div>
                )}

                {this.state.currentPage === 3 && (
                    <div>
                        <UserPage role={this.state.role} onChangePage={this.onChangePage} lang={this.state.lang}/>
                    </div>
                )}

                {this.state.currentPage === 4 && (
                    <div>
                        <UpdateNewPlant plantId={this.state.plantId} role={this.state.role} onChangePage={this.onChangePage} lang={this.state.lang}/>
                    </div>
                )}

                {this.state.currentPage === 5 && (
                    <div>
                        <UpdateNewUser userId={this.state.userId} role={this.state.role} onChangePage={this.onChangePage} lang={this.state.lang}/>
                    </div>
                )}

                {this.state.currentPage === 6 && (
                <div>
                    <Statistics role={this.state.role} lang={this.state.lang}/>
                </div>
                )}
            </div>
        );
    }
}

export default App;
