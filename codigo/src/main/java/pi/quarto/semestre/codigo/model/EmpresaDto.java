package pi.quarto.semestre.codigo.model;

import jakarta.annotation.ManagedBean;

/*
 * Dto da empresa, onde tem seus atributos, juntamente com os construtores e getters e setters necessários.
 * OBS: utilizei todos os atributos em tipo String para conseguir tratar melhor os dados no banco de dados. Classe criada caso fosse necessária sua utilização.
 */

@ManagedBean
public class EmpresaDto {
    private String cnpj;
    private String saldo;

    public EmpresaDto(String cnpj, String saldo) {
        this.cnpj = cnpj;
        this.saldo = saldo;
    }

    public EmpresaDto() {
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

