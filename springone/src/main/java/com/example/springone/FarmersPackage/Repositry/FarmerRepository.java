package com.example.springone.FarmersPackage.Repositry;

import com.example.springone.FarmersPackage.Entity.FarmerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FarmerRepository extends JpaRepository<FarmerEntity,Integer> {
    @Query("SELECT f FROM FarmerEntity f WHERE f.email = :email")
    FarmerEntity findByEmail(@Param("email") String email);

}
