public class Mercado {

    private static final int PRECO_GRAO = 4;          // compra: cidade paga
    private static final int PRECO_VENDA_GRAO = 2;    // venda: cidade recebe
    private static final int PRECO_TERRA = 10;
    private static final int PRECO_VENDA_TERRA = 5;

    // O Mercado não guarda nenhuma Cidade: ela chega por parâmetro.

    public boolean comprarGraos(Cidade cidade, int quantidade) {
        if (quantidade <= 0 || quantidade > Integer.MAX_VALUE / PRECO_GRAO) {
            return false;
        }
        int custo = quantidade * PRECO_GRAO;
        if (!cidade.removerFlorins(custo)) {
            return false;
        }
        cidade.adicionarGraos(quantidade);
        return true;
    }

    public boolean venderGraos(Cidade cidade, int quantidade) {
        if (quantidade <= 0 || quantidade > Integer.MAX_VALUE / PRECO_VENDA_GRAO) {
            return false;
        }
        if (!cidade.removerGraos(quantidade)) {
            return false;
        }
        cidade.adicionarFlorins(quantidade * PRECO_VENDA_GRAO);
        return true;
    }

    public boolean comprarTerras(Cidade cidade, int quantidade) {
        if (quantidade <= 0 || quantidade > Integer.MAX_VALUE / PRECO_TERRA) {
            return false;
        }
        int custo = quantidade * PRECO_TERRA;
        if (!cidade.removerFlorins(custo)) {
            return false;
        }
        cidade.adicionarTerras(quantidade);
        return true;
    }

    public boolean venderTerras(Cidade cidade, int quantidade) {
        if (quantidade <= 0 || quantidade > Integer.MAX_VALUE / PRECO_VENDA_TERRA) {
            return false;
        }
        if (!cidade.removerTerras(quantidade)) {
            return false;
        }
        cidade.adicionarFlorins(quantidade * PRECO_VENDA_TERRA);
        return true;
    }

    public int getPrecoGrao()       { return PRECO_GRAO; }
    public int getPrecoVendaGrao()  { return PRECO_VENDA_GRAO; }
    public int getPrecoTerra()      { return PRECO_TERRA; }
    public int getPrecoVendaTerra() { return PRECO_VENDA_TERRA; }
}