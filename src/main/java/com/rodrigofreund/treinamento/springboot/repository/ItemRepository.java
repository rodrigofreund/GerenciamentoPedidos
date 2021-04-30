package com.rodrigofreund.treinamento.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rodrigofreund.treinamento.springboot.repository.domain.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}
