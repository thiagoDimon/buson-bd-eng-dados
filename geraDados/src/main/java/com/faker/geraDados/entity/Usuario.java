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
    
    @Column(name = "dias_uso_transporte", nullable = false)
    private String diasUsoTransporte;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public Usuario() {
    }

    public Usuario(Long id, String nome, String email, String telefone, String endereco, String matricula, Curso curso, Associacao associacao, EnumTipoAcesso tipoAcesso, String senha, EnumUsuarioSituacao situacao, String diasUsoTransporte, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.matricula = matricula;
        this.curso = curso;
        this.associacao = associacao;
        this.tipoAcesso = tipoAcesso;
        this.senha = senha;
        this.situacao = situacao;
        this.diasUsoTransporte = diasUsoTransporte;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Associacao getAssociacao() {
        return associacao;
    }

    public void setAssociacao(Associacao associacao) {
        this.associacao = associacao;
    }

    public EnumTipoAcesso getTipoAcesso() {
        return tipoAcesso;
    }

    public void setTipoAcesso(EnumTipoAcesso tipoAcesso) {
        this.tipoAcesso = tipoAcesso;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public EnumUsuarioSituacao getSituacao() {
        return situacao;
    }

    public void setSituacao(EnumUsuarioSituacao situacao) {
        this.situacao = situacao;
    }

    public String getDiasUsoTransporte() {
        return diasUsoTransporte;
    }

    public void setDiasUsoTransporte(String diasUsoTransporte) {
        this.diasUsoTransporte = diasUsoTransporte;
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
