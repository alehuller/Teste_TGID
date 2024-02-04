package pi.quarto.semestre.codigo.model;

/*
 * Classe do cliente, onde tem seus atributos, juntamente com os construtores e getters e setters necessários.
 * OBS: utilizei todos os atributos em tipo String para conseguir tratar melhor os dados no banco de dados.
 */

public class Cliente {
    private String cpf;
    private String saldo;
    private String senha;
    private String cnpjEmpresa;

    public Cliente(String cpf, String saldo, String senha, String cnpjEmpresa) {
        this.cpf = cpf;
        this.saldo = saldo;
        this.senha = senha;
        this.cnpjEmpresa = cnpjEmpresa;
    }

    public Cliente() {
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCnpjEmpresa() {
        return cnpjEmpresa;
    }

    public void setCnpjEmpresa(String cnpjEmpresa) {
        this.cnpjEmpresa = cnpjEmpresa;
    }
}
