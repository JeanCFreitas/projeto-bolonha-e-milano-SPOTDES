import java.util.Scanner;

public class Menu {

    private final Scanner scanner;
    private boolean emExecucao;

    public Menu() {
        this.scanner = new Scanner(System.in);
        this.emExecucao = true;
    }

    // Laço principal: exibir -> ler -> processar
    public void executar() {
        while (emExecucao) {
            exibirOpcoes();
            int opcao = lerOpcao();
            processarOpcao(opcao);
        }
        scanner.close();
    }

    // Apenas imprime o menu, não lê nada do teclado
    private void exibirOpcoes() {
        System.out.println();
        System.out.println("==========================================");
        System.out.println("            BOLONHA E MILANO");
        System.out.println("  Um jogo de estrategia economica medieval");
        System.out.println("==========================================");
        System.out.println("1 - Novo jogo");
        System.out.println("2 - Sobre o jogo");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opcao: ");
    }

    // Lê uma linha e converte para inteiro; devolve -1 se for inválida
    private int lerOpcao() {
        try {
            String linha = scanner.nextLine().trim();
            return Integer.parseInt(linha);
        } catch (NumberFormatException e) {
            return -1;
        } catch (java.util.NoSuchElementException e) {
            // Fim da entrada (ex.: Ctrl+D): encerra de forma limpa
            return 0;
        }
    }

    // Decide a ação com base na opção lida
    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                System.out.println("\nNovo jogo: sera implementado na Etapa 2.");
                break;
            case 2:
                exibirSobre();
                break;
            case 0:
                System.out.println("\nAte a proxima!");
                emExecucao = false;
                break;
            default:
                System.out.println("\nOpcao invalida. Tente novamente.");
        }
    }

    // Texto explicativo do jogo (só chamado pela opção 2)
    private void exibirSobre() {
        System.out.println();
        System.out.println("--- SOBRE O JOGO ---");
        System.out.println("Bolonha e Milano e um jogo de estrategia economica");
        System.out.println("ambientado na Italia medieval. Voce comanda um mercador");
        System.out.println("que viaja entre as cidades de Bolonha e Milano,");
        System.out.println("comprando mercadorias onde estao baratas e vendendo");
        System.out.println("onde valem mais. Administre seu dinheiro, aproveite as");
        System.out.println("diferencas de preco e construa sua fortuna.");
    }
}
