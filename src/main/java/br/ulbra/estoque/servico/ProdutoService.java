package br.ulbra.estoque.servico;

import br.ulbra.estoque.modelo.Produto;
import br.ulbra.estoque.repositorio.ProdutoRepository;

import java.util.List;

/**
 * Regras de negócio do estoque.
 *
 * R1 - O nome do produto é obrigatório.
 * R2 - O preço deve ser maior que zero.
 * R3 - O estoque inicial não pode ser negativo.
 * R4 - A baixa de estoque exige quantidade maior que zero.
 * R5 - Não é possível dar baixa de mais unidades do que existem em estoque.
 * R6 - Buscar um produto que não existe lança ProdutoNaoEncontradoException.
 */
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public Produto cadastrar(Produto produto) {
        if (produto.getNome() == null || produto.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome do produto é obrigatório");
        }
        if (produto.getPreco() <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }
        if (produto.getEstoque() < 0) {
            throw new IllegalArgumentException("Estoque não pode ser negativo");
        }
        return repository.salvar(produto);
    }

    public Produto buscarPorId(Long id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));
    }

    public List<Produto> listar() {
        return repository.listarTodos();
    }

    public Produto darBaixa(Long id, int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        Produto produto = buscarPorId(id);
        if (quantidade > produto.getEstoque()) {
            throw new IllegalArgumentException("Estoque insuficiente");
        }
        produto.setEstoque(produto.getEstoque() - quantidade);
        return repository.salvar(produto);
    }
}
