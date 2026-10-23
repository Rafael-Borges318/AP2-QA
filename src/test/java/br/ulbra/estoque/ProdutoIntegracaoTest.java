package br.ulbra.estoque;

import br.ulbra.estoque.modelo.Produto;
import br.ulbra.estoque.repositorio.ProdutoRepositoryEmMemoria;
import br.ulbra.estoque.servico.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class ProdutoIntegracaoTest {

    private ProdutoService service;

    @BeforeEach
    void setUp() {
        service = new ProdutoService(new ProdutoRepositoryEmMemoria());
    }

    @Test
    @DisplayName("Cadastrar dois produtos gera ids sequenciais, permite buscar por id e listar na ordem de inserção")
    void cadastrarEBuscarGeraIdsSequenciaisEPersiste() {
        //cadastra dois produtos
        Produto caneta = service.cadastrar(new Produto("Caneta", 2.50, 100));
        Produto caderno = service.cadastrar(new Produto("Caderno", 15.00, 30));
        //se os ids foram gerados em sequência
        assertEquals(1L, caneta.getId());
        assertEquals(2L, caderno.getId());
        //so verifica
        assertSame(caderno, service.buscarPorId(2L));

        List<Produto> todos = service.listar();
        //confere se tem dois produtos na lista
        assertEquals(2, todos.size());
        //se os produtos estão na ordem de cadastro
        assertEquals("Caneta", todos.get(0).getNome());
        assertEquals("Caderno", todos.get(1).getNome());
    }

    @Test
    @DisplayName("Dar baixa de 4 unidades em estoque de 10 persiste estoque 6 ao buscar novamente")
    void darBaixaAtualizaEstoquePersistidoNoRepositorio() {
        Produto cadastrado = service.cadastrar(new Produto("Caneta", 2.50, 10));

        Produto retornado = service.darBaixa(cadastrado.getId(), 4);

        assertEquals(6, retornado.getEstoque());
        assertEquals(6, service.buscarPorId(cadastrado.getId()).getEstoque());
    }
}
