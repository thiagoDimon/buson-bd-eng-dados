package com.faker.geraDados.entity;

import com.faker.geraDados.enums.EnumPagamentoTipo;
import com.faker.geraDados.enums.EnumSituacaoPagamento;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "pagamentos")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private EnumPagamentoTipo tipo;

    @Column(name = "valor", nullable = false)
    private int valor;

    @Column(name = "multa")
    private int multa;

    @Column(name = "data_vencimento")
    private LocalDateTime dataVencimento;

    @Column(name = "data_pagamento")
    private LocalDateTime dataPagamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao", nullable = false)
    private EnumSituacaoPagamento situacaoPagamento;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public Pagamento() {
    }

    public Pagamento(Long id, Usuario usuario, EnumPagamentoTipo tipo, int valor, int multa, LocalDateTime dataVencimento, LocalDateTime dataPagamento, EnumSituacaoPagamento situacaoPagamento, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.usuario = usuario;
        this.tipo = tipo;
        this.valor = valor;
        this.multa = multa;
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento;
        this.situacaoPagamento = situacaoPagamento;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public EnumPagamentoTipo getTipo() {
        return tipo;
    }

    public void setTipo(EnumPagamentoTipo tipo) {
        this.tipo = tipo;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getMulta() {
        return multa;
    }

    public void setMulta(int multa) {
        this.multa = multa;
    }

    public LocalDateTime getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDateTime dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public EnumSituacaoPagamento getSituacaoPagamento() {
        return situacaoPagamento;
    }

    public void setSituacaoPagamento(EnumSituacaoPagamento situacaoPagamento) {
        this.situacaoPagamento = situacaoPagamento;
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
