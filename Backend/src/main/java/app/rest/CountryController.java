package app.rest;

import app.exceptions.ModelNotFound;
import app.models.Country;
import app.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Handles all requests related to countries
 *
 * @author Pepijn dik, Fenne
 * @since 19-11-2022
 */
@Controller
public class CountryController {

    private final CountryRepository countryRepository;

    @Autowired
    public CountryController(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    /**
     * Get all countries
     *
     * @return ResponseEntity<Iterable < Country>> all countries
     */
    @GetMapping("/countries")
    public ResponseEntity<Iterable<Country>> getAllProjects() {
        return new ResponseEntity<>(countryRepository.findAll(), HttpStatus.OK);
    }

    /**
     * Get a country by id
     *
     * @param countryID Country id
     * @return HttpEntity<?> country
     */
    @GetMapping("/countries/{id}")
    public HttpEntity<?> getProject(@PathVariable(value = "id") Integer countryID) {
        Country p = countryRepository.findById(countryID);
        return countryRepository.findById(countryID) != null ? new ResponseEntity<>(p, HttpStatus.OK) : new ResponseEntity<>(new ModelNotFound("Project", "id", countryID), HttpStatus.NOT_FOUND);
    }
}


