package cl.java.spring.controller;

import cl.java.spring.model.Categoria;
import cl.java.spring.repository.CategoriaRepository;
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
@RequestMapping("/categoria")
public class ControllerCategoria {

    @Autowired
    private CategoriaRepository categoriaRepository;

   
	
	@GetMapping("/nuevo")
	public String nuevo(Categoria categoria) {
		return "categoria/form";
	}
	
	@GetMapping("editar/{id}")
	public String editar(@PathVariable(name="id") Categoria categoria, Model model) {
		model.addAttribute("categoria", categoria);
		return "categoria/form";
	}

	@PostMapping("/procesar")
	public String procesar(@Valid Categoria categoria, BindingResult informeValidar) {
		if(informeValidar.hasErrors()) {
			return "categoria/form";
		}
		categoriaRepository.saveAndFlush(categoria);
		return "redirect:/categoria/listado";	
		
	}
	
	@GetMapping("/listado")
	public String listar(Model modelo) {
		List<Categoria> listaCategoria = categoriaRepository.findAll();
		modelo.addAttribute("categorias", listaCategoria);
		return "categoria/listado";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id) {
		categoriaRepository.deleteById(id);
		return "redirect:/categoria/listado";
	}
	
}
