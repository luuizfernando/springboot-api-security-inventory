package project.inventory.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import project.inventory.domain.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

}