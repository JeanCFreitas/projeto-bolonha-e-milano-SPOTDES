public class Governo {

    public static final String JUSTA = "Justa";
    public static final String MODERADA = "Moderada";
    public static final String SEVERA = "Severa";

    private static final int TAXA_MINIMA = 0;
    private static final int TAXA_MAXIMA = 50;

    private static final double MULTIPLICADOR_JUSTA = 0.8;
    private static final double MULTIPLICADOR_MODERADA = 1.0;
    private static final double MULTIPLICADOR_SEVERA = 1.3;

    // Devolve o total arrecadado; -1 se ja arrecadou neste ano (nada e alterado)
    public int arrecadarImpostos(Cidade cidade) {
        if (cidade.isImpostosArrecadados()) {
            return -1;
        }
        int base = (int) ((long) cidade.getServos() * cidade.getTaxaImposto() / 100);
        double multiplicador = multiplicadorPara(cidade.getNivelJustica());
        int arrecadado = (int) (base * multiplicador);
        cidade.adicionarFlorins(arrecadado);
        cidade.marcarImpostosArrecadados();
        return arrecadado;
    }

    public boolean definirTaxaImposto(Cidade cidade, int taxa) {
        if (taxa < TAXA_MINIMA || taxa > TAXA_MAXIMA) {
            return false;
        }
        cidade.setTaxaImposto(taxa);
        return true;
    }

    public boolean definirJustica(Cidade cidade, String nivel) {
        if (!JUSTA.equals(nivel) && !MODERADA.equals(nivel) && !SEVERA.equals(nivel)) {
            return false;
        }
        cidade.setNivelJustica(nivel);
        return true;
    }

    private double multiplicadorPara(String nivel) {
        if (JUSTA.equals(nivel)) {
            return MULTIPLICADOR_JUSTA;
        }
        if (SEVERA.equals(nivel)) {
            return MULTIPLICADOR_SEVERA;
        }
        return MULTIPLICADOR_MODERADA;
    }

    public int getTaxaMinima() { return TAXA_MINIMA; }
    public int getTaxaMaxima() { return TAXA_MAXIMA; }
}