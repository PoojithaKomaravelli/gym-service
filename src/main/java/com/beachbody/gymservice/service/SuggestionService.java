package com.beachbody.gymservice.service;


import com.beachbody.gymservice.repository.WorkoutClass;
import com.beachbody.gymservice.repository.WorkoutClassRepository;
import com.beachbody.gymservice.view.Suggestion;
import com.beachbody.gymservice.view.SuggestionException;
import com.beachbody.gymservice.view.SuggestionFormatter;
import com.google.common.primitives.Doubles;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Service class for handling business logic
 */
@Service
public class SuggestionService {

    private WorkoutClassRepository workoutClassRepository;

    private static Logger logger = LoggerFactory.getLogger(SuggestionService.class);


    public SuggestionService(WorkoutClassRepository workoutClassRepository) {

        this.workoutClassRepository = workoutClassRepository;
    }

    /**
     * retrieves suggestions based on user preferences
     * @param latitude
     * @param longitude
     * @param time
     * @return List of Suggestions ordered by user's desired time and gym location
     */
    public List<Suggestion> getSuggestions(Double latitude, Double longitude, Long time){

        int dayOfTheWeek = getDayOfTheWeek(time);
        if(dayOfTheWeek == 6 || dayOfTheWeek == 7){
            logger.warn("User input is a weekend.Hence cannot process the request");
            throw new SuggestionException("Weekends are not allowed as input");
        }
        Set<WorkoutClass> workoutClasses = workoutClassRepository.getWorkoutCLasses();

       // removing past time slots
       workoutClasses.forEach(gym -> {
               List<Long> timings = gym.getTimings().stream().
                     filter(a -> a > getHoursFromTimestamp(time) )
                      .collect(Collectors.toList());
               gym.setTimings(timings);

       });

       // remove the gyms if timings doesn't fall within client's requested timings
      workoutClasses = workoutClasses.stream()
               .filter( gym -> !gym.getTimings().isEmpty())
               .collect(Collectors.toSet());

      //sort the Gyms based on timings and then location if required
      List<WorkoutClass> sortedWorkoutClasses = workoutClasses.stream().sorted((gym1, gym2) -> {
                           if(gym1.getTimings().get(0).compareTo(gym2.getTimings().get(0)) == 0) {
                               return Doubles.compare(distance(gym1.getLatitude(), latitude, gym1.getLongitude(), longitude, 0, 0),
                                       distance(gym2.getLatitude(), latitude, gym2.getLongitude(), longitude, 0, 0));
                           }else{
                               return gym1.getTimings().get(0).compareTo(gym2.getTimings().get(0)) ;
                           }
                })
              .collect(Collectors.toList());

        return SuggestionFormatter.formatGymToSuggestion(sortedWorkoutClasses);

    }

    /**
     * Method for calculating distance between two geo co-ordinates
     */
    private double distance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    /**
     * fetch hours of the day from the given epoch
     * NOTE: expected epoch is in UTC timezone
     */
    private Long getHoursFromTimestamp(Long time){
        DateTime preferredTime = new DateTime(time, DateTimeZone.UTC);
        return TimeUnit.HOURS.toHours(preferredTime.getHourOfDay());
    }

    /**
     * fetch Day of the Week from the given epoch
     * NOTE: expected epoch is in UTC timezone
     */
    private int getDayOfTheWeek(Long time){
        DateTime preferredTime = new DateTime(time, DateTimeZone.UTC);
        return preferredTime.getDayOfWeek();
    }
}
