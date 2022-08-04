package fr.xibalba.axiumwebsite.api.repositories;

import fr.xibalba.axiumwebsite.api.tables.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);
}