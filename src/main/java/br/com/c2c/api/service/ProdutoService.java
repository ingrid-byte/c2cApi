package br.com.c2c.api.service;

import br.com.c2c.api.entity.Produto;
import br.com.c2c.api.repository.ProdutoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    ProdutoRepository produtoRepository;
    //Autowired é semelhante ao (new), serve para injetar a repository
    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto adicionaProduto(Produto produto){
        return produtoRepository.save(produto);
    }

    public List<Produto> listaProdutos(){
        return produtoRepository.findAll();
    }

    public Optional<Produto> buscaProdutoPorId(Integer id){
        //optional é um recurso do java para evitar null pointer, tem um metodo isPresent para verificar se existe
        //e um get para pegar o objeto
        return produtoRepository.findById(id);
    }

    public Optional<Produto> atualizaProduto(Integer id, Produto produto){
        Optional<Produto> optionalProduto = buscaProdutoPorId(id);
        if(!optionalProduto.isPresent()){
            return optionalProduto;
        }
        //Copio as propriedades de produto para o get do optiona, menos o atributo ID
        BeanUtils.copyProperties(produto, optionalProduto.get(), "id");

        return Optional.of(produtoRepository.save(optionalProduto.get()));
    }

    public boolean deletaProduto(Integer id){
        if(produtoRepository.existsById(id)){
            produtoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
