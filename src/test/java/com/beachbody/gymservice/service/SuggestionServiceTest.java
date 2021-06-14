package com.beachbody.gymservice.service;


import com.beachbody.gymservice.repository.WorkoutClass;
import com.beachbody.gymservice.repository.WorkoutClassRepository;
import com.beachbody.gymservice.view.Suggestion;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.when;

@SpringBootTest
class SuggestionServiceTest{

    private static final Long NINE_AM = TimeUnit.HOURS.toHours(9);
    private static final Long ELEVEN_AM = TimeUnit.HOURS.toHours(11);
    private static final Long ONE_PM = TimeUnit.HOURS.toHours(13);
    private static final Long THREE_PM = TimeUnit.HOURS.toHours(15);
    private static final Long TWO_PM = TimeUnit.HOURS.toHours(14);


    @DataProvider
    public Object[][] testGetSuggestionsProvider(){
        return new Object[][]{
                // Test Data when the requested time is 8:00 AM
                {ImmutableSet.of(createGym(1L,"Gym1",33.9540723,-118.3636729,
                        ImmutableList.of(NINE_AM,ELEVEN_AM,ONE_PM,THREE_PM))),
                        33.9540723,-118.3636729,1623657631000L,
                        ImmutableList.of(new Suggestion("Gym1",33.9540723,-118.3636729,
                        ImmutableList.of("9 AM","11 AM","1 PM","3 PM")))},
                //Test Data  when the requested time is 1:30 PM

                {ImmutableSet.of(createGym(1L,"Gym1",33.9540723,-118.3636729,
                        ImmutableList.of(NINE_AM,ELEVEN_AM,ONE_PM,THREE_PM))),
                        33.9540723,-118.3636729,1623681031000L,
                        ImmutableList.of(new Suggestion("Gym1",33.9540723,-118.3636729,
                                ImmutableList.of("3 PM")))},
                //Test Data  when the requested time is 5:30 PM

                {ImmutableSet.of(createGym(1L,"Gym1",33.9540723,-118.3636729,
                        ImmutableList.of(NINE_AM,ELEVEN_AM,ONE_PM,THREE_PM))),
                        33.9540723,-118.3636729,1623691831000L,
                        ImmutableList.of()},
                //Test Data  when there are more than 2 Gyms in requested time i.e, 1:00PM

                {ImmutableSet.of(createGym(1L,"Gym1",33.9540723,-118.3636729,
                        ImmutableList.of(NINE_AM,ELEVEN_AM,ONE_PM,THREE_PM)),
                        createGym(1L,"Gym2",34.002827,-118.331835,
                        ImmutableList.of(NINE_AM,ELEVEN_AM,TWO_PM))),
                        33.9540723,-118.3636729,1622554231000L,
                        ImmutableList.of(new Suggestion("Gym2",34.002827,-118.331835, ImmutableList.of("2 PM")),
                                new Suggestion("Gym1",33.9540723,-118.3636729, ImmutableList.of("3 PM")))}
        };
    }

    @Test(dataProvider = "testGetSuggestionsProvider")
    public void testGetSuggestions(Set<WorkoutClass> workoutClasses, Double latitude, Double longitude, Long time, List<Suggestion> expectedSuggestion) {
        Mocks m = new Mocks();
        when(m.workoutClassRepository.getWorkoutCLasses()).thenReturn(workoutClasses);
        List<Suggestion> suggestions = m.suggestionService.getSuggestions(latitude,longitude,time);
        if(!expectedSuggestion.isEmpty()) {
            Assert.assertEquals(expectedSuggestion.get(0).getName(), suggestions.get(0).getName());
            Assert.assertEquals(expectedSuggestion.get(0).getTiming().size(), suggestions.get(0).getTiming().size());
            Assert.assertEquals(expectedSuggestion.get(0).getTiming(), suggestions.get(0).getTiming());
        }

    }

    private WorkoutClass createGym(Long id, String name, Double latitude, Double longitude, List<Long> timings){
        return new WorkoutClass(id,name,latitude,latitude,timings);
    }


    private static class Mocks{

        public Mocks() {
            MockitoAnnotations.openMocks(this);
            suggestionService = new SuggestionService(workoutClassRepository);
        }

        SuggestionService suggestionService;

        @Mock
        WorkoutClassRepository workoutClassRepository;

    }


}