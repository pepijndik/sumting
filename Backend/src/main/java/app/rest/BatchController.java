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

/**
 * Handles the Endpoints belonging to a batch
 */
@Controller
public class BatchController {

    @Autowired
    private BatchRepository batchRepository;
    @Autowired
    private ProjectServiceImpl projectService;
    @Autowired
    private OrderlineRepository orderlineRepository;

    /**
     * Gets all batches
     *
     * @return all batches
     */
    @GetMapping("/batch")
    public ResponseEntity<Iterable<Batch>> getAllBatches() {
        return new ResponseEntity<>(batchRepository.findAll(), HttpStatus.OK);
    }

    /**
     * Gets a batch by id
     *
     * @param batchId the id of the batch
     * @return the batch
     */
    @GetMapping("/batch/{id}")
    public ResponseEntity<Batch> getBatch(@PathVariable(value = "id") Integer batchId) {
        return new ResponseEntity<>(batchRepository.findById(batchId), HttpStatus.OK);
    }

    /**
     * Creates a batch
     *
     * @param batch the batch to be created
     * @return the created batch
     */
    @PostMapping("/batch")
    public ResponseEntity<Batch> createBatch(@RequestBody ObjectNode batch) {
        try {

            Batch newBatch = new Batch();
            newBatch.setCreatedAt(LocalDateTime.now());
            newBatch.setTextPlanned(batch.get("textPlanned").asText());
            newBatch.setBatchSize(batch.get("batchSize").asInt());
            newBatch.setProjectKey(batch.get("projectKey").asInt());

            Optional<Project> batchProject = this.projectService.findById(batch.get("projectKey").asInt());
            if (batchProject.isPresent()) newBatch.setProject(batchProject.get());

            for (int i = 0; i < batch.get("batchSize").asInt(); i++) {
                Optional<OrderLine> batchOrderline =
                        Optional.ofNullable(this.orderlineRepository.findById(batch.get("orderlines").get(i).get("id").asInt()));

                if (batchOrderline.isPresent()) batchOrderline.get().setBatch(newBatch);
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

    @PutMapping("/batch/update")
    public ResponseEntity<Batch> editBatch(@RequestBody ObjectNode batch) {
        try {
            Batch updatedBatch = this.batchRepository.findById(batch.get("id").asInt());
            // Add the new data of the batch to the Batch variables.
            updatedBatch.setTextPlanned(batch.get("textPlanned").asText());
            updatedBatch.setBatchSize(batch.get("batchSize").asInt());
            updatedBatch.setUpdatedAt(LocalDateTime.now());

            // Looks for every orderline of updatedBatch and sets the Batch to null.
            if (orderlineRepository.findAllByBatch(updatedBatch) != null) {
                for (OrderLine orderLine : orderlineRepository.findAllByBatch(updatedBatch)) {
                    orderLine.setBatch(null);
                    orderlineRepository.save(orderLine);
                }
            }

            // Loops through all orderlines of the Batch from the RequestBody, and adds the Batch to each orderline.
            for (int i = 0; i < batch.get("batchSize").asInt(); i++) {
                OrderLine batchOrderline =
                        this.orderlineRepository.findById(batch.get("orderlines").get(i).get("id").asInt());

                batchOrderline.setBatch(updatedBatch);
                orderlineRepository.save(batchOrderline);
            }

            updatedBatch = this.batchRepository.save(updatedBatch);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(updatedBatch.getId()).toUri();
            return ResponseEntity.created(location).body(updatedBatch);
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

    /**
     * Deletes a batch
     *
     * @param batchId the id of the batch to be deleted
     * @return the deleted batch
     */
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
