package org.example.DAO;

import org.example.model.entity.Hospital;
import org.example.rest.dto.HospitaisComOcupacao;
import org.example.rest.dto.HospitalOcupacaoPorTempo;
import org.example.rest.dto.IntercambioRecurso;
import org.example.rest.dto.MediaRecursoHospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface Querys extends CrudRepository<Hospital, Integer>, JpaRepository<Hospital, Integer> {


    @Query(value = "SELECT H.NOME, HR.ID_HOSPITAL, HR.ID_RECURSO, R.PONTOS FROM HOSPITAL_RECURSO AS HR INNER JOIN HOSPITAL AS H ON H.ID = HR.ID_HOSPITAL INNER JOIN RECURSO AS R ON R.ID = HR.ID_RECURSO", nativeQuery = true)
    List<IntercambioRecurso> getHospitalRecursos();

    @Query(value="SELECT PORCENTAGEM, NOME FROM (SELECT MIN(DATA_HORA_REGISTRADO), COUNT(*) AS N_PACIENTES, (COUNT(*)*1.0/MAX(HOSPITAL.VAGAS_TOTAIS)) * 100 AS PORCENTAGEM, HOSPITAL.NOME FROM PACIENTE INNER JOIN HOSPITAL ON PACIENTE.ID_HOSPITAL=HOSPITAL.ID GROUP BY PACIENTE.ID_HOSPITAL) WHERE PORCENTAGEM < 90", nativeQuery = true)
    List<HospitalOcupacaoPorTempo> hospitalMenorOcupacaoMaisTempo();

    @Query(value="SELECT PORCENTAGEM, NOME FROM (SELECT MIN(DATA_HORA_REGISTRADO), COUNT(*) AS N_PACIENTES, (COUNT(*)*1.0/MAX(HOSPITAL.VAGAS_TOTAIS)) * 100 AS PORCENTAGEM, HOSPITAL.NOME FROM PACIENTE INNER JOIN HOSPITAL ON PACIENTE.ID_HOSPITAL=HOSPITAL.ID GROUP BY PACIENTE.ID_HOSPITAL) WHERE PORCENTAGEM > 90", nativeQuery = true)
    List<HospitalOcupacaoPorTempo> hospitalMaiorOcupacaoMaisTempo();

    @Query(value="SELECT * FROM (SELECT T.ID_RECURSO ,T.ID_HOSPITAL, COUNT(T.ID_RECURSO) AS MEDIA_RECURSO_HOSPITAL FROM HOSPITAL_RECURSO AS T GROUP BY ID_RECURSO)", nativeQuery = true)
    List<MediaRecursoHospital> mediaRecursoHospital();

    @Query(value="SELECT NOME, NPANCIENTS * 100 / VAGAS_TOTAIS AS PORCENTAGEM FROM  (SELECT H.ID, H.NOME, VAGAS_TOTAIS, (SELECT COUNT(H.ID) FROM PACIENTE WHERE ID_HOSPITAL = H.ID )  AS NPANCIENTS FROM HOSPITAL  AS H) WHERE NPANCIENTS * 100 / VAGAS_TOTAIS < 90", nativeQuery = true)
    List<HospitaisComOcupacao> ocupacaoMenores();

    @Query(value="SELECT NOME, NPANCIENTS * 100 / VAGAS_TOTAIS AS PORCENTAGEM FROM  (SELECT H.ID, H.NOME, VAGAS_TOTAIS, (SELECT COUNT(H.ID) FROM PACIENTE WHERE ID_HOSPITAL = H.ID )  AS NPANCIENTS FROM HOSPITAL  AS H) WHERE NPANCIENTS * 100 / VAGAS_TOTAIS < 90", nativeQuery = true)
    List<HospitaisComOcupacao> ocupacaoMaiores();


    // QUERYS RELACIONADAS AO RELATÓRIO
    @Query(value="SELECT COUNT(DISTINCT ID_HOSPITAL) = 1  FROM HOSPITAL_RECURSO AS HR WHERE HR.ID_RECURSO IN (?,?)", nativeQuery = true)
    boolean verificarPertenceHospital(Integer idRecurso1, Integer idRecurso2);

    @Query(value="SELECT COUNT(ID_RECURSO) FROM HOSPITAL_RECURSO AS HR WHERE HR.ID_RECURSO IN (?,?)", nativeQuery = true)
    Integer verificarIdRecurso(Integer idRecurso1, Integer idRecurso2);

    @Query(value="SELECT ID_HOSPITAL  FROM HOSPITAL_RECURSO AS HR WHERE HR.ID_RECURSO IN (?,?)GROUP BY ID_HOSPITAL ", nativeQuery = true)
    Integer pegarIdHospitalRecurso(Integer idRecurso1, Integer idRecurso2);

    @Modifying
    @Query(value="UPDATE HOSPITAL_RECURSO SET ID_HOSPITAL = ? WHERE ID_RECURSO IN(?,?)", nativeQuery = true)
    Integer atualizarIdRecurso(Integer idHospital, Integer idRecurso1, Integer idRecurso2);


}

