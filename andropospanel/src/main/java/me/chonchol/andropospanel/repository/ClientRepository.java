package me.chonchol.andropospanel.repository;

import me.chonchol.andropospanel.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    @Query(value = "SELECT * FROM CLIENT A WHERE A.client_db=:client_db", nativeQuery = true)
    Client getClientByDBName(@Param("client_db") String clientDBName);
}
