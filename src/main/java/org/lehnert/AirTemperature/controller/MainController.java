package org.lehnert.AirTemperature.controller;

import lombok.AllArgsConstructor;
import org.lehnert.AirTemperature.db.repository.AirTemperatureRepository;
import org.lehnert.AirTemperature.db.tables.AirTemperature;
import org.lehnert.AirTemperature.helper.RangeEnum;
import org.lehnert.AirTemperature.helper.VoidFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.function.Function;

@AllArgsConstructor
@RequestMapping("/")
@Controller
public class MainController {
    @Autowired
    private final AirTemperatureRepository airTemperatureRepository;
    @GetMapping
    String getMainPage(@RequestParam(name = "range", required = false) String range, Model model) {
        ArrayList<Double> temps = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        VoidFunction getWeekData = () -> {
            Calendar c = Calendar.getInstance();

            // Set the calendar to monday of the current week
            c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

            // Get the first and last day of the week
            var first = c.getTime();
            c.add(Calendar.DATE, 6);
            var last = c.getTime();

            // query data
            Iterable<AirTemperature> data = airTemperatureRepository
                    .getAirTemperatureByBetweenFirstAndLast(new java.sql.Date(first.getTime()),
                            new java.sql.Date(last.getTime()));

            // return only days of week with data
            for (AirTemperature d: data) {
                temps.add(d.getTemperature());
                labels.add(new SimpleDateFormat("EEEE").format(d.getDate()));
            }
        };

        try {
            RangeEnum possibleRange = RangeEnum.valueOf(range);

            // give the values by range
            switch (possibleRange) {
                case WEEK -> getWeekData.apply();
            }

        } catch (Exception ignore) {
            // return default range
            getWeekData.apply();
        }

        model.addAttribute("labels", labels);
        model.addAttribute("temps", temps);

        return "index";
    }
}
