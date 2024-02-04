package pi.quarto.semestre.codigo.model;

import jakarta.annotation.ManagedBean;

/*
 * Dto do cliente, onde tem seus atributos, juntamente com os construtores e getters e setters necessários.
 * OBS: utilizei todos os atributos em tipo String para conseguir tratar melhor os dados no banco de dados. Classe criada caso fosse necessária sua utilização.
 */

@ManagedBean
public class ClienteDto {
    private String cpf;
    private String saldo;
    private String senha;
    private String cnpjEmpresa;

    public ClienteDto(String cpf, String saldo, String senha, String cnpjEmpresa) {
        this.cpf = cpf;
        this.saldo = saldo;
        this.senha = senha;
        this.cnpjEmpresa = cnpjEmpresa;
    }

    public ClienteDto() {
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

