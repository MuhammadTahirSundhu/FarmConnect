package com.example.springone.ConsumerPackage.Repositry;

import com.example.springone.ConsumerPackage.Entity.ConsumerEntity;
import com.example.springone.FarmersPackage.Entity.FarmerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConsumerRepository extends JpaRepository<ConsumerEntity,Integer > {

    @Query("SELECT f FROM ConsumerEntity f WHERE f.email = :email")
    ConsumerEntity findByEmail(@Param("email") String email);
}
