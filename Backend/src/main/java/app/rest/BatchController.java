package app.rest;

import app.models.Batch.Batch;
import app.repositories.Batch.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BatchController {

    @Autowired
    private BatchRepository batchRepository;

    @GetMapping("/batch")
    public ResponseEntity<Iterable<Batch>> getAllBatches() {
        return new ResponseEntity<>(batchRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/batch/upload")
    public ResponseEntity<String> uploadBatch() {
       // AmazonConfig.bucketSettings.proof
        return new ResponseEntity<>("Batch upload", HttpStatus.OK);
    }
}
