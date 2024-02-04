package pi.quarto.semestre.codigo.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import pi.quarto.semestre.codigo.dao.UsuarioDao2;
import pi.quarto.semestre.codigo.model.ClienteDto;

/*
 * Controller para a realização do login do usuário cliente, com a verificação se o usuário existe.
 * UsuarioDao2 é a DAO do cliente.
 * Neste, utilizo o HttpSession para guardar o cpf do Cliente e utilizá-lo em outras controllers.
 * Aqui é onde é recebido o cpf e senha do usuário, e tambem é tratado, para verificar se ele existe no sistema.
 * A página de login deve ser a primeira a ser acessada, para conseguir ter acesso a todas as funções.
 */

@Controller
@RequestMapping("/login")
public class ControllerPaginaCliente {

    @Autowired
    private HttpServletRequest request;

    //GetMapping para pegar a página de login do cliente, onde ele pode fazer o seu login, ou cadastrar uma empresa, ou um novo cliente.
    @GetMapping
    public String init(final Model model) {
        model.addAttribute("clienteDto", new ClienteDto());
        return "paginaCliente";
    }
    
    public ModelAndView redirect(final Model model) {
        ModelAndView modelAndView = new ModelAndView("redirect:paginaDepositoOuSaque");
        return modelAndView;
    }

    //PostMapping para receber os dados inseridos na página para login.
    @PostMapping
    public ModelAndView result(@ModelAttribute ClienteDto clienteDto) throws NoSuchAlgorithmException, UnsupportedEncodingException, SQLException {
        clienteDto.getCpf();
        clienteDto.getSenha();

        UsuarioDao2 usuarioDAO = new UsuarioDao2();
        boolean valido = usuarioDAO.validarCliente(clienteDto.getCpf(), clienteDto.getSenha());
        if(valido == true) {
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", clienteDto.getCpf());
            ModelAndView modelAndView = new ModelAndView("redirect:paginaDepositoOuSaque");
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("redirect:login");
        return modelAndView;
    }
}

