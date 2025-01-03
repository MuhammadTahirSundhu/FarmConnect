package com.example.springone.ConsumerPackage.Service;

import com.example.springone.ConsumerPackage.Entity.ConsumerEntity;
import com.example.springone.ConsumerPackage.Model.Consumer;
import com.example.springone.ConsumerPackage.Repositry.ConsumerRepository;
import com.example.springone.FarmersPackage.Entity.FarmerEntity;
import com.example.springone.FarmersPackage.Model.Farmer;
import org.springframework.beans.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ConsumerServiceImpl implements ConsumerService {

    private ConsumerRepository consumerRepo;

    public ConsumerServiceImpl(ConsumerRepository consumerRepo) {
        this.consumerRepo = consumerRepo;
    }

    @Override
    public Consumer insertConsumer(Consumer consumer) {
        Timestamp date = new Timestamp(System.currentTimeMillis());
        ConsumerEntity consumerEntity = new ConsumerEntity();
        BeanUtils.copyProperties(consumer,consumerEntity);
        consumerEntity.setRegisteredDate(date);
        consumerRepo.save(consumerEntity);
        return consumer;
    }

    @Override
    public void deleteConsumer(int id) {
        ConsumerEntity consumerEntity = consumerRepo.findById(id).get();
        consumerRepo.delete(consumerEntity);
    }

    @Override
    public void updateConsumer(int id, Consumer consumer) {
        try {
            Optional<ConsumerEntity> optionalConsumerEntity = consumerRepo.findById(id);
            if (optionalConsumerEntity.isPresent()) {
                ConsumerEntity consumerEntity = optionalConsumerEntity.get();
                consumerEntity.setName(consumer.getName());
                consumerEntity.setLocation(consumer.getLocation());
                consumerEntity.setRegisteredDate(consumer.getRegisteredDate());
                consumerEntity.setEmail(consumer.getEmail());
                consumerEntity.setPassword(consumer.getPassword());

                // Save the updated consumer entity
                consumerRepo.save(consumerEntity);
                log.info("Record Updated Successfully!");
            } else {
                throw new RuntimeException("Consumer with ID " + id + " not found.");
            }
        } catch (Exception e) {
            log.error("Error updating consumer: " + e.getMessage(), e);
            throw e;
        }

    }

    @Override
    public Consumer getConsumerById(int id) {
        ConsumerEntity  consumerEntity= consumerRepo.findById(id).get();
        Consumer consumer = new Consumer();
        BeanUtils.copyProperties(consumerEntity,consumer);
        return consumer;
    }

    @Override
    public List<Consumer> getAllConsumers() {
        List<ConsumerEntity> consumerEntities = consumerRepo.findAll();

        return consumerEntities.stream()
                .map(consumerEntity -> new Consumer(
                        consumerEntity.getConsumerID(),
                        consumerEntity.getName(),
                        consumerEntity.getLocation(),
                        consumerEntity.getRegisteredDate(),
                        consumerEntity.getEmail(),
                        consumerEntity.getPassword()
                ))
                .collect(Collectors.toList());

    }

    @Override
    public Consumer login(String email, String password) {
        ConsumerEntity consumerEntity = consumerRepo.findByEmail(email);



        if (consumerEntity == null) {
            throw new RuntimeException("consumer not found");
        }

        Consumer consumer= new Consumer();
        BeanUtils.copyProperties(consumerEntity,consumer);

        // Validate password (assuming it's stored as plain text, consider hashing it in real scenarios)
        if (!consumer.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        return consumer; // Return consumer details if validation succeeds
    }
}
