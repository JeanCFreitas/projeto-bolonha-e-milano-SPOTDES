public class Agricultura {

    private static final int HECTARES_POR_SERVO = 5;
    private static final int PRODUCAO_POR_HECTARE = 2;
    private static final int HECTARES_POR_SEMENTE = 2;   // sementes = hectares / 2
    private static final int SERVOS_POR_GRAO = 5;        // necessidade = servos / 5
    private static final int CRESCIMENTO_PERCENTUAL = 5;
    private static final int PERCENTUAL_MINIMO = 20;
    private static final int PERCENTUAL_MAXIMO = 80;

    // A Agricultura não guarda nenhuma Cidade: ela chega por parâmetro.

    // Devolve a producao de graos (0 se nao foi possivel plantar nada)
    public int processarColheita(Cidade cidade) {
        long limite = (long) cidade.getServos() * HECTARES_POR_SERVO;
        int hectares = (int) Math.min(cidade.getTerras(), limite);
        int sementes = hectares / HECTARES_POR_SEMENTE;

        // Se faltar grao para a semente, planta so o que for possivel
        if (sementes > cidade.getGraos()) {
            sementes = cidade.getGraos();
            hectares = sementes * HECTARES_POR_SEMENTE;
        }

        if (hectares <= 0) {
            return 0;
        }

        int producao = hectares * PRODUCAO_POR_HECTARE;
        cidade.removerGraos(sementes);
        cidade.adicionarGraos(producao);
        return producao;
    }

    // Devolve false se o percentual estiver fora do intervalo permitido
    public boolean distribuirAlimentos(Cidade cidade, int percentual) {
        if (percentual < PERCENTUAL_MINIMO || percentual > PERCENTUAL_MAXIMO) {
            return false;
        }

        int alimentoDistribuido = (int) ((long) cidade.getGraos() * percentual / 100);
        cidade.removerGraos(alimentoDistribuido);

        int necessidade = cidade.getServos() / SERVOS_POR_GRAO;

        if (alimentoDistribuido >= necessidade) {
            int crescimento = cidade.getServos() * CRESCIMENTO_PERCENTUAL / 100;
            cidade.adicionarServos(crescimento);
        } else {
            int deficit = necessidade - alimentoDistribuido;
            cidade.removerServos(Math.min(deficit, cidade.getServos()));
        }
        return true;
    }

    public int getPercentualMinimo() { return PERCENTUAL_MINIMO; }
    public int getPercentualMaximo() { return PERCENTUAL_MAXIMO; }
}