package ua.skuhtin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.skuhtin.model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    @Query("SELECT entity FROM Users entity WHERE entity.login = :login")
    Users getUserByLogin(@Param("login") String login);
}
