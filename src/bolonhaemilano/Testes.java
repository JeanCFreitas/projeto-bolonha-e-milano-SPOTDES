import java.util.List;

public class Testes {

    private static int aprovados = 0;
    private static int reprovados = 0;

    public static void main(String[] args) {
        testarCidade();
        testarMercado();
        testarAgricultura();
        testarGoverno();
        testarConstrucoes();
        testarJogadorENobreza();
        testarPartida();

        System.out.println();
        System.out.println("Aprovados: " + aprovados + " | Reprovados: " + reprovados);
        if (reprovados > 0) {
            System.exit(1);
        }
    }

    // ---------- Apoio ----------

    private static void verificar(String descricao, boolean condicao) {
        if (condicao) {
            aprovados++;
            System.out.println("[OK] " + descricao);
        } else {
            reprovados++;
            System.out.println("[FALHA] " + descricao);
        }
    }

    private static void titulo(String grupo) {
        System.out.println();
        System.out.println("=== " + grupo + " ===");
    }

    // Sempre com os valores iniciais
    private static Cidade novaCidade() {
        return new Cidade("Bolonha", new Jogador("Teste"));
    }

    // ---------- Cidade ----------

    private static void testarCidade() {
        titulo("Cidade");

        // preparar + verificar: valores iniciais
        Cidade c = novaCidade();
        verificar("cidade comeca com 1000 florins", c.getFlorins() == 1000);
        verificar("cidade comeca com 10000 hectares", c.getTerras() == 10000);
        verificar("cidade comeca com 500 graos", c.getGraos() == 500);
        verificar("cidade comeca com 200 servos", c.getServos() == 200);
        verificar("cidade comeca com 0 soldados e 0 construcoes",
                c.getSoldados() == 0 && c.getConstrucoes() == 0);
        verificar("taxa inicial 10% e justica Moderada",
                c.getTaxaImposto() == 10 && "Moderada".equals(c.getNivelJustica()));
        verificar("ano inicial 1400", c.getAnoAtual() == 1400);

        // avancarAno soma 1 e libera os indicadores
        c = novaCidade();
        c.marcarColheitaFeita();
        c.marcarImpostosArrecadados();
        verificar("indicadores marcados antes de avancar",
                c.isColheitaFeita() && c.isImpostosArrecadados());
        c.avancarAno();
        verificar("avancarAno soma 1 ao ano", c.getAnoAtual() == 1401);
        verificar("avancarAno libera a colheita", !c.isColheitaFeita());
        verificar("avancarAno libera os impostos", !c.isImpostosArrecadados());

        // adicionar funciona
        c = novaCidade();
        c.adicionarFlorins(50);
        verificar("adicionarFlorins soma ao saldo", c.getFlorins() == 1050);

        // erros: remover* acima do saldo devolve false e nao altera
        c = novaCidade();
        verificar("removerFlorins acima do saldo devolve false", !c.removerFlorins(1001));
        verificar("... e nao altera os florins", c.getFlorins() == 1000);
        verificar("removerFlorins negativo devolve false", !c.removerFlorins(-1));
        verificar("removerFlorins do saldo exato funciona", c.removerFlorins(1000));
        verificar("... e deixa 0 (nunca negativo)", c.getFlorins() == 0);

        c = novaCidade();
        verificar("removerTerras acima do saldo devolve false", !c.removerTerras(10001));
        verificar("removerGraos acima do saldo devolve false", !c.removerGraos(501));
        verificar("removerServos acima do saldo devolve false", !c.removerServos(201));
        verificar("removerSoldados sem soldados devolve false", !c.removerSoldados(1));
        verificar("removerConstrucoes sem construcoes devolve false", !c.removerConstrucoes(1));
        verificar("recursos intactos apos as recusas",
                c.getTerras() == 10000 && c.getGraos() == 500 && c.getServos() == 200);
    }

    // ---------- Mercado ----------

