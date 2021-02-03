package org.example.rest;

import io.swagger.annotations.ApiOperation;
import org.example.model.entity.Hospital;
import org.example.model.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospital")
public class HospitalController {

    @Autowired
    private final HospitalRepository repository;

    public HospitalController(HospitalRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "INSERIR HOSPITAL")
    public Hospital salvarHospital(@RequestBody Hospital hospital){
        return repository.save(hospital);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "LISTAR HOSPITAIS")
    public List<Hospital> listarHospital(){
        return repository.findAll();
    }


}
