package pi.quarto.semestre.codigo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import pi.quarto.semestre.codigo.model.Empresa;

/*
 * DAO para tratar sobre os dados das empresas com o banco de dados, fornecido pelas Controllers.
 * Aqui é onde é recebido dados, e também devolvidos se necessário pós tratamento.
 * Para a parte da taxa de transação, realizei apenas para saque, sendo a taxa de 5 reais.
 */

@Repository
public class EmpresaDao {
    
    //Informações do banco de dados.
    private static final String URL = "jdbc:mysql://localhost:3306/financeiro";
    private static final String USER = "root";
    private static final String PASSWORD = "admin1234";

    //Método para inserir uma empresa.
    public void inserir(Empresa empresa) throws SQLException {
        var con = DriverManager.getConnection(URL, USER, PASSWORD);

        var ps = con.prepareStatement("INSERT INTO empresa (cnpj, saldo) VALUES (?, ?)");
        ps.setString(1, empresa.getCnpj());
        ps.setString(2, empresa.getSaldo());

        ps.execute();

        con.close();
    }

    //Método para diminuir o saldo da empresa em questão, realizado por meio de um update.
    public void diminuirSaldo(String cnpjEmpresaAtual, String valorSaque, String saldoAtualEmpresa) throws SQLException{
        var con = DriverManager.getConnection(URL, USER, PASSWORD);

        double valorSaqueDouble = Double.parseDouble(valorSaque);
        double saldoAtualDouble = Double.parseDouble(saldoAtualEmpresa);

        double novoSaldo = saldoAtualDouble - valorSaqueDouble - 5;
        String novoSaldoString = String.valueOf(novoSaldo);

        var ps = con.prepareStatement("UPDATE empresa SET saldo = ? WHERE cnpj = ?");
        ps.setString(1, novoSaldoString);
        ps.setString(2, cnpjEmpresaAtual);

        ps.execute();

		con.close();
    }

    //Método para pegar o saldo da empresa por meio de um select do cnpj da própria.
    public String getSaldo(String cnpjEmpresaAtual) throws SQLException{
        String saldo = null;
    
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT saldo FROM empresa WHERE cnpj = ?";
            
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, cnpjEmpresaAtual);
    
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        saldo = rs.getString("saldo");
                    }
                }
            }
        }
    
        return saldo;
    }

    //Método para aumentar o saldo da Empresa por meio de um update, e é necessário o cnpj que é recebido para realizar aumentar o saldo certo.
    public void aumentarSaldo(String cnpjEmpresaAtual, String valorDeposito, String saldoAtualEmpresa) throws SQLException{
        var con = DriverManager.getConnection(URL, USER, PASSWORD);

        double valorDepositoDouble = Double.parseDouble(valorDeposito);
        double saldoAtualDouble = Double.parseDouble(saldoAtualEmpresa);

        double novoSaldo = saldoAtualDouble + valorDepositoDouble;
        String novoSaldoString = String.valueOf(novoSaldo);

        var ps = con.prepareStatement("UPDATE empresa SET saldo = ? WHERE cnpj = ?");
        ps.setString(1, novoSaldoString);
        ps.setString(2, cnpjEmpresaAtual);

        ps.execute();

		con.close();
    }

    //Método para verificar se a empresa possui saldo ou não, confirmado por meio de um true ou false.
    public boolean verificarSeTemSaldo(String cnpjEmpresaAtual, String saldoAtualEmpresa, String valorSaque) throws SQLException{
        var con = DriverManager.getConnection(URL, USER, PASSWORD);

        double valorSaqueDouble = Double.parseDouble(valorSaque);
        double saldoAtualDouble = Double.parseDouble(saldoAtualEmpresa);

        double novoSaldo = saldoAtualDouble - valorSaqueDouble;

        if(novoSaldo > 0) {
		    con.close();
            return true;
        }
        else {
            return false;
        }
    }
}
