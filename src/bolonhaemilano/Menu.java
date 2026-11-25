import java.util.List;
import java.util.Scanner;

public class Menu {

    private final Scanner scanner;
    private final Mercado mercado;
    private final Agricultura agricultura;
    private final Governo governo;
    private final Construcoes construcoes;
    private final Nobreza nobreza;
    private boolean emExecucao;
    private Partida partida;
    private Cidade cidade; // cidade da vez

    public Menu() {
        this.scanner = new Scanner(System.in);
        this.mercado = new Mercado();
        this.agricultura = new Agricultura();
        this.governo = new Governo();
        this.construcoes = new Construcoes();
        this.nobreza = new Nobreza();
        this.emExecucao = true;
    }

    public void executar() {
        while (emExecucao) {
            exibirOpcoes();
            int opcao = lerOpcao();
            processarOpcao(opcao);
        }
        scanner.close();
    }

    private void exibirOpcoes() {
        System.out.println();
        System.out.println("==========================================");
        System.out.println("            BOLONHA E MILANO");
        System.out.println("  Um jogo de estrategia economica medieval");
        System.out.println("==========================================");
        if (partida != null) {
            System.out.println("Ano " + partida.getAno() + " | Vez de: "
                    + cidade.getDono().getTitulo() + " " + cidade.getDono().getNome()
                    + " (" + cidade.getNome() + ")");
            System.out.println("------------------------------------------");
        }
        System.out.println("1 - Novo jogo");
        System.out.println("2 - Sobre o jogo");
        System.out.println("3 - Ver status da cidade");
        System.out.println("4 - Mercado");
        System.out.println("5 - Agricultura");
        System.out.println("6 - Governo");
        System.out.println("7 - Construcoes");
        System.out.println("8 - Ver classificacao");
        System.out.println("9 - Encerrar turno");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opcao: ");
    }

