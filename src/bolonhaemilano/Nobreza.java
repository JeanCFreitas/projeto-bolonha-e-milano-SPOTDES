public class Nobreza {

    // Quem decide quando promover é a Nobreza.
    // Índice = nível do título (0 = Cavaleiro ... 5 = Rei)
    private static final int[] PRESTIGIO_MINIMO = {0, 400, 600, 900, 1300, 1800};

    // Florins não entram na conta
    public int calcularPrestigio(Cidade cidade) {
        long prestigio = (long) cidade.getConstrucoes() * 10
                + (long) cidade.getSoldados() * 2
                + cidade.getServos()
                + cidade.getTerras() / 100;
        return (int) Math.min(prestigio, Integer.MAX_VALUE);
    }

    // No máximo 1 título por chamada
    public boolean verificarPromocao(Cidade cidade) {
        Jogador dono = cidade.getDono();
        if (dono.monarca()) {
            return false;
        }
        int proximoNivel = dono.getNivelTitulo() + 1;
        if (calcularPrestigio(cidade) >= PRESTIGIO_MINIMO[proximoNivel]) {
            dono.promover();
            return true;
        }
        return false;
    }
}