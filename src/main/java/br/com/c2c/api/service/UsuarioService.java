package br.com.c2c.api.service;

import br.com.c2c.api.dto.EmailValidoDTO;
import br.com.c2c.api.entity.Produto;
import br.com.c2c.api.entity.Usuario;
import br.com.c2c.api.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario adicionaUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listaUsuario(){
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscaUsuarioPorId(Integer id){
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> atualizaUsuario(Integer id, Usuario usuario){
        Optional<Usuario> optionalUsuario = buscaUsuarioPorId(id);
        if(!optionalUsuario.isPresent()){
            return optionalUsuario;
        }
        //Copio as propriedades de produto para o get do optiona, menos o atributo ID
        BeanUtils.copyProperties(usuario, optionalUsuario.get(), "id");

        return Optional.of(usuarioRepository.save(optionalUsuario.get()));
    }

    public boolean deletaUsuario(Integer id){
        if(usuarioRepository.existsById(id)){
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public EmailValidoDTO validaEmail(Integer id, String email){
        Usuario usuario = usuarioRepository.buscaUsuarioPorEmailEId(id, email);
        EmailValidoDTO emailValidoDTO = new EmailValidoDTO();
        emailValidoDTO.setEmail(email);
        if(usuario != null){
            emailValidoDTO.setValido(true);
        }
        return emailValidoDTO;
    }
}
