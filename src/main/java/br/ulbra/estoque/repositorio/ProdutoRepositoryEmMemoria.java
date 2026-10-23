package br.ulbra.estoque.repositorio;

import br.ulbra.estoque.modelo.Produto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implementação real do repositório: guarda os produtos em memória (sem banco).
 */
public class ProdutoRepositoryEmMemoria implements ProdutoRepository {

    private final Map<Long, Produto> produtos = new LinkedHashMap<>();
    private long proximoId = 1;

    @Override
    public Produto salvar(Produto produto) {
        if (produto.getId() == null) {
            produto.setId(proximoId);
            proximoId++;
        }
        produtos.put(produto.getId(), produto);
        return produto;
    }

    @Override
    public Optional<Produto> buscarPorId(Long id) {
        return Optional.ofNullable(produtos.get(id));
    }

    @Override
    public List<Produto> listarTodos() {
        return new ArrayList<>(produtos.values());
    }
}
