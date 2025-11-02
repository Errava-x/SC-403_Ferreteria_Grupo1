/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ferreteria.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ferreteria.service.CarritoService;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @GetMapping({ "", "/", "/listado" })
    public String ver(Model model, HttpSession session) {
        model.addAttribute("items", carritoService.getItems(session));
        model.addAttribute("total", carritoService.getTotal(session));
        return "carrito/listado";
    }


    @PostMapping("/agregar")
    public String agregar(@RequestParam Long productoId,
                          @RequestParam(defaultValue = "1") Integer cantidad,
                          HttpSession session,
                          RedirectAttributes ra) {
        carritoService.add(session, productoId, cantidad);
        ra.addFlashAttribute("todoOk", "Producto agregado al carrito.");
        return "redirect:/carrito";
    }

    @PostMapping("/actualizar")
    public String actualizar(@RequestParam Long productoId,
                             @RequestParam Integer cantidad,
                             HttpSession session) {
        carritoService.update(session, productoId, cantidad);
        return "redirect:/carrito";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Long productoId,
                           HttpSession session,
                           RedirectAttributes ra) {
        carritoService.remove(session, productoId);
        ra.addFlashAttribute("todoOk", "Producto eliminado.");
        return "redirect:/carrito";
    }

    @PostMapping("/vaciar")
    public String vaciar(HttpSession session) {
        carritoService.clear(session);
        return "redirect:/carrito";
    }
    
    //Test
    @GetMapping("/demo")
    public String demo(HttpSession session, RedirectAttributes ra) {
        carritoService.seedDemo(session);
        ra.addFlashAttribute("todoOk", "Se cargó un carrito de ejemplo.");
        return "redirect:/carrito";
    }
}
