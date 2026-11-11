import java.util.Scanner;

public class Menu {

    private final Scanner scanner;
    private boolean emExecucao;
    private Jogador jogador;
    private Cidade cidade;

    public Menu() {
        this.scanner = new Scanner(System.in);
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
        System.out.println("1 - Novo jogo");
        System.out.println("2 - Sobre o jogo");
        System.out.println("3 - Ver status da cidade");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opcao: ");
    }

    private int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        } catch (java.util.NoSuchElementException e) {
            return 0; // fim da entrada: sai de forma limpa
        }
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
            case 0:
                System.out.println("\nAte a proxima!");
                emExecucao = false;
                break;
            default:
                System.out.println("\nOpcao invalida. Tente novamente.");
        }
    }

    private void iniciarNovoJogo() {
        String nomeJogador = lerNome("Digite o nome do jogador: ");
        String nomeCidade = lerNome("Digite o nome da cidade: ");
        jogador = new Jogador(nomeJogador);
        cidade = new Cidade(nomeCidade, jogador);
        cidade.exibirStatus();
    }

    private void exibirStatusCidade() {
        if (cidade == null) {
            System.out.println("\nNenhum jogo iniciado. Escolha \"Novo jogo\" primeiro.");
        } else {
            cidade.exibirStatus();
        }
    }

    // Repete a pergunta enquanto o nome vier vazio
    private String lerNome(String pergunta) {
        String nome = "";
        while (nome.isEmpty()) {
            System.out.print(pergunta);
            try {
                nome = scanner.nextLine().trim();
            } catch (java.util.NoSuchElementException e) {
                return "Desconhecido"; // fim da entrada
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
        System.out.println("ambientado na Italia medieval. Voce comanda uma cidade,");
        System.out.println("administra seus recursos e constroi sua fortuna.");
    }
}