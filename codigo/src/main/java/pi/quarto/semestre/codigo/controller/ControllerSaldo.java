package pi.quarto.semestre.codigo.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import pi.quarto.semestre.codigo.dao.EmpresaDao;
import pi.quarto.semestre.codigo.dao.UsuarioDao2;
import pi.quarto.semestre.codigo.model.Cliente;

/*
 * Controller para o saque.
 * UsuarioDao2 é a DAO do cliente.
 * Neste, utilizo o HttpSession para guardar o cpf do Cliente e pegar suas informações.
 * Aqui é onde é recebido o valor do saque da página, e tambem é tratado, para verificar se há saldo por parte da empresa para realizar o saque.
 * Para a parte da taxa de transação, realizei apenas para saque, sendo a taxa de 5 reais.
 */

@Controller
@RequestMapping("/paginaSaque")
public class ControllerSaldo {
    
    private final UsuarioDao2 usuarioDao2;
    private HttpServletRequest request;

    public ControllerSaldo(UsuarioDao2 usuarioDao2, HttpServletRequest request) {
        this.usuarioDao2 = usuarioDao2;
        this.request = request;
    }

    //GetMapping para pegar a página de saque.
    @GetMapping
    public String init(Model model) throws SQLException {
        return "paginaSaque";
    }

    //PostMapping para receber os dados inseridos na página do saque.
    @PostMapping
    public String result(@RequestParam("valorSaque") String valorSaque, Model model) throws NoSuchAlgorithmException, UnsupportedEncodingException, SQLException {
        HttpSession session = request.getSession();
        String loggedInUserCpf = (String) session.getAttribute("loggedInUser");
        EmpresaDao empresaDao = new EmpresaDao();

        String cnpjEmpresaAtual = usuarioDao2.getCnpjEmpresa(loggedInUserCpf);
        String saldoAtualEmpresa = empresaDao.getSaldo(cnpjEmpresaAtual);
        
        String saldoAtual = usuarioDao2.getSaldo(loggedInUserCpf);
        boolean verificado = empresaDao.verificarSeTemSaldo(cnpjEmpresaAtual, saldoAtualEmpresa, valorSaque);

        if(verificado) {
            usuarioDao2.setSaldo(loggedInUserCpf, valorSaque, saldoAtual);
            empresaDao.diminuirSaldo(cnpjEmpresaAtual, valorSaque, saldoAtualEmpresa);
            model.addAttribute("transacaoSucesso", true);
            return "redirect:/paginaDepositoOuSaque";
        }
        model.addAttribute("transacaoFalha", true);
        return "redirect:/paginaSaque";
    }    
}
