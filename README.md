# AP2 — Qualidade e Testes de Software

Projeto Java de controle de estoque usado na **AP2**. Ele já funciona: o seu trabalho é **testá-lo**.

## 🎯 O que você precisa entregar

| Tipo | Quantidade | Onde criar | Como |
|---|---|---|---|
| **Testes unitários** | **4** | `src/test/java/br/ulbra/estoque/ProdutoServiceTest.java` | `ProdutoService` com o repositório **mockado** (Mockito) |
| **Testes de integração** | **2** | `src/test/java/br/ulbra/estoque/ProdutoIntegracaoTest.java` | `ProdutoService` com o repositório **real** (`ProdutoRepositoryEmMemoria`) |

Total: **6 testes**, todos passando (testes verdes, `BUILD SUCCESS`).

> O arquivo `ExemploMockitoTest.java` é só um modelo. **Ele não conta** e não pode ser copiado como um dos seus testes.

## Como rodar

**Pré-requisito: só o JDK 25.** Você **não precisa instalar o Maven**: o projeto vem com o *Maven Wrapper* (`mvnw`), que baixa o Maven sozinho na primeira vez.

Confira o Java no terminal (tem que aparecer a versão 25):

```bash
java -version
```

Baixe o projeto:

```bash
git clone https://github.com/Danielrsouza97/qa-ap2-api.git
cd qa-ap2-api
```

Rode os testes:

| Sistema | Comando |
|---|---|
| **Windows** (Prompt ou PowerShell) | `.\mvnw.cmd test` |
| **Mac / Linux** | `sh mvnw test` |

Na primeira execução ele baixa o Maven 3.9.16 (uns 10 MB) para a pasta `.m2` do seu usuário. Nas próximas, roda direto.

No final tem que aparecer `Tests run: ..., Failures: 0` e `BUILD SUCCESS`.

**No IntelliJ** dá para fazer tudo sem terminal: abra a pasta do projeto, espere o Maven baixar as dependências e clique na seta verde ao lado da classe de teste. Para ver o sistema funcionando, rode a classe `Main`.

> Se aparecer `WARNING: A Java agent has been loaded dynamically`, pode ignorar: é só um aviso do Mockito, não é erro.

### Se der erro

| Mensagem | O que fazer |
|---|---|
| `java não é reconhecido` / `java: command not found` | O JDK não está instalado ou não está no `PATH`. Instale o JDK 25 e abra um terminal novo |
| `The JAVA_HOME environment variable is not defined correctly` | A variável `JAVA_HOME` aponta para uma pasta errada. Ela deve apontar para a pasta do JDK (a que contém `bin\java.exe`), não para a `bin` |
| `release version 25 not supported` | O Java do terminal é mais antigo que o 25. Confira com `java -version` |
| No IntelliJ: `Unable to create injector` ... `No implementation for ArtifactHandlerManager` | Atualize o projeto (`git pull`) e clique em **Reload All Maven Projects** (ícone do Maven, à direita). Se continuar: **Settings → Build Tools → Maven → Maven home path → Bundled (Maven 3)** |

## Regras de negócio (`ProdutoService`)

- **R1** — O nome do produto é obrigatório.
- **R2** — O preço deve ser maior que zero.
- **R3** — O estoque inicial não pode ser negativo.
- **R4** — A baixa de estoque exige quantidade maior que zero.
- **R5** — Não é possível dar baixa de mais unidades do que existem em estoque.
- **R6** — Buscar um produto que não existe lança `ProdutoNaoEncontradoException`.

Regras R1 a R5 violadas lançam `IllegalArgumentException`.

## Estrutura

```
src/main/java/br/ulbra/estoque/
├── Main.java                                  ← demonstração
├── modelo/Produto.java                        ← o produto
├── repositorio/ProdutoRepository.java         ← interface (é ela que você mocka)
├── repositorio/ProdutoRepositoryEmMemoria.java ← implementação real, em memória
├── servico/ProdutoService.java                ← as regras R1 a R6
└── servico/ProdutoNaoEncontradoException.java
```
