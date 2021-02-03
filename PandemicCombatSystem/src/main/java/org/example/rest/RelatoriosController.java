package org.example.rest;


import io.swagger.annotations.ApiOperation;
import org.example.DAO.Querys;
import org.example.model.entity.HistoricoIntercambioRecursoHospital;
import org.example.model.entity.RequestPutItemsHospital;
import org.example.model.repository.HistoricoIntercambioRecursoHospitalRepository;
import org.example.rest.dto.HospitaisComOcupacao;
import org.example.rest.dto.HospitalOcupacaoPorTempo;
import org.example.rest.dto.IntercambioRecurso;
import org.example.rest.dto.MediaRecursoHospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/relatorios")
public class RelatoriosController {

    @Autowired
    private final Querys querys;
    private final HistoricoIntercambioRecursoHospitalRepository historicoIntercambioRecursoHospital;

    public RelatoriosController(Querys query, HistoricoIntercambioRecursoHospitalRepository historicoIntercambioRecursoHospital ) {
        this.querys = query;
        this.historicoIntercambioRecursoHospital = historicoIntercambioRecursoHospital;
    }
    @GetMapping
    @RequestMapping("/ocupacaoMaiorHospital")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "LISTAR HOSPITAIS COM MAIORES OCUPAÇÃO")
    public List<HospitaisComOcupacao> listarMaioresOcucacao(){
        List<HospitaisComOcupacao> res = querys.ocupacaoMaiores();
        return res;
    }

    @GetMapping
    @RequestMapping("/ocupacaoMenorHospital")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "LISTAR HOSPITAIS COM MENORES OCUPAÇÃO")
    public List<HospitaisComOcupacao> listarMenoresOcucacao(){
        List<HospitaisComOcupacao> res = querys.ocupacaoMenores();
        return res;
    }

    @GetMapping
    @RequestMapping("/mediaRecursoHospital")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "LISTAR MEDIA DE RECURSOS POR HOSPITAL")
    public List<MediaRecursoHospital> listarMediaRecursoHospital(){
        List<MediaRecursoHospital> res = querys.mediaRecursoHospital();
        return res;
    }


    @GetMapping
    @RequestMapping("/hospitalMaiorOcupacaoMaisTempo")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "HOSPITAL COM MAIS OCUPAÇÃO COM MAIS TEMPO")
    public List<HospitalOcupacaoPorTempo> maiorOcupacaoMaisTempo(){
        List<HospitalOcupacaoPorTempo> res = querys.hospitalMaiorOcupacaoMaisTempo();
        return res;
    }


    @GetMapping
    @RequestMapping("/hospitalMenorOcupacaoMaisTempo")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "HOSPITAL COM MAIS OCUPAÇÃO COM MAIS TEMPO")
    public List<HospitalOcupacaoPorTempo> menorOcupacaoMaisTempo(){
        List<HospitalOcupacaoPorTempo> res = querys.hospitalMenorOcupacaoMaisTempo();
        return res;

    }

    @GetMapping
    @RequestMapping("/hospitalRecurso")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "LISTAR HOSPITAIS COM SEUS RECURSOS")
    public List<IntercambioRecurso> listarHospitaisrecurso(){
        List<IntercambioRecurso> res = querys.getHospitalRecursos();
        return res;
    }

    // NÃO FINALIZEI A FUNCIONALIDADE DE TROCA DE ITEM PELA MESMA PONTUAÇÃO
    //OBS: SÓ PODE RELAIZAR A TROCA 2 ITENS POR HOSPITAL
    @PostMapping
    public String postHospitaisrecurso(@RequestBody RequestPutItemsHospital req){

        // VERIFICANDO OS PRIMEIROS RECURSOS DO PRIMEIRO HOSPITAL
        List<Integer> itemHospital1 = new ArrayList<Integer>();
        Integer idHospital;
        itemHospital1 = req.getItemHospital1();

        Integer idRecurso1 = itemHospital1.get(0);
        Integer idRecurso2 = itemHospital1.get(1);
        boolean verificarIdRecursoPerteceHospital = querys.verificarPertenceHospital(idRecurso1,idRecurso2);

        // VERIFICANDO SE OS RECURSOS PERTENCEM AO HOSPITAL
        if(!verificarIdRecursoPerteceHospital)
            return  "RECURSO NÃO PERTENCE A ESTÉ HOSPITAL";

        // VERIFICANDO SE ESTES RECUSOS EXISTEM
        Integer verificarIdRecurso = querys.verificarIdRecurso(idRecurso1,idRecurso2);
        if(verificarIdRecurso == 2){
            idHospital = querys.pegarIdHospitalRecurso(idRecurso1,idRecurso2);

        }else{
            return "RECURSO NÃO EXISTE";
        }

/////////////////////////////////////////////////////////////////////////////////////////////////

        // VERIFICANDO OS PRIMEIROS RECURSO DO SEGUNDO HOSPITAL
        List<Integer> itemHospital2 = new ArrayList<Integer>();
        itemHospital2 = req.getItemHospital2();

        Integer idHospital2;

        Integer idRecurso3 = itemHospital2.get(0);
        Integer idRecurso4 = itemHospital2.get(1);
        boolean verificarIdRecursoPerteceHospital2 = querys.verificarPertenceHospital(idRecurso3,idRecurso4);

        // VERIFICANDO SE OS RECURSOS PERTENCEM AO HOSPITAL
        if(!verificarIdRecursoPerteceHospital2)
            return "RECURSO NÃO PERTENCE A ESTE HOSPITAL";

        // VERIFICANDO SE ESTES RECUSOS EXISTEM
        Integer verificarIdRecurso2 = querys.verificarIdRecurso(idRecurso3,idRecurso4);
        if(verificarIdRecurso2 == 2){
            idHospital2 = querys.pegarIdHospitalRecurso(idRecurso3,idRecurso4);
        }else{
            return "RECURSO NÃO EXISTE";
        }

        // ATUALIZAR TABLE DE HISPOTAL_RECURSO
        querys.atualizarIdRecurso(idHospital, idRecurso3, idRecurso4);
        querys.atualizarIdRecurso(idHospital2, idRecurso1, idRecurso2);

        //INSERIR PRIMEIRO OBJETO DE ITEM NA TABELA HISTORICO
        // INSERINDO PRIMEIRO ITEM DE TROCA
        HistoricoIntercambioRecursoHospital historicoItem1 = new HistoricoIntercambioRecursoHospital();
        Instant instant = Instant.now();
        long timeStampMillis = instant.toEpochMilli();

        historicoItem1.setData_hora_registrado(timeStampMillis);
        historicoItem1.setDeHospital(idHospital);
        historicoItem1.setParaHospital(idHospital2);
        historicoItem1.setItemId(idRecurso1);
        historicoIntercambioRecursoHospital.save(historicoItem1);

        // INSERINDO SEGUNDO ITEM DE TROCA
        HistoricoIntercambioRecursoHospital historicoItem2 = new HistoricoIntercambioRecursoHospital();
        historicoItem2.setData_hora_registrado(timeStampMillis);
        historicoItem2.setDeHospital(idHospital);
        historicoItem2.setParaHospital(idHospital2);
        historicoItem2.setItemId(idRecurso2);
        historicoIntercambioRecursoHospital.save(historicoItem2);

        //INSERIR SEGUNDO OBJETO DE ITEM NA TABELA HISTORICO
        // INSERINDO PRIMEIRO ITEM DE TROCA
        HistoricoIntercambioRecursoHospital historico = new HistoricoIntercambioRecursoHospital();
        historico.setData_hora_registrado(timeStampMillis);
        historico.setDeHospital(idHospital2);
        historico.setParaHospital(idHospital);
        historico.setItemId(idRecurso3);
        historicoIntercambioRecursoHospital.save(historico);

        // INSERINDO SEGUNDO ITEM DE TROCA
        HistoricoIntercambioRecursoHospital historico1 = new HistoricoIntercambioRecursoHospital();
        historico1.setData_hora_registrado(timeStampMillis);
        historico1.setDeHospital(idHospital2);
        historico1.setParaHospital(idHospital);
        historico1.setItemId(idRecurso4);
        historicoIntercambioRecursoHospital.save(historico1);

        return "SALVO HISTORICO";
    }



}