    // So chama lerInteiro()
    private int lerOpcao() {
        return lerInteiro();
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                iniciarNovoJogo();
                break;
            case 2:
                exibirSobre();
                break;
            case 3:
                exibirStatusCidade();
                break;
            case 4:
                abrirMercado();
                break;
            case 5:
                abrirAgricultura();
                break;
            case 6:
                abrirGoverno();
                break;
            case 7:
                abrirConstrucoes();
                break;
            case 8:
                verClassificacao();
                break;
            case 9:
                encerrarTurno();
                break;
            case 0:
                System.out.println("\nAte a proxima!");
                emExecucao = false;
                break;
            default:
                System.out.println("\nOpcao invalida. Tente novamente.");
        }
    }

    // ---------- Partida ----------

    private void iniciarNovoJogo() {
        System.out.print("Quantos jogadores (1 a " + Partida.MAXIMO_JOGADORES + ")? ");
        int quantidade = lerInteiro();
        if (quantidade < 1 || quantidade > Partida.MAXIMO_JOGADORES) {
            System.out.println("Quantidade invalida. Usando 2 jogadores.");
            quantidade = 2;
        }

        Partida novaPartida = new Partida();
        for (int i = 0; i < quantidade; i++) {
            String nomeCidade = Partida.nomeDaCidade(i);
            String nomeJogador = lerNome("Nome do jogador " + (i + 1)
                    + " (governara " + nomeCidade + "): ");
            novaPartida.adicionarCidade(new Cidade(nomeCidade, new Jogador(nomeJogador)));
        }

        partida = novaPartida;
        cidade = partida.getCidadeAtual();
        System.out.println("\nA partida comecou no ano " + partida.getAno() + "!");
        System.out.println("Vence quem virar Rei primeiro, ou o melhor colocado ao fim de 1410.");
        cidade.exibirStatus();
    }

    private void encerrarTurno() {
        if (!exigirJogoEmAndamento()) {
            return;
        }

        System.out.println("\nTurno de " + cidade.getDono().getNome() + " encerrado.");
        boolean anoAcabou = partida.passarTurno();

        if (anoAcabou) {
            List<Cidade> promovidas = partida.encerrarAno(nobreza);
            System.out.println("\n=== O ano " + (partida.getAno() - 1) + " terminou ===");
            for (Cidade promovida : promovidas) {
                System.out.println(promovida.getDono().getNome() + " foi promovido a "
                        + promovida.getDono().getTitulo() + "!");
            }
            if (promovidas.isEmpty()) {
                System.out.println("Nenhuma promocao neste ano.");
            }
            
            mostrarClassificacao();

            if (partida.terminou()) {
                anunciarVencedor();
                partida = null;
                cidade = null;
                return;
            }
        }

        cidade = partida.getCidadeAtual();
        System.out.println("\nAgora e a vez de " + cidade.getDono().getTitulo() + " "
                + cidade.getDono().getNome() + " (" + cidade.getNome() + ").");
    }

        // Opcao 8: so delega para mostrarClassificacao()
    private void verClassificacao() {
        if (!exigirJogoEmAndamento()) {
            return;
        }
        mostrarClassificacao();
    }

    private void mostrarClassificacao() {
        System.out.println("\n--- CLASSIFICACAO (ano " + partida.getAno() + ") ---");
        int posicao = 1;
        for (Cidade c : partida.classificar(nobreza)) {
            Jogador dono = c.getDono();
            int prestigio = nobreza.calcularPrestigio(c);
            System.out.println(posicao + ". " + dono.getTitulo() + " " + dono.getNome()
                    + " (" + c.getNome() + ") - prestigio " + prestigio);
            System.out.println("   " + descreverMeta(dono, prestigio));
            posicao++;
        }
    }

    // Texto da meta do proximo titulo
    private String descreverMeta(Jogador dono, int prestigio) {
        if (dono.monarca()) {
            return "Meta: ja alcancou o maior titulo (" + dono.getTitulo() + ").";
        }
        int meta = nobreza.prestigioParaProximoTitulo(dono);
        int faltam = meta - prestigio;
        if (faltam <= 0) {
            return "Meta: " + dono.getProximoTitulo() + " (" + meta
                    + " de prestigio) - ja alcancada, a promocao sai no fim do ano.";
        }
        return "Meta: " + dono.getProximoTitulo() + " (" + meta
                + " de prestigio) - faltam " + faltam + ".";
    }

    private void anunciarVencedor() {
        Cidade vencedora = partida.determinarVencedor(nobreza);
        System.out.println("\n******************************************");
        System.out.println("FIM DE JOGO!");
        System.out.println("Vencedor: " + vencedora.getDono().getTitulo() + " "
                + vencedora.getDono().getNome() + " de " + vencedora.getNome());
        System.out.println("******************************************");
    }

    private void exibirStatusCidade() {
        if (!exigirJogoEmAndamento()) {
            return;
        }
        cidade.exibirStatus();
    }

    // Se nao ha partida, avisa e devolve false. Cada submenu faz:
    // if (!exigirJogoEmAndamento()) return;
    private boolean exigirJogoEmAndamento() {
        if (partida == null) {
            System.out.println("\nNenhum jogo em andamento. Escolha \"Novo jogo\" primeiro.");
            return false;
        }
        return true;
    }

    // ---------- Mercado ----------

    private void abrirMercado() {
        if (!exigirJogoEmAndamento()) {
            return;
        }

        boolean noMercado = true;
        while (noMercado) {
            System.out.println();
            System.out.println("--- MERCADO ---");
            System.out.println("Florins: " + cidade.getFlorins()
                    + " | Graos: " + cidade.getGraos()
                    + " | Terras: " + cidade.getTerras());
            System.out.println("1 - Comprar graos  (" + mercado.getPrecoGrao() + " florins cada)");
            System.out.println("2 - Vender graos   (" + mercado.getPrecoVendaGrao() + " florins cada)");
            System.out.println("3 - Comprar terras (" + mercado.getPrecoTerra() + " florins/hectare)");
            System.out.println("4 - Vender terras  (" + mercado.getPrecoVendaTerra() + " florins/hectare)");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opcao: ");

            int opcao = lerInteiro();
            switch (opcao) {
                case 1:
                    resultado(mercado.comprarGraos(cidade, lerQuantidade("graos")),
                            "Compra de graos realizada.", "Florins insuficientes ou quantidade invalida.");
                    break;
                case 2:
                    resultado(mercado.venderGraos(cidade, lerQuantidade("graos")),
                            "Venda de graos realizada.", "Graos insuficientes ou quantidade invalida.");
                    break;
                case 3:
                    resultado(mercado.comprarTerras(cidade, lerQuantidade("hectares")),
                            "Compra de terras realizada.", "Florins insuficientes ou quantidade invalida.");
                    break;
                case 4:
                    resultado(mercado.venderTerras(cidade, lerQuantidade("hectares")),
                            "Venda de terras realizada.", "Terras insuficientes ou quantidade invalida.");
                    break;
                case 0:
                    noMercado = false;
                    break;
                default:
                    System.out.println("\nOpcao invalida. Tente novamente.");
            }
        }
    }

    // So imprime o pedido e chama lerInteiro()
    private int lerQuantidade(String unidade) {
        System.out.print("Quantidade de " + unidade + ": ");
        return lerInteiro();
    }

    private void resultado(boolean sucesso, String msgOk, String msgErro) {
        System.out.println("\n" + (sucesso ? msgOk : msgErro));
    }

    // ---------- Agricultura ----------

    private void abrirAgricultura() {
        if (!exigirJogoEmAndamento()) {
            return;
        }

        boolean naAgricultura = true;
        while (naAgricultura) {
            System.out.println();
            System.out.println("--- AGRICULTURA ---");
            System.out.println("Terras: " + cidade.getTerras()
                    + " | Servos: " + cidade.getServos()
                    + " | Graos: " + cidade.getGraos());
            System.out.println("1 - Colher" + (cidade.isColheitaFeita() ? " (ja feita neste ano)" : ""));
            System.out.println("2 - Distribuir alimentos");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opcao: ");

            int opcao = lerInteiro();
            switch (opcao) {
                case 1:
                    colher();
                    break;
                case 2:
                    distribuirAlimentos();
                    break;
                case 0:
                    naAgricultura = false;
                    break;
                default:
                    System.out.println("\nOpcao invalida. Tente novamente.");
            }
        }
    }

    private void colher() {
        int graosAntes = cidade.getGraos();
        int producao = agricultura.processarColheita(cidade);
        if (producao == -1) {
            System.out.println("\nVoce ja colheu neste ano. Encerre o turno para avancar.");
        } else if (producao == 0) {
            System.out.println("\nNao foi possivel plantar: faltam servos, terras ou graos para semente.");
        } else {
            int sementes = graosAntes + producao - cidade.getGraos();
            System.out.println("\nColheita realizada: " + producao + " graos produzidos, "
                    + sementes + " gastos em sementes.");
        }
    }

    private void distribuirAlimentos() {
        System.out.print("Percentual de graos a distribuir ("
                + agricultura.getPercentualMinimo() + " a "
                + agricultura.getPercentualMaximo() + "): ");
        int percentual = lerInteiro();

        int servosAntes = cidade.getServos();
        int graosAntes = cidade.getGraos();

        if (!agricultura.distribuirAlimentos(cidade, percentual)) {
            System.out.println("\nPercentual invalido. Use um valor entre "
                    + agricultura.getPercentualMinimo() + " e "
                    + agricultura.getPercentualMaximo() + ".");
            return;
        }

        int distribuido = graosAntes - cidade.getGraos();
        int variacao = cidade.getServos() - servosAntes;

        System.out.println("\n" + distribuido + " graos distribuidos.");
        if (variacao > 0) {
            System.out.println("Comida suficiente: a populacao cresceu " + variacao + " servos.");
        } else if (variacao < 0) {
            System.out.println("Fome! A populacao perdeu " + (-variacao) + " servos.");
        } else {
            System.out.println("A populacao permaneceu estavel.");
        }
    }

    // ---------- Governo ----------

    private void abrirGoverno() {
        if (!exigirJogoEmAndamento()) {
            return;
        }

        boolean noGoverno = true;
        while (noGoverno) {
            System.out.println();
            System.out.println("--- GOVERNO ---");
            System.out.println("Florins: " + cidade.getFlorins()
                    + " | Servos: " + cidade.getServos()
                    + " | Taxa: " + cidade.getTaxaImposto() + "%"
                    + " | Justica: " + cidade.getNivelJustica());
            System.out.println("1 - Arrecadar impostos"
                    + (cidade.isImpostosArrecadados() ? " (ja feito neste ano)" : ""));
            System.out.println("2 - Definir taxa de imposto");
            System.out.println("3 - Definir nivel de justica");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opcao: ");

            int opcao = lerInteiro();
            switch (opcao) {
                case 1:
                    arrecadarImpostos();
                    break;
                case 2:
                    definirTaxaImposto();
                    break;
                case 3:
                    definirJustica();
                    break;
                case 0:
                    noGoverno = false;
                    break;
                default:
                    System.out.println("\nOpcao invalida. Tente novamente.");
            }
        }
    }

    private void arrecadarImpostos() {
        int arrecadado = governo.arrecadarImpostos(cidade);
        if (arrecadado == -1) {
            System.out.println("\nOs impostos ja foram arrecadados neste ano. Encerre o turno para avancar.");
        } else {
            System.out.println("\nImpostos arrecadados: " + arrecadado + " florins.");
        }
    }

    private void definirTaxaImposto() {
        System.out.print("Nova taxa de imposto ("
                + governo.getTaxaMinima() + " a " + governo.getTaxaMaxima() + "%): ");
        int taxa = lerInteiro();
        if (governo.definirTaxaImposto(cidade, taxa)) {
            System.out.println("\nTaxa de imposto definida para " + taxa + "%.");
        } else {
            System.out.println("\nTaxa invalida. Use um valor entre "
                    + governo.getTaxaMinima() + " e " + governo.getTaxaMaxima() + ".");
        }
    }

    private void definirJustica() {
        System.out.println("1 - " + Governo.JUSTA);
        System.out.println("2 - " + Governo.MODERADA);
        System.out.println("3 - " + Governo.SEVERA);
        System.out.print("Escolha o nivel de justica: ");
        int escolha = lerInteiro();

        String nivel;
        switch (escolha) {
            case 1:
                nivel = Governo.JUSTA;
                break;
            case 2:
                nivel = Governo.MODERADA;
                break;
            case 3:
                nivel = Governo.SEVERA;
                break;
            default:
                nivel = "";
        }

        if (governo.definirJustica(cidade, nivel)) {
            System.out.println("\nNivel de justica definido para " + nivel + ".");
        } else {
            System.out.println("\nNivel de justica invalido.");
        }
    }

    // ---------- Construcoes ----------

    private void abrirConstrucoes() {
        if (!exigirJogoEmAndamento()) {
            return;
        }

        boolean emConstrucoes = true;
        while (emConstrucoes) {
            System.out.println();
            System.out.println("--- CONSTRUCOES ---");
            System.out.println("Florins: " + cidade.getFlorins()
                    + " | Servos: " + cidade.getServos()
                    + " | Soldados: " + cidade.getSoldados()
                    + " | Construcoes: " + cidade.getConstrucoes());
            System.out.println("1 - Recrutar soldados ("
                    + construcoes.getPrecoSoldado() + " florins + "
                    + construcoes.getServosPorSoldado() + " servo cada)");
            System.out.println("2 - Construir edificios ("
                    + construcoes.getPrecoConstrucao() + " florins cada)");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opcao: ");

            int opcao = lerInteiro();
            switch (opcao) {
                case 1:
                    recrutarSoldados();
                    break;
                case 2:
                    construir();
                    break;
                case 0:
                    emConstrucoes = false;
                    break;
                default:
                    System.out.println("\nOpcao invalida. Tente novamente.");
            }
        }
    }

    private void recrutarSoldados() {
        int quantidade = lerQuantidade("soldados");
        resultado(construcoes.recrutarSoldados(cidade, quantidade),
                "Recrutamento realizado: " + quantidade + " soldados.",
                "Florins ou servos insuficientes, ou quantidade invalida.");
    }

    private void construir() {
        int quantidade = lerQuantidade("construcoes");
        resultado(construcoes.construir(cidade, quantidade),
                "Construcao realizada: " + quantidade + " edificio(s).",
                "Florins insuficientes ou quantidade invalida.");
    }

    // ---------- Leitura de entrada ----------

    // Le a linha e converte com Integer.parseInt; devolve -1 se nao for numero.
    // lerOpcao() e lerQuantidade() so chamam este metodo.
    private int lerInteiro() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        } catch (java.util.NoSuchElementException e) {
            return 0; // fim da entrada: sai de forma limpa
        }
    }

    private String lerNome(String pergunta) {
        String nome = "";
        while (nome.isEmpty()) {
            System.out.print(pergunta);
            try {
                nome = scanner.nextLine().trim();
            } catch (java.util.NoSuchElementException e) {
                return "Desconhecido";
            }
            if (nome.isEmpty()) {
                System.out.println("O nome nao pode ficar vazio.");
            }
        }
        return nome;
    }

    private void exibirSobre() {
        System.out.println();
        System.out.println("--- SOBRE O JOGO ---");
        System.out.println("Bolonha e Milano e um jogo de estrategia economica");
        System.out.println("ambientado na Italia medieval. De 1 a 4 governantes");
        System.out.println("(Bolonha, Milano, Florenca e Veneza) disputam o trono.");
        System.out.println("A cada ano cada um joga o seu turno: colhe, cobra");
        System.out.println("impostos, negocia, recruta e constroi. Ao fim do ano,");
        System.out.println("o prestigio decide as promocoes de titulo.");
        System.out.println("Vence o primeiro a virar Rei, ou o melhor ao fim de 1410.");
    }
}