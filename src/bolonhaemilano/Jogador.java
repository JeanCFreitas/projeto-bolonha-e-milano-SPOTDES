public class Jogador {

    // Quem sabe a ORDEM dos títulos é o Jogador
    private static final String[] TITULOS = {
        "Cavaleiro(a)", "Barao(Baronesa)", "Conde(ssa)", "Marques(a)", "Duque(sa)", "Rei(Rainha)"
    };

    private final String nome;
    private int nivelTitulo;

    public Jogador(String nome) {
        this.nome = nome;
        this.nivelTitulo = 0; // Começa como cavaleiro(a)
    }

    public String getNome() {
        return nome;
    }

    public String getTitulo() {
        return TITULOS[nivelTitulo];
    }

    public int getNivelTitulo() {
        return nivelTitulo;
    }

        // Nome do proximo titulo; null se ja e monarca
    public String getProximoTitulo() {
        if (monarca()) {
            return null;
        }
        return TITULOS[nivelTitulo + 1];
    }

    // Se já é monarca devolve false; senão sobe, um nível
    public boolean promover() {
        if (monarca()) {
            return false;
        }
        nivelTitulo++;
        return true;
    }

    public boolean monarca() {
        return nivelTitulo == TITULOS.length - 1;
    }
}