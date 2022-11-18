package com.example.oenskeliste.Misc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DaysToChristmasCalc {


    public static long calculateDaysToCristmas()  {

        LocalDate christmas = LocalDate.parse("2022-12-24");
        LocalDate current = LocalDate.now();

        return current.until(christmas, ChronoUnit.DAYS);
    }
}
