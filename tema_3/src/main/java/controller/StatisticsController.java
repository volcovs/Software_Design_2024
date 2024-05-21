package controller;

import com.intellij.uiDesigner.core.GridConstraints;
import model.*;
import model.repository.PlantRepository;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import view.Statistics;

import javax.swing.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatisticsController {
    private List<Statistics> statistics;
    private String role;
    private String lang;
    private Language language;

    public StatisticsController(String role, String lang) {
        this.statistics = new ArrayList<>();
        this.role = role;
        this.lang = lang;

        switch (lang) {
            case "romanian" -> { this.language = new Romanian();  }
            case "english" -> { this.language = new English();  }
            case "french" -> { this.language = new French();  }
            case "russian" -> { this.language = new Russian(); }
            default -> { System.out.println("Not a valid language choice");}
        }

        JFreeChart chart = createPieChart(createPieDataset());
        ChartPanel ch = new ChartPanel(chart);
        ch.setPreferredSize(new java.awt.Dimension(800, 600));
        this.statistics.add(new Statistics());
        this.statistics.get(0).getMainPanel().add(ch, new GridConstraints());

        JFreeChart chart2 = createBarChart(createBarDataset());
        ChartPanel ch2 = new ChartPanel(chart2);
        ch.setPreferredSize(new java.awt.Dimension(800, 600));
        this.statistics.add(new Statistics());
        this.statistics.get(1).getMainPanel().add(ch2, new GridConstraints());

        JFreeChart chart3 = createBarChart2(createBarDataset2());
        ChartPanel ch3 = new ChartPanel(chart3);
        ch.setPreferredSize(new java.awt.Dimension(800, 600));
        this.statistics.add(new Statistics());
        this.statistics.get(2).getMainPanel().add(ch3, new GridConstraints());
    }

    public List<Statistics> getViews() {

        HashMap<String, String> languageConfig = this.language.getLanguageConfig();

        for (Statistics v: this.statistics) {
            v.setTitle(languageConfig.get("Statistics"));
            v.setContentPane(v.getMainPanel());

            v.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            v.setSize(800, 600);
        }

        return this.statistics;
    }


    private static CategoryDataset createBarDataset2() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        PlantRepository plantRepository = new PlantRepository("gradina_botanica", null);
        List<Plant> plants = plantRepository.plantList();
        List<String> places = new ArrayList<>();

        for (Plant p: plants) {
            if (!places.contains(p.getLocation())) {
                places.add(p.getLocation());
            }
        }

        for (String t: places) {
            int num = 0;
            for (Plant p: plants) {
                if (p.getLocation().equals(t)) {
                    num++;
                }
            }

            dataset.addValue(num, t, t);
        }

        return dataset;
    }

    private JFreeChart createBarChart2(CategoryDataset dataset) {
        JFreeChart chart=ChartFactory.createBarChart(
                "Plant locations", //Chart Title
                "Location", // Category axis
                "Number of plants", // Value axis
                dataset,
                PlotOrientation.VERTICAL,
                true,true,false
        );

        return chart;
    }


    private static CategoryDataset createBarDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        PlantRepository plantRepository = new PlantRepository("gradina_botanica", null);
        List<Plant> plants = plantRepository.plantList();
        int num1 = 0;
        int num2 = 0;

        for (Plant p: plants) {
            if (p.getCarnivorous().equals("yes")) {
                num2++;
            }
            else {
                num1++;
            }
        }

        dataset.addValue(num1, "no", "no");
        dataset.addValue(num2, "yes", "yes");

        return dataset;
    }


    private JFreeChart createBarChart(CategoryDataset dataset) {
        JFreeChart chart=ChartFactory.createBarChart(
                "Plant types by alimentation", //Chart Title
                "Carnivorous", // Category axis
                "Number of plants", // Value axis
                dataset,
                PlotOrientation.VERTICAL,
                true,true,false
        );

        return chart;
    }

    private static PieDataset createPieDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        PlantRepository plantRepository = new PlantRepository("gradina_botanica", null);
        List<Plant> plants = plantRepository.plantList();
        List<String> types = new ArrayList<>();

        for (Plant p: plants) {
            if (!types.contains(p.getPlantType())) {
                types.add(p.getPlantType());
            }
        }

        for (String t: types) {
            int num = 0;
            for (Plant p: plants) {
                if (p.getPlantType().equals(t)) {
                    num++;
                }
            }

            dataset.setValue(t, num);
        }

        return dataset;
    }

    private static JFreeChart createPieChart(PieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart(
                "Plant types",   // chart title
                dataset,          // data
                true,             // include legend
                true,
                false);

        PiePlot plot = (PiePlot) chart.getPlot();
        PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator(
                "{0}: {1} ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
        plot.setLabelGenerator(gen);

        return chart;
    }

}
