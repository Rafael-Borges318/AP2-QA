package br.ulbra.estoque;

import br.ulbra.estoque.modelo.Produto;
import br.ulbra.estoque.repositorio.ProdutoRepository;
import br.ulbra.estoque.servico.ProdutoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * EXEMPLO de teste unitário com Mockito — NÃO conta para a AP2.
 * Use como modelo de sintaxe para escrever os seus.
 */
@ExtendWith(MockitoExtension.class)
class ExemploMockitoTest {

    @Mock
    private ProdutoRepository repository;

    @Test
    @DisplayName("Exemplo: listar devolve exatamente o que o repositório (mock) devolve")
    void listarDevolveOQueORepositorioDevolve() {
        ProdutoService service = new ProdutoService(repository);
        List<Produto> produtosDoMock = List.of(new Produto("Caneta", 2.50, 100));
        when(repository.listarTodos()).thenReturn(produtosDoMock);

        List<Produto> resultado = service.listar();

        assertEquals(1, resultado.size());
        verify(repository).listarTodos();
    }
}
