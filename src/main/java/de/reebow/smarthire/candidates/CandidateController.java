package de.reebow.smarthire.candidates;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.reebow.smarthire.matchings.JobOfferSuggestion;
import de.reebow.smarthire.matchings.MatchingService;

@RestController
@RequestMapping("/api/candidates")
class CandidateController {

    private final MatchingService matchingService;

    CandidateController(MatchingService matchingService) {
        this.matchingService = matchingService;
    }

    @PostMapping("/upload")
    ResponseEntity<List<JobOfferSuggestion>> uploadText(@RequestBody String text) {
        if (text.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var result = matchingService.machJobOffers(text);

        return ResponseEntity.ok(result);
    }
}