package com.rodrigofreund.treinamento.springboot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rodrigofreund.treinamento.springboot.repository.domain.Cliente;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Long>{

}
