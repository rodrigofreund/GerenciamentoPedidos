package com.rodrigofreund.treinamento.springboot.repository.domain;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = true)
    private Cliente cliente;

    @ManyToMany
    @Cascade(CascadeType.MERGE)
    private List<Item> itens;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate data;

    @Min(1)
    private Double valorTotal;

    public Pedido(Long idPedido, Cliente cliente, List<Item> itens, Double valor) {
        this.id = idPedido;
        this.itens = itens;
        this.cliente = cliente;
        this.itens = itens;
        this.valorTotal = valor;
    }
    
    public Pedido() {}
}
