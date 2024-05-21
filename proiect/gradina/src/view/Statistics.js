import React, { Component } from 'react';
import Chart from 'chart.js/auto';
import axios from "axios";
import PlantPresenter from "../presenter/PlantPresenter";

class StatisticsPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            chartData: null,
            lastLang: null,
            dict: []
        };


        this.pieChartRef = React.createRef();
        this.barChart1Ref = React.createRef();
        this.barChart2Ref = React.createRef();
        this.presenter = new PlantPresenter(this);
    }

    componentDidMount() {
        this.presenter.fetchDataStats();
    }

    destroyChart = (chartRef) => {
        if (chartRef.current && chartRef.current.chart) {
            chartRef.current.chart.destroy();
        }
    };

    componentDidUpdate(prevProps, prevState) {
        if (prevState.chartData !== this.state.chartData && this.state.chartData) {
            this.destroyChart(this.pieChartRef);
            this.destroyChart(this.barChart1Ref);
            this.destroyChart(this.barChart2Ref);

            this.renderChart(this.pieChartRef, this.state.chartData.pieChartData, 'pie');
            this.renderChart(this.barChart1Ref, this.state.chartData.barChartData1, 'bar');
            this.renderChart(this.barChart2Ref, this.state.chartData.barChartData2, 'bar');
        }
    }

    componentWillUnmount() {
        this.destroyChart(this.pieChartRef);
        this.destroyChart(this.barChart1Ref);
        this.destroyChart(this.barChart2Ref);
    }

    renderChart = (chartRef, data, type) => {
        if (chartRef.current) {
            chartRef.current.chart = new Chart(chartRef.current, {
                type: type,
                data: data,
                options: {
                    responsive: true,
                    maintainAspectRatio: false
                }
            });
        }
    };

   render() {
       return this.presenter.renderStats();
   }
}

export default StatisticsPage;
