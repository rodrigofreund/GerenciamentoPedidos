package com.rodrigofreund.treinamento.springboot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rodrigofreund.treinamento.springboot.repository.domain.Item;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {

}
