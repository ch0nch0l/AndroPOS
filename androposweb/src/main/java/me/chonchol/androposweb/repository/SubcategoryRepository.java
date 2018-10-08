package me.chonchol.androposweb.repository;

import me.chonchol.androposweb.entity.Category;
import me.chonchol.androposweb.entity.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Integer> {

    @Query(value = "SELECT * FROM SUBCATEGORY A WHERE A.cat_id = :cat_id", nativeQuery = true)
    public List<Subcategory> getSubCategoryByCatId(@Param("cat_id") Integer catId);

}
