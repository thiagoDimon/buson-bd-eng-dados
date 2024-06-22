package com.faker.geraDados.entity;

import com.faker.geraDados.enums.EnumInstituicaoSituacao;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "instituicaos")
public class Instituicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "endereco", nullable = false)
    private String endereco;

    @Column(name = "situacao", nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumInstituicaoSituacao situacao;

    @ManyToOne
    @JoinColumn(name = "associacao_id", nullable = false)
    private Associacao associacao;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public Instituicao() {
        // Default constructor
    }

    public Instituicao(Long id, String nome, String endereco, EnumInstituicaoSituacao situacao, Associacao associacao, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.situacao = situacao;
        this.associacao = associacao;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public EnumInstituicaoSituacao getSituacao() {
        return situacao;
    }

    public void setSituacao(EnumInstituicaoSituacao situacao) {
        this.situacao = situacao;
    }

    public Associacao getAssociacao() {
        return associacao;
    }

    public void setAssociacao(Associacao associacao) {
        this.associacao = associacao;
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
