package fr.xibalba.axiumwebsite.api.controllers;

import fr.xibalba.axiumwebsite.api.repositories.RoleRepository;
import fr.xibalba.axiumwebsite.api.tables.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping("")
    public List<Role> infos(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "id", required = false) Integer id) {

        if (name != null && roleRepository.findByName(name) != null) {

            return List.of(roleRepository.findByName(name));
        } else if (id != null && roleRepository.findById(id).isPresent()) {

            return List.of(roleRepository.findById(id).get());
        } else {

            return roleRepository.findAll();
        }
    }
}
