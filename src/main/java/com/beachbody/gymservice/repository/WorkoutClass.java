package com.beachbody.gymservice.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Backend Data Entity for WorkoutClass
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutClass {

    private long id;
    private String name;
    private double latitude;
    private double longitude;
    private List<Long> timings;


}
