package com.beachbody.gymservice.view;

import com.beachbody.gymservice.repository.WorkoutClass;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * Class used to format {@link WorkoutClass} to {@link Suggestion}
 */
public class SuggestionFormatter {

    /**
     * formats the list of gyms to list of suggestions
     * @param workoutClasses to be formatted
     * @return list of suggestions
     */
    public static List<Suggestion> formatGymToSuggestion(List<WorkoutClass> workoutClasses){

       return workoutClasses.stream()
                .map(gym -> new Suggestion(gym.getName(), gym.getLatitude(), gym.getLongitude(),convert24HRto12HrFormat(gym.getTimings())))
                .collect(Collectors.toList());


    }

    private static List<String> convert24HRto12HrFormat(List<Long> hours){
        return hours.stream()
                .map(hour -> {
                    DateTime dt = new DateTime(TimeUnit.HOURS.toMillis(hour), DateTimeZone.UTC);
                    return dt.toString("h a");
                })
                .collect(Collectors.toList());

    }
}
