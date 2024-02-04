package pi.quarto.semestre.codigo.model;

/*
 * Classe da empresa, onde tem seus atributos, juntamente com os construtores e getters e setters necessários.
 * OBS: utilizei todos os atributos em tipo String para conseguir tratar melhor os dados no banco de dados.
 */

public class Empresa {
    private String cnpj;
    private String saldo;

    public Empresa(String cnpj, String saldo) {
        this.cnpj = cnpj;
        this.saldo = saldo;
    }

    public Empresa() {
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }
}

