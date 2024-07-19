package org.gbbv.musikkspring.controllers.RestControllers;

import org.gbbv.musikkspring.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS})
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;
    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

    @GetMapping
    public ResponseEntity<List<Object>> search(@RequestParam String query) {
        List<Object> results = searchService.search(query);
        logger.info("Search results for query '{}': {}", query, results);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}