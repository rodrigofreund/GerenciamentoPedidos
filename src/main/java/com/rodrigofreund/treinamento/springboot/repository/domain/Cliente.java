package com.rodrigofreund.treinamento.springboot.repository.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;


@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Length(min = 2, max = 30, message = "O tamanho do nome  deve ser entre {min} e {max} caracteres")
    private String nome;

    @NotNull
    @Length(min = 2, max = 300, message = "O tamanho do endereço deve ser entre {min} e {max} caracteres")
    private String endereco;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pedido> pedidos;
    
    public Cliente(long idClienteFernando, @NotNull(message = "Nome não pode ser vazio") String nome, String endereco) {
        this.id = idClienteFernando;
        this.nome = nome;
        this.endereco = endereco;
        this.pedidos = new ArrayList<Pedido>();
    }
    
    public Cliente() {}
    
    public void adicionaPedido(Pedido pedido) {
        if(pedido == null) {
            throw new RuntimeException("Pedido não pode ser nulo");
        }
        pedidos.add(pedido);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

}
