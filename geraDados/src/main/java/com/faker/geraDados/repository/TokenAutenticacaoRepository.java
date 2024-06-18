package com.faker.geraDados.repository;

import com.faker.geraDados.entity.TokenAutenticacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenAutenticacaoRepository extends JpaRepository<TokenAutenticacao, Long> {
}

