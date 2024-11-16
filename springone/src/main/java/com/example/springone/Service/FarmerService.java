package com.example.springone.Service;


import com.example.springone.Model.Farmer;

import java.util.List;

public interface FarmerService {
    Farmer insertFarmer(Farmer farmer);

    void deleteFarmer(int id);

    void updateFarmer(int id, Farmer farmer);

    Farmer getFarmerById(int id);

    List<Farmer> getAllFarmers();
}
