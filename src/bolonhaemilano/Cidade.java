public class Cidade {

    private static final int FLORINS_INICIAIS = 1000;
    private static final int TERRAS_INICIAIS = 10000;
    private static final int GRAOS_INICIAIS = 500;
    private static final int SERVOS_INICIAIS = 200;

    private final String nome;
    private final Jogador dono;
    private int anoAtual;
    private int florins;
    private int terras;
    private int graos;
    private int servos;

    public Cidade(String nome, Jogador dono) {
        this.nome = nome;
        this.dono = dono;
        this.anoAtual = 1400;
        this.florins = FLORINS_INICIAIS;
        this.terras = TERRAS_INICIAIS;
        this.graos = GRAOS_INICIAIS;
        this.servos = SERVOS_INICIAIS;
    }

    // Só será chamado a partir da Etapa 7
    public void avancarAno() {
        anoAtual++;
    }

    // ---- Florins ----
    public void adicionarFlorins(int quantidade) {
        florins += quantidade;
    }

    public boolean removerFlorins(int quantidade) {
        if (quantidade < 0 || quantidade > florins) {
            return false;
        }
        florins -= quantidade;
        return true;
    }

    // ---- Terras ----
    public void adicionarTerras(int quantidade) {
        terras += quantidade;
    }

    public boolean removerTerras(int quantidade) {
        if (quantidade < 0 || quantidade > terras) {
            return false;
        }
        terras -= quantidade;
        return true;
    }

    // ---- Grãos ----
    public void adicionarGraos(int quantidade) {
        graos += quantidade;
    }

    public boolean removerGraos(int quantidade) {
        if (quantidade < 0 || quantidade > graos) {
            return false;
        }
        graos -= quantidade;
        return true;
    }

    // ---- Servos (população) ----
    public void adicionarServos(int quantidade) {
        servos += quantidade;
    }

    public boolean removerServos(int quantidade) {
        if (quantidade < 0 || quantidade > servos) {
            return false;
        }
        servos -= quantidade;
        return true;
    }

    public int getFlorins() { return florins; }
    public int getTerras()  { return terras; }
    public int getGraos()   { return graos; }
    public int getServos()  { return servos; }

    public void exibirStatus() {
        System.out.println();
        System.out.println("--- STATUS DA CIDADE ---");
        System.out.println("Cidade: " + nome);
        System.out.println("Governante: " + dono.getTitulo() + " " + dono.getNome());
        System.out.println("Ano atual: " + anoAtual);
        System.out.println("Florins: " + florins);
        System.out.println("Terras: " + terras + " hectares");
        System.out.println("Graos: " + graos + " unidades");
        System.out.println("Servos: " + servos);
    }
}