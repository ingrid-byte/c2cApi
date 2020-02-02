package br.com.c2c.api.repository;

import br.com.c2c.api.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    @Query("SELECT usuario FROM Usuario usuario WHERE usuario.id = :id AND usuario.email = :email")
    Usuario buscaUsuarioPorEmailEId(@Param("id") Integer id, @Param("email") String email);
}
