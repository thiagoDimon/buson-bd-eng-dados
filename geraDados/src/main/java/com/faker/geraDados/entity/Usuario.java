package com.faker.geraDados.entity;

import com.faker.geraDados.enums.EnumTipoAcesso;
import com.faker.geraDados.enums.EnumUsuarioSituacao;
import com.faker.geraDados.enums.EnumUsuariosDiasUsoTransporte;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "telefone", nullable = false)
    private String telefone;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "matricula")
    private String matricula;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @ManyToOne
    @JoinColumn(name = "associacao_id")
    private Associacao associacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_acesso", nullable = false)
    private EnumTipoAcesso tipoAcesso;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao", nullable = false)
    private EnumUsuarioSituacao situacao;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public Usuario() {
    }

    public Usuario(Long id, String email, String nome, String telefone, String endereco, String matricula, Curso curso, Associacao associacao, EnumTipoAcesso tipoAcesso, String senha, EnumUsuarioSituacao situacao, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
        this.matricula = matricula;
        this.curso = curso;
        this.associacao = associacao;
        this.tipoAcesso = tipoAcesso;
        this.senha = senha;
        this.situacao = situacao;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long id() {
        return id;
    }

    public Usuario setId(Long id) {
        this.id = id;
        return this;
    }

    public String nome() {
        return nome;
    }

    public Usuario setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public String email() {
        return email;
    }

    public Usuario setEmail(String email) {
        this.email = email;
        return this;
    }

    public String endereco() {
        return endereco;
    }

    public Usuario setEndereco(String endereco) {
        this.endereco = endereco;
        return this;
    }

    public String telefone() {
        return telefone;
    }

    public Usuario setTelefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public Curso curso() {
        return curso;
    }

    public Usuario setCurso(Curso curso) {
        this.curso = curso;
        return this;
    }

    public String matricula() {
        return matricula;
    }

    public Usuario setMatricula(String matricula) {
        this.matricula = matricula;
        return this;
    }

    public Associacao associacao() {
        return associacao;
    }

    public Usuario setAssociacao(Associacao associacao) {
        this.associacao = associacao;
        return this;
    }

    public EnumTipoAcesso tipoAcesso() {
        return tipoAcesso;
    }

    public Usuario setTipoAcesso(EnumTipoAcesso tipoAcesso) {
        this.tipoAcesso = tipoAcesso;
        return this;
    }

    public String senha() {
        return senha;
    }

    public Usuario setSenha(String senha) {
        this.senha = senha;
        return this;
    }

    public EnumUsuarioSituacao situacao() {
        return situacao;
    }

    public Usuario setSituacao(EnumUsuarioSituacao situacao) {
        this.situacao = situacao;
        return this;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }

    public Usuario setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public LocalDateTime updatedAt() {
        return updatedAt;
    }

    public Usuario setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
