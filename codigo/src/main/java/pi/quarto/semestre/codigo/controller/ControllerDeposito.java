package pi.quarto.semestre.codigo.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import pi.quarto.semestre.codigo.dao.EmpresaDao;
import pi.quarto.semestre.codigo.dao.UsuarioDao2;

/*
 * Controller para o depósito.
 * UsuarioDao2 é a DAO do cliente.
 * Neste, utilizo o HttpSession para guardar o cpf do Cliente e pegar suas informações.
 * Aqui é onde é recebido o valor do depósito da página, e tambem é tratado, para verificar se há saldo por parte do usuário para realizar o depósito.
 */

@Controller
@RequestMapping("/paginaDeposito")
public class ControllerDeposito {
    
    private final UsuarioDao2 usuarioDao2;
    private HttpServletRequest request;

    public ControllerDeposito(UsuarioDao2 usuarioDao2, HttpServletRequest request) {
        this.usuarioDao2 = usuarioDao2;
        this.request = request;
    }

    //GetMapping para pegar a página de depósito.
    @GetMapping
    public String init(Model model) throws SQLException {
        return "paginaDeposito";
    }

    //PostMapping para receber os dados inseridos na página de depósito.
    @PostMapping
    public String result(@RequestParam("valorDeposito") String valorDeposito, Model model) throws NoSuchAlgorithmException, UnsupportedEncodingException, SQLException {
        HttpSession session = request.getSession();
        String loggedInUserCpf = (String) session.getAttribute("loggedInUser");
        EmpresaDao empresaDao = new EmpresaDao();
        
        String cnpjEmpresaAtual = usuarioDao2.getCnpjEmpresa(loggedInUserCpf);
        String saldoAtualEmpresa = empresaDao.getSaldo(cnpjEmpresaAtual);

        String saldoAtual = usuarioDao2.getSaldo(loggedInUserCpf);
        boolean verificado = usuarioDao2.verificarSeTemSaldo(loggedInUserCpf, saldoAtual, valorDeposito);

        if(verificado) {
            usuarioDao2.setMenosSaldo(loggedInUserCpf, valorDeposito, saldoAtual);
            empresaDao.aumentarSaldo(cnpjEmpresaAtual, valorDeposito, saldoAtualEmpresa);
            model.addAttribute("transacaoSucesso", true);
            return "redirect:/paginaDepositoOuSaque";
        }
        model.addAttribute("transacaoFalha", true);
        return "redirect:/paginaDeposito";
    }

}
