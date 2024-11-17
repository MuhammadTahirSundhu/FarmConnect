package com.example.springone.FarmersPackage.Repositry;

import com.example.springone.FarmersPackage.Entity.FarmerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FarmerRepository extends JpaRepository<FarmerEntity,Integer> {
}
