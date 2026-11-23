# Bolonha e Milano

Jogo de estratégia econômica medieval em Java, construído em etapas
incrementais para o projeto do 4º bimestre (SPOTDES).

## Etapas concluídas

- **Etapa 1:** estrutura inicial, classes `Main` e `Menu`.
- **Etapa 2:** classes `Jogador` e `Cidade`, "Novo jogo" e "Ver status da cidade".

## Como compilar e executar

Passo 1: dar **git clone https://github.com/JeanCFreitas/projeto-bolonha-e-milano-SPOTDES.git**
Passo 2: abrir **Visual Studio Code**
Passo 3: dar **Ctrl + Alt + N**

## Menu

```
1 - Novo jogo
2 - Ver status da cidade
3 - Sobre o jogo
0 - Sair
```

## Estrutura do repositório

| Pasta | Conteúdo |
|---|---|
| `src/bolonhaemilano/` | Código-fonte Java (pacote `bolonhaemilano`) |
| `uml/` | Diagrama de classes (cumulativo, `.puml`) |
| `casos-de-uso/` | Diagrama de casos de uso (cumulativo, `.puml`) |

Os diagramas estão em PlantUML. Para visualizar, use plantuml.com ou a
extensão PlantUML do VS Code.

## Classes

- `Main`: ponto de entrada; cria o `Menu` e chama `executar()`.
- `Menu`: exibe as opções, lê e valida a entrada e controla o laço principal.
- `Jogador`: nome e título de nobreza (começa como "Cavaleiro").
- `Cidade`: nome, ano atual (começa em 1400) e o jogador dono.
