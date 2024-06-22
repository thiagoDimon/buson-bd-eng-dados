package com.faker.geraDados.entity;

import com.faker.geraDados.enums.EnumParametrosLiberaAlteracaoDadosPessoais;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "parametros")
public class Parametro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "associacao_id", nullable = false)
    private Associacao associacao;

    @Column(name = "valor1", nullable = false)
    private int valor1;

    @Column(name = "valor2", nullable = false)
    private int valor2;

    @Column(name = "valor3", nullable = false)
    private int valor3;

    @Column(name = "valor4", nullable = false)
    private int valor4;

    @Column(name = "valor5", nullable = false)
    private int valor5;

    @Column(name = "valor6", nullable = false)
    private int valor6;

    @Column(name = "valor_multa", nullable = false)
    private int valorMulta;

    @Column(name = "dia_vencimento", nullable = false)
    private int diaVencimento;

    @Column(name = "dia_abertura_pagamentos", nullable = false)
    private int diaAberturaPagamentos;

    @Column(name = "dias_tolerancia_multa", nullable = false)
    private int diasToleranciaMulta;

    @Enumerated(EnumType.STRING)
    @Column(name = "libera_alteracao_dados_pessoais", nullable = false)
    private EnumParametrosLiberaAlteracaoDadosPessoais liberaAlteracaoDadosPessoais;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public Parametro() {
    }

    public Parametro(Long id, Associacao associacao, int valor1, int valor2, int valor3, int valor4, int valor5, int valor6, int valorMulta, int diaAberturaPagamentos, int diaVencimento, int diasToleranciaMulta, LocalDateTime createdAt, EnumParametrosLiberaAlteracaoDadosPessoais liberaAlteracaoDadosPessoais, LocalDateTime updatedAt) {
        this.id = id;
        this.associacao = associacao;
        this.valor1 = valor1;
        this.valor2 = valor2;
        this.valor3 = valor3;
        this.valor4 = valor4;
        this.valor5 = valor5;
        this.valor6 = valor6;
        this.valorMulta = valorMulta;
        this.diaAberturaPagamentos = diaAberturaPagamentos;
        this.diaVencimento = diaVencimento;
        this.diasToleranciaMulta = diasToleranciaMulta;
        this.createdAt = createdAt;
        this.liberaAlteracaoDadosPessoais = liberaAlteracaoDadosPessoais;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Associacao getAssociacao() {
        return associacao;
    }

    public void setAssociacao(Associacao associacao) {
        this.associacao = associacao;
    }

    public int getValor1() {
        return valor1;
    }

    public void setValor1(int valor1) {
        this.valor1 = valor1;
    }

    public int getValor2() {
        return valor2;
    }

    public void setValor2(int valor2) {
        this.valor2 = valor2;
    }

    public int getValor3() {
        return valor3;
    }

    public void setValor3(int valor3) {
        this.valor3 = valor3;
    }

    public int getValor4() {
        return valor4;
    }

    public void setValor4(int valor4) {
        this.valor4 = valor4;
    }

    public int getValor5() {
        return valor5;
    }

    public void setValor5(int valor5) {
        this.valor5 = valor5;
    }

    public int getValor6() {
        return valor6;
    }

    public void setValor6(int valor6) {
        this.valor6 = valor6;
    }

    public int getValorMulta() {
        return valorMulta;
    }

    public void setValorMulta(int valorMulta) {
        this.valorMulta = valorMulta;
    }

    public int getDiaVencimento() {
        return diaVencimento;
    }

    public void setDiaVencimento(int diaVencimento) {
        this.diaVencimento = diaVencimento;
    }

    public int getDiaAberturaPagamentos() {
        return diaAberturaPagamentos;
    }

    public void setDiaAberturaPagamentos(int diaAberturaPagamentos) {
        this.diaAberturaPagamentos = diaAberturaPagamentos;
    }

    public int getDiasToleranciaMulta() {
        return diasToleranciaMulta;
    }

    public void setDiasToleranciaMulta(int diasToleranciaMulta) {
        this.diasToleranciaMulta = diasToleranciaMulta;
    }

    public EnumParametrosLiberaAlteracaoDadosPessoais getLiberaAlteracaoDadosPessoais() {
        return liberaAlteracaoDadosPessoais;
    }

    public void setLiberaAlteracaoDadosPessoais(EnumParametrosLiberaAlteracaoDadosPessoais liberaAlteracaoDadosPessoais) {
        this.liberaAlteracaoDadosPessoais = liberaAlteracaoDadosPessoais;
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
