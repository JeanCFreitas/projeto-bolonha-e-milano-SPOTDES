import java.util.ArrayList;
import java.util.List;

public class Partida {

    public static final int MAXIMO_JOGADORES = 4;
    private static final int ANO_FINAL = 1410;
    private static final String[] NOMES_CIDADES = {"Bolonha", "Milano", "Florenca", "Veneza"};

    private final List<Cidade> cidades;
    private int indiceAtual;

    public Partida() {
        this.cidades = new ArrayList<>();
        this.indiceAtual = 0;
    }

    // Nome da cidade do jogador de índice i (0 a 3)
    public static String nomeDaCidade(int indice) {
        return NOMES_CIDADES[indice];
    }

    // Recusa a quinta cidade
    public boolean adicionarCidade(Cidade cidade) {
        if (cidades.size() >= MAXIMO_JOGADORES) {
            return false;
        }
        cidades.add(cidade);
        return true;
    }

    public Cidade getCidadeAtual() {
        return cidades.get(indiceAtual);
    }

    public int getAno() {
        return cidades.get(0).getAnoAtual();
    }

    // Devolve true quando o último jogador encerrou o turno (o ano acabou)
    public boolean passarTurno() {
        indiceAtual++;
        if (indiceAtual >= cidades.size()) {
            indiceAtual = 0;
            return true; // o ano acabou
        }
        return false;
    }

    // Devolve a lista das cidades cujo governante foi promovido
    public List<Cidade> encerrarAno(Nobreza nobreza) {
        List<Cidade> promovidas = new ArrayList<>();
        for (Cidade cidade : cidades) {
            cidade.avancarAno();
            if (nobreza.verificarPromocao(cidade)) {
                promovidas.add(cidade);
            }
        }
        return promovidas;
    }

    // Termina se alguém é monarca ou se o ano passou de 1410
    public boolean terminou() {
        if (cidades.isEmpty()) {
            return false;
        }
        for (Cidade cidade : cidades) {
            if (cidade.getDono().monarca()) {
                return true;
            }
        }
        return getAno() > ANO_FINAL;
    }

    // Título mais alto; empate, maior prestígio
    public List<Cidade> classificar(Nobreza nobreza) {
        List<Cidade> ranking = new ArrayList<>(cidades);
        ranking.sort((a, b) -> {
            int porTitulo = Integer.compare(b.getDono().getNivelTitulo(),
                                            a.getDono().getNivelTitulo());
            if (porTitulo != 0) {
                return porTitulo;
            }
            return Integer.compare(nobreza.calcularPrestigio(b), nobreza.calcularPrestigio(a));
        });
        return ranking;
    }

    public Cidade determinarVencedor(Nobreza nobreza) {
        return classificar(nobreza).get(0);
    }
}