    private static void testarMercado() {
        titulo("Mercado");
        Mercado mercado = new Mercado();

        // caminho feliz (grao: compra 2, venda 1; terra: compra 10, venda 5)
        Cidade c = novaCidade();
        boolean comprou = mercado.comprarGraos(c, 100);
        verificar("comprar 100 graos funciona", comprou);
        verificar("... custa 200 florins", c.getFlorins() == 800);
        verificar("... e soma 100 graos", c.getGraos() == 600);

        c = novaCidade();
        verificar("vender 100 graos funciona", mercado.venderGraos(c, 100));
        verificar("... remove 100 graos e credita 100 florins",
                c.getGraos() == 400 && c.getFlorins() == 1100);

        c = novaCidade();
        verificar("comprar 10 hectares funciona", mercado.comprarTerras(c, 10));
        verificar("... custa 100 florins e soma 10 hectares",
                c.getFlorins() == 900 && c.getTerras() == 10010);

        c = novaCidade();
        verificar("vender 10 hectares funciona", mercado.venderTerras(c, 10));
        verificar("... remove 10 hectares e credita 50 florins",
                c.getTerras() == 9990 && c.getFlorins() == 1050);

        // erros
        c = novaCidade();
        verificar("comprar quantidade negativa e recusado", !mercado.comprarGraos(c, -5));
        verificar("comprar quantidade zero e recusado", !mercado.comprarGraos(c, 0));
        verificar("comprar com florins insuficientes e recusado", !mercado.comprarGraos(c, 501));
        verificar("comprar numero enorme (overflow) e recusado",
                !mercado.comprarGraos(c, Integer.MAX_VALUE));
        verificar("vender mais graos do que tem e recusado", !mercado.venderGraos(c, 501));
        verificar("vender mais terras do que tem e recusado", !mercado.venderTerras(c, 10001));
        verificar("vender numero enorme (overflow) nao credita florins",
                !mercado.venderGraos(c, Integer.MAX_VALUE));
        verificar("recusas nao alteram nenhum recurso",
                c.getFlorins() == 1000 && c.getGraos() == 500 && c.getTerras() == 10000);
    }

    // ---------- Agricultura ----------

    private static void testarAgricultura() {
        titulo("Agricultura");
        Agricultura agricultura = new Agricultura();

        // caminho feliz: 1000 ha cultivados, 500 de semente, 2000 de producao
        Cidade c = novaCidade();
        int producao = agricultura.processarColheita(c);
        verificar("1a colheita cultiva 1000 ha (producao 2000)", producao == 2000);
        verificar("... gasta 500 de semente e soma a producao (500 - 500 + 2000)",
                c.getGraos() == 2000);
        verificar("... e marca a colheita do ano", c.isColheitaFeita());

        // erro: 2a colheita no mesmo ano
        verificar("2a colheita no mesmo ano devolve -1", agricultura.processarColheita(c) == -1);
        verificar("... e nao altera os graos", c.getGraos() == 2000);

        // novo ano libera
        c.avancarAno();
        verificar("no ano seguinte a colheita volta a funcionar",
                agricultura.processarColheita(c) == 2000);

        // erro: sem servos nada se planta e o indicador nao e marcado
        c = novaCidade();
        c.removerServos(200);
        verificar("sem servos nao planta nada (devolve 0)", agricultura.processarColheita(c) == 0);
        verificar("... nao marca a colheita e nao altera os graos",
                !c.isColheitaFeita() && c.getGraos() == 500);

        // borda: faltou semente, planta so o possivel
        c = novaCidade();
        c.removerGraos(400); // sobram 100
        verificar("com so 100 graos planta 200 ha (producao 400)",
                agricultura.processarColheita(c) == 400);
        verificar("... graos finais 400 (0 + 400)", c.getGraos() == 400);

        // distribuir alimentos
        c = novaCidade();
        verificar("distribuir 20% funciona", agricultura.distribuirAlimentos(c, 20));
        verificar("... sobra comida: graos 400 e servos 210",
                c.getGraos() == 400 && c.getServos() == 210);

        c = novaCidade();
        c.removerGraos(495); // 5 graos
        agricultura.distribuirAlimentos(c, 20); // 1 grao contra necessidade 40
        verificar("falta comida: populacao cai (200 -> 161)", c.getServos() == 161);

        // erros: percentual fora de 20 a 80
        c = novaCidade();
        verificar("percentual 19 e recusado", !agricultura.distribuirAlimentos(c, 19));
        verificar("percentual 81 e recusado", !agricultura.distribuirAlimentos(c, 81));
        verificar("percentual invalido nao altera graos nem servos",
                c.getGraos() == 500 && c.getServos() == 200);
    }

