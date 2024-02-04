package pi.quarto.semestre.codigo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import pi.quarto.semestre.codigo.model.Cliente;

/*
 * DAO para tratar sobre os dados dos clientes com o banco de dados, fornecido pelas Controllers.
 * Aqui é onde é recebido dados, e também devolvidos se necessário pós tratamento.
 * Para a parte da taxa de transação, realizei apenas para saque, sendo a taxa de 5 reais.
 */

@Repository
public class UsuarioDao2 {
    
    //Informações do banco de dados.
    private static final String URL = "jdbc:mysql://localhost:3306/financeiro";
    private static final String USER = "root";
    private static final String PASSWORD = "admin1234";

    //Método para validar se o cliente existe no sistema, por meio de um select ou é utilizado o cpf e a senha inserida.
    public boolean validarCliente(String cpf, String senha) throws SQLException{
        var con = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String query = "SELECT * FROM cliente WHERE cpf = ? and senha = ?";
        preparedStatement = con.prepareStatement(query);
        preparedStatement.setString(1, cpf);
        preparedStatement.setString(2, senha);
        resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) {
            return true;
        }
        else {
            return false;
        }
    }


    //Método para inserir um cliente.
    public void inserir(Cliente cliente) throws SQLException{
        var con = DriverManager.getConnection(URL, USER, PASSWORD);

        var ps = con.prepareStatement("INSERT INTO cliente (cpf, senha, saldo, cnpj_empresa) VALUES (?, ?, ?, ?)");
        ps.setString(1, cliente.getCpf());
        ps.setString(2, cliente.getSenha());
        ps.setString(3, "0");
        ps.setString(4, cliente.getCnpjEmpresa());

        ps.execute();

        con.close();
    }


    //Método para realizar o saque do cliente por meio do cpf do cliente.
    public void setSaldo(String loggedInUserCpf, String valorSaque, String saldoAtual) throws SQLException{
        var con = DriverManager.getConnection(URL, USER, PASSWORD);

        double valorSaqueDouble = Double.parseDouble(valorSaque);
        double saldoAtualDouble = Double.parseDouble(saldoAtual);

        double novoSaldo = saldoAtualDouble + valorSaqueDouble - 5;
        String novoSaldoString = String.valueOf(novoSaldo);

        var ps = con.prepareStatement("UPDATE cliente SET saldo = ? WHERE cpf = ?");
        ps.setString(1, novoSaldoString);
        ps.setString(2, loggedInUserCpf);

        ps.execute();

		con.close();
    }

    //Método para pegar o cnpj da empresa que está atribuido ao cliente, que é retornado.
    public String getCnpjEmpresa(String loggedInUserCpf) throws SQLException {
        String cnpjEmpresa = null;
    
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT cnpj_empresa FROM cliente WHERE cpf = ?";
            
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, loggedInUserCpf);
    
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        cnpjEmpresa = rs.getString("cnpj_empresa");
                    }
                }
            }
        }
    
        return cnpjEmpresa;
    }

    //Método para pegar o saldo do cliente por meio de seu cpf, que é retornado.
    public String getSaldo(String loggedInUserCpf) throws SQLException{
        String saldo = null;
    
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT saldo FROM cliente WHERE cpf = ?";
            
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, loggedInUserCpf);
    
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        saldo = rs.getString("saldo");
                    }
                }
            }
        }
    
        return saldo;
    }

    //Método para diminuir o saldo do cliente por meio de seu cpf.
    public void setMenosSaldo(String loggedInUserCpf, String valorDeposito, String saldoAtual) throws SQLException{
        var con = DriverManager.getConnection(URL, USER, PASSWORD);

        double valorDepositoDouble = Double.parseDouble(valorDeposito);
        double saldoAtualDouble = Double.parseDouble(saldoAtual);

        double novoSaldo = saldoAtualDouble - valorDepositoDouble;
        String novoSaldoString = String.valueOf(novoSaldo);

        var ps = con.prepareStatement("UPDATE cliente SET saldo = ? WHERE cpf = ?");
        ps.setString(1, novoSaldoString);
        ps.setString(2, loggedInUserCpf);

        ps.execute();

		con.close();
    }

    //Método para verificar se o usuário possui saldo ou não, retornando se ele tem ou não por meio do true ou false.
    public boolean verificarSeTemSaldo(String loggedInUserCpf, String saldoAtual, String valorDeposito) throws SQLException{
        var con = DriverManager.getConnection(URL, USER, PASSWORD);

        double valorDepositoDouble = Double.parseDouble(valorDeposito);
        double saldoAtualDouble = Double.parseDouble(saldoAtual);

        double novoSaldo = saldoAtualDouble - valorDepositoDouble;

        if(novoSaldo > 0) {
		    con.close();
            return true;
        }
        else {
            return false;
        }
    }
}
