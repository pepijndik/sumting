package app.rest;

import app.models.Batch.Batch;
import app.models.Order.OrderLine;
import app.models.Project.Project;
import app.repositories.Batch.BatchRepository;
import app.repositories.Order.OrderlineRepository;
import app.repositories.Project.ProjectServiceImpl;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class BatchController {

    @Autowired
    private BatchRepository batchRepository;
    @Autowired
    private ProjectServiceImpl projectService;
    @Autowired
    private OrderlineRepository orderlineRepository;

    @GetMapping("/batch")
    public ResponseEntity<Iterable<Batch>> getAllBatches() {
        return new ResponseEntity<>(batchRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/batch/{id}")
    public ResponseEntity<Batch> getBatch(@PathVariable(value = "id") Integer batchId) {
        return new ResponseEntity<>(batchRepository.findById(batchId), HttpStatus.OK);
    }

    @PostMapping("/batch")
    public ResponseEntity<Batch> createBatch(@RequestBody ObjectNode batch) {
        try {

            Batch newBatch = new Batch();
            newBatch.setCreatedAt(LocalDateTime.now());
            newBatch.setTextPlanned(batch.get("textPlanned").asText());
            newBatch.setBatchSize(batch.get("batchSize").asInt());
            newBatch.setProjectKey(batch.get("projectKey").asInt());

            Optional<Project> batchProject = this.projectService.findById(batch.get("projectKey").asInt());
            newBatch.setProject(batchProject.get());

            for (int i = 0; i < batch.get("batchSize").asInt(); i++) {
                Optional<OrderLine> batchOrderline =
                        Optional.ofNullable(this.orderlineRepository.findById(batch.get("orderlines").get(i).get("id").asInt()));

                batchOrderline.get().setBatch(newBatch);
            }

            newBatch = this.batchRepository.save(newBatch);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(newBatch.getId()).toUri();
            return ResponseEntity.created(location).body(newBatch);
        } catch (Exception e) {
            Batch errBatch = new Batch();
            System.out.printf(e.getMessage());
            return ResponseEntity.internalServerError().body(errBatch);
        }
    }

    @PostMapping("/batch/upload")
    public ResponseEntity<String> uploadBatch() {
       // AmazonConfig.bucketSettings.proof
        return new ResponseEntity<>("Batch upload", HttpStatus.OK);
    }

    @DeleteMapping("/batch/{id}")
    public ResponseEntity<Void> deleteBatch(@PathVariable(value = "id") Integer batchId) {
        Batch batchToDelete = batchRepository.findById(batchId);

        if (orderlineRepository.findAllByBatch(batchToDelete) != null) {
            for (OrderLine orderLine : orderlineRepository.findAllByBatch(batchToDelete)) {
                orderLine.setBatch(null);
                orderlineRepository.save(orderLine);
            }
        }

        batchRepository.delete(batchToDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
