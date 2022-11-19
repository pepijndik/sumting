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


@Controller
public class CountryController {


    private final CountryRepository countryRepository;

    @Autowired
    public CountryController(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @GetMapping("/countries")
    public ResponseEntity<Iterable<Country>> getAllProjects() {
        return new ResponseEntity<>(countryRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/countries/{id}")
    public HttpEntity<?> getProject(@PathVariable(value = "id") Integer projectId) {
        Country p = countryRepository.findById(projectId);
        return countryRepository.findById(projectId) != null ? new ResponseEntity<>(p, HttpStatus.OK) : new ResponseEntity<ModelNotFound>(new ModelNotFound("Project", "id", projectId), HttpStatus.NOT_FOUND);
    }
}


