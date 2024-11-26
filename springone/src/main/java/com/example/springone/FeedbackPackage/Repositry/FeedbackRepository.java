package com.example.springone.FeedbackPackage.Repositry;

import com.example.springone.FeedbackPackage.Entity.FeedbackEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<FeedbackEntity,Integer> {

    @Query("SELECT f FROM FeedbackEntity f WHERE f.consumerEntity.consumerID = :consumerID")
    List<FeedbackEntity> findByConsumerId(@Param("consumerID") int consumerID);


    @Query("SELECT f FROM FeedbackEntity f WHERE f.farmerEntity.farmerid = :farmerID")
    List<FeedbackEntity> findByFarmerId(@Param("farmerID") int farmerID);

}
