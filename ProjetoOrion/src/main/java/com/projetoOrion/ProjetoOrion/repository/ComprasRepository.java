package com.projetoOrion.ProjetoOrion.repository;


import com.projetoOrion.ProjetoOrion.model.Compras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComprasRepository extends JpaRepository<Compras, Long> {
}
