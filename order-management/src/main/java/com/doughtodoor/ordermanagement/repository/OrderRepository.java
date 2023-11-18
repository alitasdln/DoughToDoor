package com.doughtodoor.ordermanagement.repository;

import com.doughtodoor.ordermanagement.model.Order;
import io.hypersistence.utils.spring.repository.BaseJpaRepository;
import io.hypersistence.utils.spring.repository.BaseJpaRepositoryImpl;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


public interface OrderRepository extends BaseJpaRepository<Order, Long> {

}
