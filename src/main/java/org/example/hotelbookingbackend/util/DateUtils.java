package org.example.hotelbookingbackend.util;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;


public class DateUtils {

    public static Set<Instant> getInstantsBetween(Instant startInstant, Instant endInstant) {
        Set<Instant> instants = new TreeSet<>();
        Instant currentInstant = startInstant;

        while (currentInstant.isBefore(endInstant) || currentInstant.equals(endInstant)) {
            instants.add(currentInstant);
            currentInstant = currentInstant.plusSeconds(86400); // Добавляем 24 часа в секундах
        }

        return instants;
    }

    public static boolean isInInterval(Set<Instant> dates, Instant leftDate, Instant rightDate){
        if (rightDate.isBefore(leftDate)){
            return false;
        }

        for (var date : dates){
            if (date.isAfter(leftDate) && date.isBefore(rightDate)){
                return true;
            }
        }

        return false;
    }

}
