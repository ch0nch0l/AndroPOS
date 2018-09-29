package me.chonchol.androposweb.repository;

import me.chonchol.androposweb.entity.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Integer> {

}
