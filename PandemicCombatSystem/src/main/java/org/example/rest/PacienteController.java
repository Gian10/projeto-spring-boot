package org.example.rest;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.example.model.entity.Hospital;
import org.example.model.entity.Paciente;
import org.example.model.repository.HospitalRepository;
import org.example.model.repository.PacienteRepository;
import org.example.rest.dto.PacienteDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/paciente")
@RequiredArgsConstructor
public class PacienteController {


    private final HospitalRepository hospitalRepository;
    private final PacienteRepository repository;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "INSERIR PACIENTE")
    public Paciente salvarPaciente(@RequestBody PacienteDTO pacienteDTO){

        Integer id_hospital = pacienteDTO.getIdHospital();

        Instant instant = Instant.now();
        long timeStampMillis = instant.toEpochMilli();

        Hospital hospital = hospitalRepository.findById(id_hospital)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hospital não existe"));

        Paciente paciente = new Paciente();
        paciente.setNome(pacienteDTO.getNome());
        paciente.setHospital(hospital);
       paciente.setData_hora_registrado(timeStampMillis);

        return repository.save(paciente);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "LISTAR PACIENTES")
    public List<Paciente> listarPaciente(){
        return repository.findAll();
    }
}
