package org.example.rest;


import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.example.model.entity.Hospital;
import org.example.model.entity.HospitalRecurso;
import org.example.model.entity.Recurso;
import org.example.model.repository.HospitalRecursoRepository;
import org.example.model.repository.HospitalRepository;
import org.example.model.repository.RecursoRepository;
import org.example.rest.dto.HospitalRecursoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/hospitalRecurso")
@RequiredArgsConstructor
public class HospitalRecursoController {

    private final HospitalRepository hospitalRepository;
    private final RecursoRepository recursoRepository;
    private final HospitalRecursoRepository hospitalRecursoRepository;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "INSERIR RECURSOS NOS HOSPITAIS")
    public HospitalRecurso salvar(@RequestBody HospitalRecursoDTO hospitalRecursoDTO){

        Integer HOSPITAL_ID = hospitalRecursoDTO.getIdHospital();
        Integer RECURSO_ID = hospitalRecursoDTO.getIdRecurso();

        Hospital hospital = hospitalRepository.findById(HOSPITAL_ID)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hospital não existe"));

        Recurso recurso = recursoRepository.findById(RECURSO_ID)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Recurso não existe"));

        HospitalRecurso hospitalRecurso = new HospitalRecurso();
        hospitalRecurso.setHospital(hospital);
        hospitalRecurso.setRecurso(recurso);


        return hospitalRecursoRepository.save(hospitalRecurso);

    }
}
