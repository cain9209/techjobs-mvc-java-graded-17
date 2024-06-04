package org.launchcode.techjobsmvc.controllers;


import org.launchcode.techjobsmvc.models.Job;
import org.launchcode.techjobsmvc.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

import static org.launchcode.techjobsmvc.controllers.ListController.columnChoices;


/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @GetMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);
        return "search";
    }

    // TODO #3 - Create a handler to process a search request and render the updated search view.

    @PostMapping(value = "results")
    public String displaySearchResults(Model model, @RequestParam String searchType, @RequestParam(required = false) String searchTerm) {
        model.addAttribute("columns", columnChoices);
        ArrayList<Job> jobs;

        if (searchTerm != null && !searchTerm.isBlank()) {
            if (searchType.equalsIgnoreCase("all")) {
                // If searchType is "all", search all columns for the searchTerm
                jobs = JobData.findByValue(searchTerm);
                model.addAttribute("title", "Jobs with keyword: " + searchTerm);
            } else {
                // If a specific searchType is selected, search by that column
                jobs = JobData.findByColumnAndValue(searchType, searchTerm);
                model.addAttribute("title", "Jobs with " + columnChoices.get(searchType) + ": " + searchTerm);
            }
        } else {
            // If no searchTerm is provided, return all jobs
            jobs = JobData.findAll();
            model.addAttribute("title", "All Jobs");
        }

        model.addAttribute("jobs", jobs);
        model.addAttribute("columns", columnChoices);

        return "search";
    }

}

// code needs to use the index.html file to render //