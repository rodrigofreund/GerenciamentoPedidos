package com.rodrigofreund.treinamento.springboot.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rodrigofreund.treinamento.springboot.repository.ClienteRepository;
import com.rodrigofreund.treinamento.springboot.repository.domain.Cliente;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private static final String CLIENTE_URI = "clientes/";

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("/")
    public ModelAndView list() {
        var clientes = this.clienteRepository.findAll();
        return new ModelAndView(CLIENTE_URI + "list", "clientes", clientes);
    }

    @GetMapping("{id}")
    public ModelAndView view(@PathVariable("id") Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow();
        return new ModelAndView(CLIENTE_URI + "view", "cliente", cliente);
    }

    @GetMapping("alterar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow();
        return new ModelAndView(CLIENTE_URI + "form", "cliente", cliente);
    }

    @GetMapping("remover/{id}")
    public ModelAndView remover(@PathVariable("id") Long id, RedirectAttributes redirect) {
        clienteRepository.deleteById(id);

        var clientes = clienteRepository.findAll();
        ModelAndView modelAndView = new ModelAndView(CLIENTE_URI + "list", "clientes", clientes);
        modelAndView.addObject("globalMessage", "Cliente removido com sucesso");
        return modelAndView;
    }

    @PostMapping(params = "form")
    public ModelAndView create(@Valid Cliente cliente, BindingResult results, RedirectAttributes redirect) {
        if (results.hasErrors()) {
            return new ModelAndView(CLIENTE_URI + "form", "formErrors", results.getAllErrors());
        }

        cliente = this.clienteRepository.save(cliente);

        redirect.addFlashAttribute("globalMessage", "Cliente gravado com sucesso");

        return new ModelAndView("redirect:/" + CLIENTE_URI + "{cliente.id}", "cliente.id", cliente.getId());
    }
    
    @GetMapping("/novo")
    public String createForm(@ModelAttribute Cliente cliente) {
        return CLIENTE_URI + "form";
    }

}