    // ---------- Governo ----------

    private static void testarGoverno() {
        titulo("Governo");
        Governo governo = new Governo();

        // caminho feliz
        Cidade c = novaCidade();
        verificar("Moderada a 10% arrecada 20", governo.arrecadarImpostos(c) == 20);
        verificar("... e credita nos florins", c.getFlorins() == 1020);

        c = novaCidade();
        verificar("definir justica Severa funciona", governo.definirJustica(c, Governo.SEVERA));
        verificar("Severa a 10% arrecada 26", governo.arrecadarImpostos(c) == 26);

        c = novaCidade();
        governo.definirJustica(c, Governo.JUSTA);
        verificar("Justa a 10% arrecada 16", governo.arrecadarImpostos(c) == 16);

        c = novaCidade();
        verificar("definir taxa 50 (limite) funciona", governo.definirTaxaImposto(c, 50));
        verificar("... e a taxa fica em 50", c.getTaxaImposto() == 50);

        // erro: 2a arrecadacao no ano
        c = novaCidade();
        governo.arrecadarImpostos(c);
        verificar("2a arrecadacao no mesmo ano devolve -1", governo.arrecadarImpostos(c) == -1);
        verificar("... e nao altera os florins", c.getFlorins() == 1020);
        c.avancarAno();
        verificar("no ano seguinte volta a arrecadar", governo.arrecadarImpostos(c) == 20);

        // erros: taxa e justica invalidas
        c = novaCidade();
        verificar("taxa 60% e recusada", !governo.definirTaxaImposto(c, 60));
        verificar("taxa negativa e recusada", !governo.definirTaxaImposto(c, -1));
        verificar("taxa recusada nao altera a taxa", c.getTaxaImposto() == 10);
        verificar("justica desconhecida e recusada", !governo.definirJustica(c, "Tirana"));
        verificar("justica recusada nao altera o nivel", "Moderada".equals(c.getNivelJustica()));
    }

    // ---------- Construcoes ----------

    private static void testarConstrucoes() {
        titulo("Construcoes");
        Construcoes construcoes = new Construcoes();

        // caminho feliz
        Cidade c = novaCidade();
        verificar("construir 10 funciona", construcoes.construir(c, 10));
        verificar("... custa 200 florins e soma 10 construcoes",
                c.getFlorins() == 800 && c.getConstrucoes() == 10);

        c = novaCidade();
        verificar("recrutar 10 soldados funciona", construcoes.recrutarSoldados(c, 10));
        verificar("... custa 50 florins e 10 servos",
                c.getFlorins() == 950 && c.getServos() == 190 && c.getSoldados() == 10);

        // erro: servos insuficientes, florins bastam
        // preparar: florins bastam (500 de 1000), servos nao (so 50)
        c = novaCidade();
        c.removerServos(150);
        // executar
        boolean recrutou = construcoes.recrutarSoldados(c, 100);
        // verificar
        verificar("recrutar 100 soldados com so 50 servos e recusado", !recrutou);
        verificar("... e NAO debita florins pela metade", c.getFlorins() == 1000);
        verificar("... nem altera servos e soldados", c.getServos() == 50 && c.getSoldados() == 0);

        // erro: florins insuficientes, servos bastam
        c = novaCidade();
        c.removerFlorins(990); // sobram 10
        verificar("recrutar 3 soldados com 10 florins e recusado",
                !construcoes.recrutarSoldados(c, 3));
        verificar("... e NAO debita servos", c.getServos() == 200 && c.getFlorins() == 10);

        // erros: construir
        c = novaCidade();
        verificar("construir sem florins suficientes e recusado", !construcoes.construir(c, 51));
        verificar("construir quantidade zero e recusado", !construcoes.construir(c, 0));
        verificar("construir quantidade negativa e recusado", !construcoes.construir(c, -3));
        verificar("construir numero enorme (overflow) e recusado",
                !construcoes.construir(c, Integer.MAX_VALUE));
        verificar("recusas nao alteram florins nem construcoes",
                c.getFlorins() == 1000 && c.getConstrucoes() == 0);
    }

