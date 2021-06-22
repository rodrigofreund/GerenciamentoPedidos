package com.rodrigofreund.treinamento.springboot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.rodrigofreund.treinamento.springboot.repository.domain.Item;

@Repository
@RepositoryRestResource(collectionResourceRel = "itens", path="itens")
public interface ItemRepository extends CrudRepository<Item, Long> {

}
