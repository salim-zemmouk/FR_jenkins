package net.bsfconsulting.bibliothequeapi.repository;

import net.bsfconsulting.bibliothequeapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
