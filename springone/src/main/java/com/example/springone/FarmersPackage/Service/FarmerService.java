package com.example.springone.FarmersPackage.Service;


import com.example.springone.FarmersPackage.Entity.FarmerEntity;
import com.example.springone.FarmersPackage.Model.Farmer;

import java.util.List;

public interface FarmerService {
    Farmer insertFarmer(Farmer farmer);

    void deleteFarmer(int id);

    void updateFarmer(int id, Farmer farmer);

    Farmer getFarmerById(int id);

    List<Farmer> getAllFarmers();

    Farmer login(String email, String password);
}
