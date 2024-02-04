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

import pi.quarto.semestre.codigo.dao.EmpresaDao;
import pi.quarto.semestre.codigo.dao.UsuarioDao2;
import pi.quarto.semestre.codigo.model.Cliente;
import pi.quarto.semestre.codigo.model.Empresa;
import pi.quarto.semestre.codigo.util.CnpjValidacao;

/*
 * Controller para cadastrar uma empresa, com o cnpj sendo validado.
 */

@Controller
@RequestMapping("/paginaCadastroEmpresa")
public class ControllerCadastroEmpresa {

    //GetMapping para pegar a página de cadastro de empresa.
    @GetMapping
    public String init(final Model model) {
        model.addAttribute("empresa", new Empresa());
        return "paginaCadastroEmpresa";
    }

    //PostMapping para receber os dados inseridos na página do cadastro.
    @PostMapping
    public ModelAndView result(@ModelAttribute Empresa empresa) throws NoSuchAlgorithmException, UnsupportedEncodingException, SQLException {
        CnpjValidacao cnpjValidacao = new CnpjValidacao();
        boolean cnpjValidado = cnpjValidacao.isCNPJ(empresa.getCnpj());

        if (!cnpjValidado) {
            ModelAndView errorModelAndView = new ModelAndView("redirect:/paginaCadastroEmpresa");
            errorModelAndView.addObject("cnpjError", "CNPJ inválido");
            return errorModelAndView;
        }
        EmpresaDao empresaDao = new EmpresaDao();
        empresaDao.inserir(empresa);

        ModelAndView successModelAndView = new ModelAndView("redirect:/login");
        return successModelAndView;
    }
    
}
