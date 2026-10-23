package br.ulbra.estoque;

import br.ulbra.estoque.modelo.Produto;
import br.ulbra.estoque.repositorio.ProdutoRepositoryEmMemoria;
import br.ulbra.estoque.servico.ProdutoService;

public class Main {

    public static void main(String[] args) {
        ProdutoService service = new ProdutoService(new ProdutoRepositoryEmMemoria());

        Produto caneta = service.cadastrar(new Produto("Caneta", 2.50, 100));
        System.out.println("Cadastrado: " + caneta.getNome() + " (id " + caneta.getId() + ")");

        service.darBaixa(caneta.getId(), 30);
        System.out.println("Estoque depois da baixa: " + service.buscarPorId(caneta.getId()).getEstoque());

        try {
            service.darBaixa(caneta.getId(), 500);
        } catch (IllegalArgumentException erro) {
            System.out.println("Erro esperado: " + erro.getMessage());
        }
    }
}
