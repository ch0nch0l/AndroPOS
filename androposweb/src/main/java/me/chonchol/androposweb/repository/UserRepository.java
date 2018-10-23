package me.chonchol.androposweb.repository;

import me.chonchol.androposweb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT * FROM USER A WHERE A.user_name = :user_name", nativeQuery = true)
    public User getUserByUserName(@Param("user_name") String name);
}
