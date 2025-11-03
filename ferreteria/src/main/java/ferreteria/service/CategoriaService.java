/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ferreteria.service;

import ferreteria.domain.Categoria;
import ferreteria.repository.CategoriaRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jairell Bonilla
 */
@Service
public class CategoriaService {
    private final CategoriaRepository repo;
    public CategoriaService(CategoriaRepository repo) { this.repo = repo; }

    public List<Categoria> listarActivas() { 
        return repo.findByEstadoTrue(); 
    }
}
