package com.beachbody.gymservice.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


/**
 * Data Model to store attributes of suggestion
 */

@Getter
@Setter
@AllArgsConstructor
public class Suggestion
{

    private String name;
    private Double latitude ;
    private Double longitude ;
    private List<String> timing;


}
