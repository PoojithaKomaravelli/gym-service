package com.beachbody.gymservice.repository;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * Repository class to fetch {@link WorkoutClass}
 */
@Component
public class WorkoutClassRepository {

    private static final Long NINE_AM = TimeUnit.HOURS.toHours(9);
    private static final Long TEN_AM = TimeUnit.HOURS.toHours(10);
    private static final Long ELEVEN_AM = TimeUnit.HOURS.toHours(11);
    private static final Long TWELVE_PM = TimeUnit.HOURS.toHours(12);
    private static final Long ONE_PM = TimeUnit.HOURS.toHours(13);
    private static final Long TWO_PM = TimeUnit.HOURS.toHours(14);
    private static final Long THREE_PM = TimeUnit.HOURS.toHours(15);
    private static final Long FOUR_PM = TimeUnit.HOURS.toHours(16);


    /**
     * Dummy static database to fetch {@link WorkoutClass}
     * @return Set of WorkoutClass
     */
    public Set<WorkoutClass> getWorkoutCLasses() {
       ImmutableSet.Builder<WorkoutClass> builder  = ImmutableSet.builder();
        builder.add(new WorkoutClass(1L,"Gym1",33.9540723,-118.3636729,
                                    ImmutableList.of(NINE_AM,ELEVEN_AM,ONE_PM,THREE_PM)),
                    new WorkoutClass(2L,"Gym2",34.002827,-118.331835,
                                    ImmutableList.of(TEN_AM,TWELVE_PM,TWO_PM,FOUR_PM)),
                    new WorkoutClass(3L,"Gym3",33.9988632,-118.3498971,
                                    ImmutableList.of(ELEVEN_AM,ONE_PM,THREE_PM)));

        return builder.build();

    }

}

