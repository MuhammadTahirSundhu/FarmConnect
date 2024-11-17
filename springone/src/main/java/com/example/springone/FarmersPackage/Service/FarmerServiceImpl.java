package com.example.springone.FarmersPackage.Service;

import com.example.springone.FarmersPackage.Entity.FarmerEntity;
import com.example.springone.FarmersPackage.Model.Farmer;
import com.example.springone.FarmersPackage.Repositry.FarmerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FarmerServiceImpl implements FarmerService {

    private FarmerRepository farmerRepo;

    public FarmerServiceImpl(FarmerRepository farmerRepo) {
        this.farmerRepo = farmerRepo;
    }

    @Override
    public Farmer insertFarmer(Farmer farmer) {

        FarmerEntity farmerEntity = new FarmerEntity();
        BeanUtils.copyProperties(farmer,farmerEntity);
        farmerRepo.save(farmerEntity);
        return farmer;

    }

    @Override
    public void deleteFarmer(int id) {
        FarmerEntity farmerEntity = farmerRepo.findById(id).get();
        farmerRepo.delete(farmerEntity);
    }

    @Override
    public void updateFarmer(int id, Farmer farmer) {
        try {
            Optional<FarmerEntity> optionalFarmerEntity = farmerRepo.findById(id);
            if (optionalFarmerEntity.isPresent()) {
                FarmerEntity farmerEntity = optionalFarmerEntity.get();
                farmerEntity.setAvailabilitystatus(farmer.getAvailabilitystatus());
                farmerEntity.setName(farmer.getName());
                farmerEntity.setFarmlocation(farmer.getFarmlocation());
                farmerEntity.setCroptypes(farmer.getCroptypes());
                farmerEntity.setRegistereddate(farmer.getRegistereddate());
                farmerEntity.setEmail(farmer.getEmail());
                farmerEntity.setPassword(farmer.getPassword());

                // Save the updated farmer entity
                farmerRepo.save(farmerEntity);
                log.info("Record Updated Successfully!");
            } else {
                throw new RuntimeException("Farmer with ID " + id + " not found.");
            }
        } catch (Exception e) {
            log.error("Error updating farmer: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Farmer getFarmerById(int id) {
        FarmerEntity farmerEntity= farmerRepo.findById(id).get();
        Farmer farmer= new Farmer();
        BeanUtils.copyProperties(farmerEntity,farmer);
        return farmer;
    }

    @Override
    public List<Farmer> getAllFarmers() {
        List<FarmerEntity> farmerEntities = farmerRepo.findAll();

        return farmerEntities.stream()
                .map(farmerEntity -> new Farmer(
                        farmerEntity.getFarmerid(),
                        farmerEntity.getName(),
                        farmerEntity.getFarmlocation(),
                        farmerEntity.getCroptypes(),
                        farmerEntity.getRegistereddate(),
                        farmerEntity.getAvailabilitystatus(),
                        farmerEntity.getEmail(),
                        farmerEntity.getPassword()
                ))
                .collect(Collectors.toList());
    }



}
