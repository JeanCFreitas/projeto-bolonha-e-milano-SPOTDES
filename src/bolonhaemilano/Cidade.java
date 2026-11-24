public class Cidade {

    private static final int FLORINS_INICIAIS = 1000;
    private static final int TERRAS_INICIAIS = 10000;
    private static final int GRAOS_INICIAIS = 500;
    private static final int SERVOS_INICIAIS = 200;
    private static final int SOLDADOS_INICIAIS = 0;
    private static final int CONSTRUCOES_INICIAIS = 0;
    private static final int TAXA_IMPOSTO_INICIAL = 10;
    private static final String NIVEL_JUSTICA_INICIAL = "Moderada";

    private final String nome;
    private final Jogador dono;
    private int anoAtual;
    private int florins;
    private int terras;
    private int graos;
    private int servos;
    private int soldados;
    private int construcoes;
    private int taxaImposto;
    private String nivelJustica;
    private boolean colheitaFeita;
    private boolean impostosArrecadados;

    public Cidade(String nome, Jogador dono) {
        this.nome = nome;
        this.dono = dono;
        this.anoAtual = 1400;
        this.florins = FLORINS_INICIAIS;
        this.terras = TERRAS_INICIAIS;
        this.graos = GRAOS_INICIAIS;
        this.servos = SERVOS_INICIAIS;
        this.soldados = SOLDADOS_INICIAIS;
        this.construcoes = CONSTRUCOES_INICIAIS;
        this.taxaImposto = TAXA_IMPOSTO_INICIAL;
        this.nivelJustica = NIVEL_JUSTICA_INICIAL;
        this.colheitaFeita = false;
        this.impostosArrecadados = false;
    }

    // Soma 1 ao ano e libera colher/arrecadar de novo
    public void avancarAno() {
        anoAtual++;
        colheitaFeita = false;
        impostosArrecadados = false;
    }

    // ---- Indicadores anuais ----
    public void marcarColheitaFeita()        { colheitaFeita = true; }
    public boolean isColheitaFeita()         { return colheitaFeita; }
    public void marcarImpostosArrecadados()  { impostosArrecadados = true; }
    public boolean isImpostosArrecadados()   { return impostosArrecadados; }

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

    // ---- Soldados ----
    public void adicionarSoldados(int quantidade) {
        soldados += quantidade;
    }

    public boolean removerSoldados(int quantidade) {
        if (quantidade < 0 || quantidade > soldados) {
            return false;
        }
        soldados -= quantidade;
        return true;
    }

    // ---- Construções ----
    public void adicionarConstrucoes(int quantidade) {
        construcoes += quantidade;
    }

    public boolean removerConstrucoes(int quantidade) {
        if (quantidade < 0 || quantidade > construcoes) {
            return false;
        }
        construcoes -= quantidade;
        return true;
    }

    // ---- Configurações de governo (getter/setter simples) ----
    public int getTaxaImposto() {
        return taxaImposto;
    }

    public void setTaxaImposto(int taxaImposto) {
        this.taxaImposto = taxaImposto;
    }

    public String getNivelJustica() {
        return nivelJustica;
    }

    public void setNivelJustica(String nivelJustica) {
        this.nivelJustica = nivelJustica;
    }

    public String getNome()     { return nome; }
    public Jogador getDono()    { return dono; }
    public int getAnoAtual()    { return anoAtual; }
    public int getFlorins()     { return florins; }
    public int getTerras()      { return terras; }
    public int getGraos()       { return graos; }
    public int getServos()      { return servos; }
    public int getSoldados()    { return soldados; }
    public int getConstrucoes() { return construcoes; }

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
        System.out.println("Soldados: " + soldados);
        System.out.println("Construcoes: " + construcoes);
        System.out.println("Taxa de imposto: " + taxaImposto + "%");
        System.out.println("Nivel de justica: " + nivelJustica);
        System.out.println("Colheita deste ano: " + (colheitaFeita ? "feita" : "pendente"));
        System.out.println("Impostos deste ano: " + (impostosArrecadados ? "arrecadados" : "pendentes"));
    }
}