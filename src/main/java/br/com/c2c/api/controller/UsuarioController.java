package br.com.c2c.api.controller;

import br.com.c2c.api.dto.EmailValidoDTO;
import br.com.c2c.api.entity.Usuario;
import br.com.c2c.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {
    UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Usuario> adicionaProduto(@RequestBody Usuario usuario, UriComponentsBuilder uriBuilder){
        Usuario usuarioSalvo = usuarioService.adicionaUsuario(usuario);
        //Uri para localizar o produto inserido
        URI location = uriBuilder.path("usuarios/{usuario}")
                .buildAndExpand(usuarioSalvo.getId())
                .toUri();

        return ResponseEntity.created(location).body(usuario);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listaProdutos(){
        return ResponseEntity.ok(usuarioService.listaUsuario());
    }

    @GetMapping("/{usuario}")
    public ResponseEntity<Usuario> buscaProdutoPorId(@PathVariable("usuario") Integer id){
        Optional<Usuario> optionalUsuario = usuarioService.buscaUsuarioPorId(id);
        if(optionalUsuario.isPresent()){
            return ResponseEntity.ok(optionalUsuario.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{usuario}/email/{email}/valido")
    public ResponseEntity<EmailValidoDTO> buscaProdutoPorId(@PathVariable("usuario") Integer id,
                                                     @PathVariable("email") String email){
        EmailValidoDTO emailValidoDTO = usuarioService.validaEmail(id, email);

        return ResponseEntity.ok(emailValidoDTO);
    }

    @PutMapping("/{usuario}")
    public ResponseEntity<Usuario> atualizaProduto(@PathVariable("usuario") Integer id, @RequestBody Usuario usuario){
        Optional<Usuario> optionalUsuario = usuarioService.atualizaUsuario(id, usuario);
        if(optionalUsuario.isPresent()){
            return ResponseEntity.ok(optionalUsuario.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{usuario}")
    private ResponseEntity deletaProduto(@PathVariable("usuario") Integer id){
        if(usuarioService.deletaUsuario(id)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
