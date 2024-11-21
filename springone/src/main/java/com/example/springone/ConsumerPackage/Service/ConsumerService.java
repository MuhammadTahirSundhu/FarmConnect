package com.example.springone.ConsumerPackage.Service;


import com.example.springone.ConsumerPackage.Model.Consumer;

import java.util.List;

public interface ConsumerService {
    Consumer insertConsumer(Consumer consumer);

    void deleteConsumer(int id);

    void updateConsumer(int id, Consumer consumer);

    Consumer getConsumerById(int id);

    List<Consumer> getAllConsumers();

    Consumer login(String email, String password);
}
