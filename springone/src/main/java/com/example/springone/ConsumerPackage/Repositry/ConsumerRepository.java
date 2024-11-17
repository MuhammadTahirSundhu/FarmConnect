package com.example.springone.ConsumerPackage.Repositry;

import com.example.springone.ConsumerPackage.Entity.ConsumerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumerRepository extends JpaRepository<ConsumerEntity,Integer > {
}
