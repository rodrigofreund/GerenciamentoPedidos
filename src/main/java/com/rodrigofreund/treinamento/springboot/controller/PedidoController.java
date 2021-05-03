package com.rodrigofreund.treinamento.springboot.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.rodrigofreund.treinamento.springboot.repository.ItemRepository;
import com.rodrigofreund.treinamento.springboot.repository.PedidoRepository;
import com.rodrigofreund.treinamento.springboot.repository.domain.Item;
import com.rodrigofreund.treinamento.springboot.repository.domain.Pedido;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    private static final String PEDIDO_URI = "pedidos/";

    private final PedidoRepository pedidoRepository;
    private final ItemRepository itemRepository;
    private final ClienteRepository clienteRepository;

    public PedidoController(PedidoRepository pedidoRepository, ItemRepository itemRepository,
            ClienteRepository clienteRepository) {
        this.pedidoRepository = pedidoRepository;
        this.itemRepository = itemRepository;
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("/")
    public ModelAndView list() {
        var pedidos = this.pedidoRepository.findAll();
        return new ModelAndView(PEDIDO_URI + "list", "pedidos", pedidos);
    }

    @GetMapping("{id}")
    public ModelAndView view(@PathVariable("id") Long id) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow();
        return new ModelAndView(PEDIDO_URI + "view", "pedido", pedido);
    }

    @GetMapping("alterar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow();
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("todosItens", itemRepository.findAll());
        model.put("todosClientes", clienteRepository.findAll());
        model.put("pedido", pedido);

        return new ModelAndView(PEDIDO_URI + "form", model);

    }

    @GetMapping("remover/{id}")
    public ModelAndView remover(@PathVariable("id") Long id, RedirectAttributes redirect) {

        pedidoRepository.deleteById(id);

        var pedidos = pedidoRepository.findAll();
        ModelAndView modelAndView = new ModelAndView(PEDIDO_URI + "list", "pedidos", pedidos);
        modelAndView.addObject("globalMessage", "Pedido removido com sucesso");
        return modelAndView;
    }

    @PostMapping(params = "form")
    public ModelAndView create(@Valid Pedido pedido, BindingResult results, RedirectAttributes redirect) {
        if (results.hasErrors()) {
            return new ModelAndView(PEDIDO_URI + "form", "formErrors", results.getAllErrors());
        }

        pedido.setValorTotal(
                pedido.getItens().stream().collect(
                        Collectors.summarizingDouble(Item::getPreco)).getSum());

        pedido = this.pedidoRepository.save(pedido);

        redirect.addFlashAttribute("globalMessage", "Cliente gravado com sucesso");

        return new ModelAndView("redirect:/" + PEDIDO_URI + "{pedido.id}", "pedido.id", pedido.getId());
    }

    @GetMapping("/novo")
    public ModelAndView createForm(@ModelAttribute Pedido pedido) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("todosItens", itemRepository.findAll());
        model.put("todosClientes", clienteRepository.findAll());
        return new ModelAndView(PEDIDO_URI + "form", model);
    }

}
