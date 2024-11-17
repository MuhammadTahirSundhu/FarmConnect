package com.example.springone.ConsumerPackage.Controller;

import com.example.springone.ConsumerPackage.Model.Consumer;
import com.example.springone.ConsumerPackage.Service.ConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class ConsumerController {

    private final ConsumerService consumerService;

    public ConsumerController(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @PostMapping("/consumer")
    public ResponseEntity<Consumer> insertConsumer(@RequestBody Consumer consumer) {
        try {
            Consumer savedConsumer = consumerService.insertConsumer(consumer);
            return new ResponseEntity<>(savedConsumer, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/consumer/{id}")
    public ResponseEntity<String> deleteConsumer(@PathVariable("id") int id) {
        try {
            consumerService.deleteConsumer(id);
            return new ResponseEntity<>("Consumer deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Consumer not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/consumer/{id}")
    public ResponseEntity<String> updateConsumer(@PathVariable("id") int id, @RequestBody Consumer consumer) {
        try {
            consumerService.updateConsumer(id, consumer);
            return new ResponseEntity<>("Consumer updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating consumer with ID: " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/consumer/{id}")
    public ResponseEntity<Consumer> getConsumerById(@PathVariable("id") int id) {
        try {
            Consumer consumer = consumerService.getConsumerById(id);
            if (consumer == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(consumer, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/consumer")
    public ResponseEntity<List<Consumer>> getAllConsumers() {
        try {
            List<Consumer> consumers = consumerService.getAllConsumers();
            if (consumers == null || consumers.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(consumers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
