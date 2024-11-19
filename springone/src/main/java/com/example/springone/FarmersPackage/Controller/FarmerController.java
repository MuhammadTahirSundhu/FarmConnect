package com.example.springone.FarmersPackage.Controller;

import com.example.springone.FarmersPackage.Model.Farmer;
import com.example.springone.FarmersPackage.Service.FarmerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class FarmerController {

    private final FarmerService farmService;

    public FarmerController(FarmerService farmService) {
        this.farmService = farmService;
    }
    @PostMapping("/farmer")
    public ResponseEntity<Farmer> insertFarmer(@RequestBody Farmer farmer) {
        try {
            Farmer savedFarmer = farmService.insertFarmer(farmer);
            return new ResponseEntity<>(savedFarmer, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/farmer/{id}")
    public ResponseEntity<String> deleteFarmer(@PathVariable("id") int id) {
        try {
            farmService.deleteFarmer(id);
            return new ResponseEntity<>("Farmer deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Farmer not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/farmer/{id}")
    public ResponseEntity<String> updateFarmer(@PathVariable("id") int id, @RequestBody Farmer farmer) {
        try {
            farmService.updateFarmer(id, farmer);
            return new ResponseEntity<>("Farmer updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating farmer with ID: " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/farmer/{id}")
    public ResponseEntity<Farmer> getFarmerById(@PathVariable("id") int id) {
        try {
            Farmer farmer = farmService.getFarmerById(id);
            if (farmer == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(farmer, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/farmer")
    public ResponseEntity<List<Farmer>> getAllFarmers() {
        try {
            List<Farmer> farmers = farmService.getAllFarmers();
            if (farmers == null || farmers.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(farmers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}