package br.ulbra.estoque;

import br.ulbra.estoque.modelo.Produto;
import br.ulbra.estoque.repositorio.ProdutoRepository;
import br.ulbra.estoque.servico.ProdutoNaoEncontradoException;
import br.ulbra.estoque.servico.ProdutoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository repository;

    @InjectMocks
    private ProdutoService service;

    @Test
    @DisplayName("Cadastrar com dados válidos chama repository.salvar e devolve o produto salvo")
    void cadastrarComDadosValidosSalvaNoRepositorio() {
        //Aqui tem dois objetos diferentes. Um salva e outro cria(nao no banco, mas sim ao que foi atribuido)
        Produto novo = new Produto("Caneta", 2.50, 100);
        Produto salvo = new Produto("Caneta", 2.50, 100);
        salvo.setId(1L);
        // aqui configura o comportamento do Mockito
        when(repository.salvar(novo)).thenReturn(salvo);

        Produto resultado = service.cadastrar(novo);

        assertSame(salvo, resultado);
        verify(repository).salvar(novo);
        //depois disso o mokito nao salva de verdade, ele devolve o objeto
    }

    @Test
    @DisplayName("R2: cadastrar com preço zero lança IllegalArgumentException e não acessa o repositório")
    void cadastrarComPrecoZeroLancaExcecaoENaoSalva() {
        //cria um produto inválido, pois o preço é zero
        Produto invalido = new Produto("Caneta", 0, 10);

        //verifica se lança a excecao esperada
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.cadastrar(invalido));
        //ve se a mensagem da excecao ta certa
        assertEquals("Preço deve ser maior que zero", ex.getMessage());
        verifyNoInteractions(repository);
    }

    @Test
    @DisplayName("R6: buscar id inexistente (repositório devolve vazio) lança ProdutoNaoEncontradoException")
    void buscarPorIdInexistenteLancaProdutoNaoEncontrado() {
        //configura para não encontrar nenhum produto com esse id
        when(repository.buscarPorId(99L)).thenReturn(Optional.empty());

        //ve se sai no jeito
        ProdutoNaoEncontradoException ex = assertThrows(ProdutoNaoEncontradoException.class,
                () -> service.buscarPorId(99L));
        //ve se a mensagem ta certa
        assertEquals("Produto 99 não encontrado", ex.getMessage());
    }

    @Test
    @DisplayName("R5: dar baixa maior que o estoque lança 'Estoque insuficiente' e não salva")
    void darBaixaMaiorQueEstoqueLancaExcecaoENaoSalva() {
        //produto com estoque 5
        Produto existente = new Produto("Caneta", 2.50, 5);
        existente.setId(1L);
        //retornar o produto com o id
        when(repository.buscarPorId(1L)).thenReturn(Optional.of(existente));
        //tenta tirar o tanto que nao existe
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.darBaixa(1L, 6));
        //ve se ta certa a mensagem
        assertEquals("Estoque insuficiente", ex.getMessage());
        //se continua com 53
        assertEquals(5, existente.getEstoque());
        verify(repository, never()).salvar(any());
    }
}