    // ---------- Jogador e Nobreza ----------

    private static void testarJogadorENobreza() {
        titulo("Jogador e Nobreza");

        // Jogador: ordem dos titulos (testa o NIVEL, nao o texto)
        Jogador j = new Jogador("Ana");
        verificar("jogador comeca no nivel 0", j.getNivelTitulo() == 0);
        verificar("jogador novo nao e monarca", !j.monarca());
        verificar("promover devolve true", j.promover());
        verificar("... e o nivel vira 1", j.getNivelTitulo() == 1);

        // erro: nao passa de Rei
        for (int i = 0; i < 4; i++) {
            j.promover();
        }
        verificar("apos 5 promocoes o jogador e monarca", j.monarca() && j.getNivelTitulo() == 5);
        verificar("promover um monarca devolve false", !j.promover());
        verificar("... e o nivel continua 5", j.getNivelTitulo() == 5);

        // Nobreza: prestigio
        Nobreza nobreza = new Nobreza();
        Cidade c = novaCidade();
        verificar("prestigio inicial e 300 (200 servos + 10000/100)",
                nobreza.calcularPrestigio(c) == 300);
        verificar("prestigio 300 nao promove", !nobreza.verificarPromocao(c));
        verificar("... e o nivel continua 0", c.getDono().getNivelTitulo() == 0);

        c = novaCidade();
        c.adicionarConstrucoes(5);
        c.adicionarSoldados(10);
        verificar("prestigio = construcoes x 10 + soldados x 2 + servos + terras/100 (370)",
                nobreza.calcularPrestigio(c) == 370);

        c = novaCidade();
        c.adicionarFlorins(100000);
        verificar("florins nao entram no prestigio", nobreza.calcularPrestigio(c) == 300);

        // promocao: 50 construcoes -> 800 de prestigio, mas so 1 titulo por chamada
        c = novaCidade();
        c.adicionarConstrucoes(50);
        verificar("com 50 construcoes ha promocao", nobreza.verificarPromocao(c));
        verificar("... sobe apenas 1 nivel (1 titulo por vez)", c.getDono().getNivelTitulo() == 1);
        verificar("nova chamada promove mais um nivel",
                nobreza.verificarPromocao(c) && c.getDono().getNivelTitulo() == 2);

        // erro: monarca nao e promovido
        c = novaCidade();
        for (int i = 0; i < 5; i++) {
            c.getDono().promover();
        }
        c.adicionarConstrucoes(1000);
        verificar("monarca nao e promovido pela Nobreza", !nobreza.verificarPromocao(c));
        verificar("... e continua monarca", c.getDono().monarca());
    }

    // ---------- Partida ----------

