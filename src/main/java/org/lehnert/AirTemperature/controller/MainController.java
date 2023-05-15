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
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BiFunction;

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

        BiFunction<java.util.Date, java.util.Date, Iterable<AirTemperature>> getDataByRange = (first, last) ->
                airTemperatureRepository
                .getAirTemperatureByBetweenFirstAndLast(new Date(first.getTime()),
                        new Date(last.getTime()));

        VoidFunction getWeekData = () -> {
            Calendar c = Calendar.getInstance();

            // Set the calendar to monday of the current week
            c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

            // Get the first and last day of the week
            var first = c.getTime();
            c.add(Calendar.DATE, 6);
            var last = c.getTime();

            // return only days of week with data
            for (AirTemperature d: getDataByRange.apply(first, last)) {
                temps.add(d.getTemperature());
                labels.add(new SimpleDateFormat("EEEE").format(d.getDate()));
            }
        };

        VoidFunction getMonthData = () -> {
            Calendar c = Calendar.getInstance();

            var firstDay = c.getActualMinimum(Calendar.DAY_OF_MONTH);
            c.set(Calendar.DAY_OF_MONTH, firstDay);
            var first = c.getTime();

            var lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
            c.set(Calendar.DAY_OF_MONTH, lastDay);
            var last = c.getTime();

            for (AirTemperature data: getDataByRange.apply(first, last)) {
                c.setTime(data.getDate());
                labels.add(String.valueOf(c.get(Calendar.DAY_OF_MONTH)));
                temps.add(data.getTemperature());
            }
        };

        VoidFunction getYearData = () -> {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_YEAR, 1);
            var first = c.getTime();

            c.set(Calendar.MONTH, 11); // 11 = december
            c.set(Calendar.DAY_OF_MONTH, 31); // new years eve

            var last = c.getTime();

            Map<String, List<Double>> sumDaysOfMonth = new TreeMap<>(Collections.reverseOrder());

            for (AirTemperature d: getDataByRange.apply(first, last)) {
                SimpleDateFormat dateFormat = new SimpleDateFormat( "LLLL");

                c.setTime(d.getDate());

                var MonthList =  sumDaysOfMonth.getOrDefault(dateFormat.format(d.getDate()), new ArrayList<>());
                MonthList.add(d.getTemperature());


                sumDaysOfMonth.put(dateFormat.format(d.getDate()), MonthList);
            }

            for (var set : sumDaysOfMonth.entrySet()) {

                labels.add(set.getKey());

                // get average of the month
                temps.add(set.getValue().stream()
                        .mapToDouble(Double::doubleValue)
                        .average()
                        .orElse(Double.NaN));
            }
        };

        try {
            RangeEnum possibleRange = RangeEnum.valueOf(range);

            // give the values by range
            switch (possibleRange) {
                case WEEK -> getWeekData.apply();
                case MONTH -> getMonthData.apply();
                case YEAR -> getYearData.apply();
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
