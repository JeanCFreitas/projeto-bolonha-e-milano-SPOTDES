# Bolonha e Milano

Jogo de estrategia economica medieval em Java, jogado no console.
De 1 a 4 governantes (Bolonha, Milano, Florenca e Veneza) disputam o trono
ao longo de rodadas anuais, de 1400 a 1410.

## Requisitos

- JDK 8 ou superior (`javac` e `java` no PATH)

## Compilar e executar

```bash
javac *.java
java Main
```

## Testes automatizados

```bash
java Testes
```

Imprime `[OK]` ou `[FALHA]` por verificacao e, no fim,
`Aprovados: N | Reprovados: M`. Se houver falha, termina com codigo 1.
Os testes seguem o padrao preparar, executar, verificar, e cobrem o caminho
feliz e os casos de erro de cada classe de regra.

## Menu principal

| Opcao | Acao |
|-------|------|
| 1 | Novo jogo |
| 2 | Sobre o jogo |
| 3 | Ver status da cidade |
| 4 | Mercado |
| 5 | Agricultura |
| 6 | Governo |
| 7 | Construcoes |
| 8 | Ver classificacao (prestigio de cada cidade e meta do proximo titulo) |
| 9 | Encerrar turno |
| 0 | Sair |

As opcoes 3 a 9 exigem uma partida em andamento.

## Como jogar

1. **Novo jogo**: escolha de 1 a 4 jogadores (valor invalido vira 2) e informe
   o nome de cada um. Cada jogador governa uma cidade: Bolonha, Milano,
   Florenca e Veneza, nessa ordem.
2. Em cada turno, o jogador pode usar **Mercado**, **Agricultura**, **Governo**
   e **Construcoes**, consultar a **classificacao** e, por fim, **encerrar o turno**.
3. Colher e arrecadar impostos valem **uma vez por ano** por cidade.
4. Quando o ultimo jogador encerra o turno, o ano fecha: a Nobreza calcula o
   prestigio e promove o governante (no maximo um titulo por ano).
5. Vence quem virar Rei primeiro ou, ao fim de 1410, o melhor colocado
   (titulo mais alto; empate, maior prestigio).

## Regras principais

| Item | Regra |
|------|-------|
| Recursos iniciais | 1000 florins, 10000 hectares, 500 graos, 200 servos, 0 soldados, 0 construcoes |
| Configuracoes iniciais | taxa de imposto 10%, justica Moderada |
| Mercado (graos) | compra a 2 florins, venda a 1 florim |
| Mercado (terras) | compra a 10 florins por hectare, venda a 5 |
| Colheita | hectares = min(terras, servos x 5); sementes = hectares / 2; producao = hectares x 2 |
| Alimentos | distribuir de 20% a 80% dos graos; falta comida, a populacao cai; sobra, cresce 5% |
| Impostos | servos x taxa / 100 x fator de justica (Justa 0,8 / Moderada 1,0 / Severa 1,3); taxa de 0 a 50% |
| Soldado | 5 florins + 1 servo (os dois sao validados antes de debitar) |
| Construcao | 20 florins |
| Prestigio | construcoes x 10 + soldados x 2 + servos + terras / 100 (florins nao contam) |
| Titulos | Cavaleiro, Barao, Conde, Marques, Duque, Rei |

### Prestigio minimo para cada titulo

| Titulo | Prestigio minimo |
|--------|------------------|
| Barao | 400 |
| Conde | 600 |
| Marques | 900 |
| Duque | 1300 |
| Rei | 1800 |

A cidade inicial vale 300 de prestigio (200 servos + 100 pelas terras).
Os valores nao definidos no enunciado (precos do mercado e prestigio minimo)
sao constantes no topo de `Mercado` e `Nobreza`.

## Estrutura

| Classe | Responsabilidade |
|--------|------------------|
| `Main` | ponto de entrada: cria o `Menu` e chama `executar()` |
| `Menu` | unica classe que le o teclado e imprime; so delega, sem regra de jogo |
| `Jogador` | nome e titulo de nobreza (conhece a ordem dos titulos) |
| `Cidade` | recursos, configuracoes de governo e indicadores anuais |
| `Mercado` | compra e venda de graos e terras |
| `Agricultura` | colheita e distribuicao de alimentos |
| `Governo` | impostos e justica |
| `Construcoes` | recrutamento de soldados e construcao de edificios |
| `Nobreza` | prestigio e promocao (decide quando promover) |
| `Partida` | turnos, ano, fim de jogo e vencedor |
| `Testes` | testes automatizados, com `main` proprio |

As classes de regra (`Mercado`, `Agricultura`, `Governo`, `Construcoes` e
`Nobreza`) nao guardam nenhuma `Cidade`: ela chega por parametro.
Nos recursos da `Cidade`, `remover*` devolve `false` se faltar saldo e nunca
deixa o valor negativo.

## Diagramas

- `uml/classes.puml`: diagrama de classes
- `uml/sequencia-encerrar-turno.puml`: diagrama de sequencia do fim de turno e do ano
- `casos-de-uso/casos-de-uso.puml`: diagrama de casos de uso (Jogador e Desenvolvedor)

Para visualizar, cole o conteudo em https://www.plantuml.com/plantuml ou use a
extensao PlantUML no VS Code.