    private static void testarPartida() {
        titulo("Partida");
        Nobreza nobreza = new Nobreza();

        // ordem dos turnos
        Partida p = new Partida();
        Cidade a = new Cidade("Bolonha", new Jogador("Ana"));
        Cidade b = new Cidade("Milano", new Jogador("Beto"));
        verificar("adicionarCidade aceita a primeira", p.adicionarCidade(a));
        verificar("adicionarCidade aceita a segunda", p.adicionarCidade(b));
        verificar("o primeiro turno e do primeiro jogador", p.getCidadeAtual() == a);
        verificar("1o passarTurno nao fecha o ano", !p.passarTurno());
        verificar("... e passa a vez ao segundo jogador", p.getCidadeAtual() == b);
        verificar("2o passarTurno fecha o ano", p.passarTurno());
        verificar("... e a vez volta ao primeiro", p.getCidadeAtual() == a);

        // encerrarAno avanca todas as cidades
        verificar("partida comeca em 1400", p.getAno() == 1400);
        a.marcarColheitaFeita();
        List<Cidade> promovidas = p.encerrarAno(nobreza);
        verificar("encerrarAno avanca a primeira cidade", a.getAnoAtual() == 1401);
        verificar("encerrarAno avanca a segunda cidade", b.getAnoAtual() == 1401);
        verificar("encerrarAno libera os indicadores anuais", !a.isColheitaFeita());
        verificar("sem prestigio suficiente ninguem e promovido", promovidas.isEmpty());

        a.adicionarConstrucoes(50);
        promovidas = p.encerrarAno(nobreza);
        verificar("encerrarAno devolve so a cidade promovida",
                promovidas.size() == 1 && promovidas.get(0) == a);

        // erro: a 5a cidade e recusada
        Partida cheia = new Partida();
        for (int i = 0; i < 4; i++) {
            cheia.adicionarCidade(new Cidade(Partida.nomeDaCidade(i), new Jogador("J" + i)));
        }
        verificar("a 5a cidade e recusada",
                !cheia.adicionarCidade(new Cidade("Roma", new Jogador("Extra"))));

        // fim de jogo
        Partida tempo = new Partida();
        tempo.adicionarCidade(new Cidade("Bolonha", new Jogador("Ana")));
        verificar("partida nova nao terminou", !tempo.terminou());
        for (int i = 0; i < 10; i++) {
            tempo.encerrarAno(nobreza);
        }
        verificar("em 1410 a partida ainda nao terminou", tempo.getAno() == 1410 && !tempo.terminou());
        tempo.encerrarAno(nobreza);
        verificar("passando de 1410 a partida termina", tempo.getAno() == 1411 && tempo.terminou());

        Partida coroa = new Partida();
        Cidade rei = new Cidade("Bolonha", new Jogador("Ana"));
        coroa.adicionarCidade(rei);
        for (int i = 0; i < 5; i++) {
            rei.getDono().promover();
        }
        verificar("partida termina quando alguem vira Rei", coroa.terminou());
        verificar("partida sem cidades nao termina", !new Partida().terminou());

        // vencedor
        Partida disputa = new Partida();
        Cidade barao = new Cidade("Bolonha", new Jogador("Ana"));
        Cidade cavaleiro = new Cidade("Milano", new Jogador("Beto"));
        barao.getDono().promover();
        cavaleiro.adicionarConstrucoes(100);
        disputa.adicionarCidade(cavaleiro);
        disputa.adicionarCidade(barao);
        verificar("vence o titulo mais alto, mesmo com menos prestigio",
                disputa.determinarVencedor(nobreza) == barao);

        Partida empate = new Partida();
        Cidade fraca = new Cidade("Bolonha", new Jogador("Ana"));
        Cidade forte = new Cidade("Milano", new Jogador("Beto"));
        forte.adicionarConstrucoes(10);
        empate.adicionarCidade(fraca);
        empate.adicionarCidade(forte);
        verificar("empate de titulo: vence o maior prestigio",
                empate.determinarVencedor(nobreza) == forte);
    }
}
