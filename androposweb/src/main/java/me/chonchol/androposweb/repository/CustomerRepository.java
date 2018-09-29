package me.chonchol.androposweb.repository;

import me.chonchol.androposweb.entity.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customers, Integer> {

}
