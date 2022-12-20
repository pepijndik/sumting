package app.rest;

import app.models.Batch.Batch;
import app.repositories.Batch.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;

@Controller
public class BatchController {

    @Autowired
    private BatchRepository batchRepository;

    @GetMapping("/batch")
    public ResponseEntity<Iterable<Batch>> getAllBatches() {
        return new ResponseEntity<>(batchRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/batch")
    public ResponseEntity<Batch> createBatch(@RequestBody Batch batch) {
        try {
            batch.setCreatedAt(LocalDate.now());
            Batch newBatch = this.batchRepository.save(batch);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(newBatch.getId()).toUri();
            return ResponseEntity.created(location).body(newBatch);
        } catch (Exception e) {
            System.out.printf(e.getMessage());
            return ResponseEntity.internalServerError().body(batch); //The provided data
        }
    }

    @PostMapping("/batch/upload")
    public ResponseEntity<String> uploadBatch() {
       // AmazonConfig.bucketSettings.proof
        return new ResponseEntity<>("Batch upload", HttpStatus.OK);
    }
}
