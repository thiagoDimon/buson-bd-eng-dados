package com.faker.geraDados.repository;

import com.faker.geraDados.entity.Associacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociacaoRepository extends JpaRepository<Associacao, Long> {
}
