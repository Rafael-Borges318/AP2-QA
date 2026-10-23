package br.ulbra.estoque.repositorio;

import br.ulbra.estoque.modelo.Produto;

import java.util.List;
import java.util.Optional;

/**
 * Contrato do repositório. O ProdutoService depende desta interface,
 * e não de uma implementação: por isso dá para trocar por um mock nos testes.
 */
public interface ProdutoRepository {

    Produto salvar(Produto produto);

    Optional<Produto> buscarPorId(Long id);

    List<Produto> listarTodos();
}
