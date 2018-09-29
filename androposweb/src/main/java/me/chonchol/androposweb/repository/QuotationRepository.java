package me.chonchol.androposweb.repository;

import me.chonchol.androposweb.entity.Quotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuotationRepository extends JpaRepository<Quotation, Integer> {

}
