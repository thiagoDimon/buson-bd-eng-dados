package com.faker.geraDados.entity;

import com.faker.geraDados.enums.EnumAssociacaoSituacao;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "associacaos")
public class Associacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cnpj")
    private Long cnpj;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "endereco", nullable = false)
    private String endereco;

    @Column(name = "situacao", nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumAssociacaoSituacao situacao;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public Associacao() {
    }

    public Associacao(Long id, Long cnpj, String nome, String endereco, EnumAssociacaoSituacao situacao, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.cnpj = cnpj;
        this.nome = nome;
        this.endereco = endereco;
        this.situacao = situacao;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getCnpj() {
        return cnpj;
    }

    public void setCnpj(Long cnpj) {
        this.cnpj = cnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public EnumAssociacaoSituacao getSituacao() {
        return situacao;
    }

    public void setSituacao(EnumAssociacaoSituacao situacao) {
        this.situacao = situacao;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
