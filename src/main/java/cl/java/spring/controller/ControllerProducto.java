package cl.java.spring.controller;

import cl.java.spring.model.Categoria;
import cl.java.spring.model.Producto;
import cl.java.spring.repository.CategoriaRepository;
import cl.java.spring.repository.ProductoRepository;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/producto")
public class ControllerProducto {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping("/nuevo")
    public String nuevo(Producto producto, Model modelo) {
        List<Categoria> lista = categoriaRepository.findAll();
        modelo.addAttribute("categorias", lista);
        return "producto/form";

    }

    @GetMapping("editar/{id}")
    public String editar(@PathVariable(name = "id") Producto producto, Model model) {
        List<Categoria> listaCategoria = categoriaRepository.findAll();
        model.addAttribute("categorias", listaCategoria);
        model.addAttribute("producto", producto);
        return "producto/form";
    }

    @PostMapping("/procesar")
    public String procesar(@Valid Producto producto, BindingResult informeValidar) {
        if (informeValidar.hasErrors()) {
            return "producto/form";
        }
        productoRepository.saveAndFlush(producto);
        return "redirect:/producto/listado";

    }

    @GetMapping("/listado")
    public String listar(Model modelo) {
        List<Producto> listaProducto = productoRepository.findAll();
        modelo.addAttribute("productos", listaProducto);
        return "producto/listado";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        productoRepository.deleteById(id);
        return "redirect:/producto/listado";
    }
}
