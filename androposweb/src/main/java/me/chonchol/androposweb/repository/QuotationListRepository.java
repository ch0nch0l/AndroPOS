package me.chonchol.androposweb.repository;

import me.chonchol.androposweb.entity.QuotationList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuotationListRepository extends JpaRepository<QuotationList, Integer> {


}
