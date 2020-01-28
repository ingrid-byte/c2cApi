package br.com.c2c.api.controller;

import br.com.c2c.api.entity.Produto;
import br.com.c2c.api.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("produtos")
public class ProdutoController {
    ProdutoService produtoService;

    @Autowired
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<Produto> adicionaProduto(@RequestBody Produto produto, UriComponentsBuilder uriBuilder){
        Produto produtoSalvo = produtoService.adicionaProduto(produto);
        //Uri para localizar o produto inserido
        URI location = uriBuilder.path("produtos/{produto}")
                .buildAndExpand(produtoSalvo.getId())
                .toUri();

        return ResponseEntity.created(location).body(produto);
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listaProdutos(){
        return ResponseEntity.ok(produtoService.listaProdutos());
    }

    @GetMapping("/{produto}")
    public ResponseEntity<Produto> buscaProdutoPorId(@PathVariable("produto") Integer id){
        Optional<Produto> optionalProduto = produtoService.buscaProdutoPorId(id);
        if(optionalProduto.isPresent()){
            return ResponseEntity.ok(optionalProduto.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{produto}")
    public ResponseEntity<Produto> atualizaProduto(@PathVariable("produto") Integer id, @RequestBody Produto produto){
        Optional<Produto> optionalProduto = produtoService.atualizaProduto(id, produto);
        if(optionalProduto.isPresent()){
            return ResponseEntity.ok(optionalProduto.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{produto}")
    private ResponseEntity deletaProduto(@PathVariable("produto") Integer id){
        if(produtoService.deletaProduto(id)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
