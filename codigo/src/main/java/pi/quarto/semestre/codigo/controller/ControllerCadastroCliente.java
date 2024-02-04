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
import org.springframework.web.servlet.ModelAndView;

import pi.quarto.semestre.codigo.dao.UsuarioDao2;
import pi.quarto.semestre.codigo.model.Cliente;
import pi.quarto.semestre.codigo.util.CpfValidacao;

/*
 * Controller para cadastrar um cliente, com o cpf sendo validado.
 * UsuarioDao2 é a DAO do cliente.
 */

@Controller
@RequestMapping("/paginaCadastroCliente")
public class ControllerCadastroCliente {
    
    //GetMapping para pegar a página de cadastro de cliente.
    @GetMapping
    public String init(final Model model) {
        model.addAttribute("cliente", new Cliente());
        return "paginaCadastroCliente";
    }

    //PostMapping para receber os dados inseridos na página do cadastro.
    @PostMapping
    public ModelAndView result(@ModelAttribute Cliente cliente) throws NoSuchAlgorithmException, UnsupportedEncodingException, SQLException {
        CpfValidacao cpfValidacao = new CpfValidacao();
        boolean cpfValidado = cpfValidacao.isCPF(cliente.getCpf());

        if (!cpfValidado) {
            ModelAndView errorModelAndView = new ModelAndView("redirect:/paginaCadastroCliente");
            errorModelAndView.addObject("cpfError", "CPF inválido");
            return errorModelAndView;
        }

        UsuarioDao2 usuarioDao2 = new UsuarioDao2();
        usuarioDao2.inserir(cliente);

        ModelAndView successModelAndView = new ModelAndView("redirect:/login");
        return successModelAndView;
    }
}
