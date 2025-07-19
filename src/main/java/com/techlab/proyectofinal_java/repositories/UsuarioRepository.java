package com.techlab.proyectofinal_java.repositories;

import com.techlab.proyectofinal_java.models.usuario.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UserInfo, Integer> {}