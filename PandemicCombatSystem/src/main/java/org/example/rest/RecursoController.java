package org.example.rest;


import io.swagger.annotations.ApiOperation;
import org.example.model.entity.Recurso;
import org.example.model.repository.RecursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recurso")
public class RecursoController {

    @Autowired
    private final RecursoRepository repository;

    public RecursoController(RecursoRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "INSERIR RECURSOS")
    public Recurso salvarRecurso(@RequestBody Recurso recurso){
        return repository.save(recurso);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "LISTAR TODOS OS RECURSOS")
    public List<Recurso> listarHospital(){
        return repository.findAll();
    }

}
