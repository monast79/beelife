package ru.crimea.beelife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.crimea.beelife.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role getRoleByName(String name);

}
