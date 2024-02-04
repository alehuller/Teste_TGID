package pi.quarto.semestre.codigo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * Controller utilizada única e exclusivamente para a página de escolha de depósito ou saque
 */

@Controller
public class ControllerDepositoOuSaque {
    
    //GetMapping para pegar a página de escolha se o cliente deseja depositar ou sacar.
    @GetMapping("paginaDepositoOuSaque")
    public String paginaDepositoOuSaque(Model model) {
        return "/paginaDepositoOuSaque";
    }
}
