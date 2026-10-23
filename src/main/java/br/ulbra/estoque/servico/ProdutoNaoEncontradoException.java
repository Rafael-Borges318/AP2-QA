package br.ulbra.estoque.servico;

public class ProdutoNaoEncontradoException extends RuntimeException {

    public ProdutoNaoEncontradoException(Long id) {
        super("Produto " + id + " não encontrado");
    }
}
