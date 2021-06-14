package com.beachbody.gymservice.endpoint;
import com.beachbody.gymservice.service.SuggestionService;
import com.beachbody.gymservice.view.Suggestion;
import com.beachbody.gymservice.view.SuggestionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping("/suggestion")
public class SuggestionController {


    private SuggestionService suggestionService;

    public SuggestionController(SuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }

    @GetMapping("")
    public List<Suggestion> getSuggestions(@RequestParam Long date, @RequestParam(required = false) Double latitude, @RequestParam(required = false) Double longitude){

        return suggestionService.getSuggestions(
                Optional.ofNullable(latitude).orElse(34.052235),
                Optional.ofNullable(longitude).orElse(-118.243683),
                date);
    }


    @ExceptionHandler(SuggestionException.class)
    private ResponseEntity<String> handleBadRequest(SuggestionException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request." + exception.getMessage());
    }
}
