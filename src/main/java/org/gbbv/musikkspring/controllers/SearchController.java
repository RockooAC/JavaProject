package org.gbbv.musikkspring.controllers;

import org.gbbv.musikkspring.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS})
@RequestMapping("/search")
public class SearchController {
    private final SearchService searchService;
    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }
    @GetMapping
    public String search(@RequestParam String query, Model model) {
        List<Object> results = searchService.search(query);
        logger.info("Search results for query '{}': {}", query, results);
        model.addAttribute("results", results);
        return "searchResults";
    }
}