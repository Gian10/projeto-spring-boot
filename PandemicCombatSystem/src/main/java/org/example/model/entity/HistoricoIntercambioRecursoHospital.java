package org.example.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoricoIntercambioRecursoHospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private long data_hora_registrado;

    @Column(nullable = false)
    private Integer deHospital;

    @Column(nullable = false)
    private Integer paraHospital;

    @Column(nullable = false)
    private Integer itemId;


}
