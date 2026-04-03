package mind_focus.Mind_Focus.api.controller;

import mind_focus.Mind_Focus.api.exceptions.DefaultExceptionHandler;
import mind_focus.Mind_Focus.api.dto.SessaoFocoDTO;
import mind_focus.Mind_Focus.api.model.SessaoFocoEntity;
import mind_focus.Mind_Focus.api.service.SessaoFocoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/sessao-foco")
public class SessaoFocoController {

    @Autowired
    private SessaoFocoService sessaoFocoService;

    @GetMapping(value = "/listar")
    public ResponseEntity<List<SessaoFocoDTO>> listarTodos() throws DefaultExceptionHandler {
        List<SessaoFocoDTO> listaSessoesFoco = sessaoFocoService.listarTodos();
        return ResponseEntity.ok(listaSessoesFoco);
    }

    @PostMapping(value = "/cadastrar")
    public ResponseEntity<SessaoFocoDTO> cadastrar(@RequestBody SessaoFocoDTO request) {
        SessaoFocoDTO sessaoFoco = sessaoFocoService.cadastrarSessaoFoco(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(sessaoFoco.getIdSessaoFoco())
                .toUri();
        return ResponseEntity.created(location).body(sessaoFoco);
    }

    @PutMapping(value = "/atualizar/{id}")
    public ResponseEntity<SessaoFocoDTO> atualizar(@PathVariable Long id, @RequestBody SessaoFocoDTO request) throws DefaultExceptionHandler {
        SessaoFocoDTO sessaoFocoAtualizada = sessaoFocoService.atualizarSessaoFoco(id, request);
        return ResponseEntity.ok().body(sessaoFocoAtualizada);
    }

    @DeleteMapping(value = "/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws DefaultExceptionHandler {
        sessaoFocoService.deletarSessaoFoco(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/buscar-por-id/{id}")
    public ResponseEntity<SessaoFocoEntity> buscarSessaoPorId(@PathVariable Long id) throws DefaultExceptionHandler {
        SessaoFocoEntity sessaoFoco = sessaoFocoService.buscarPorId(id);
        return ResponseEntity.ok(sessaoFoco);
    }

    @GetMapping(value = "/sessoes-foco-usuario/{idUsuario}")
    public ResponseEntity<List<SessaoFocoDTO>> listarSessaoFocoUsuario(@PathVariable Long idUsuario) throws DefaultExceptionHandler {
        List<SessaoFocoDTO> listaSessoesFoco = sessaoFocoService.listarSessoesFocoPorUsuario(idUsuario);
        return ResponseEntity.ok(listaSessoesFoco);
    }

    @GetMapping(value = "/data-sessoes-foco/{data}")
    public ResponseEntity<List<SessaoFocoDTO>> listarSessaoPorData(@PathVariable String data) throws DefaultExceptionHandler {
        LocalDate localDate = LocalDate.parse(data);
        List<SessaoFocoDTO> listaDataSessoes = sessaoFocoService.listarPorData(localDate);
        return ResponseEntity.ok(listaDataSessoes);
    }

    @GetMapping(value = "/total-minutos-usuario/{idUsuario}")
    public ResponseEntity<Integer> totalMinutosFoco(@PathVariable Long idUsuario) throws DefaultExceptionHandler {
        Integer totalMinutos = sessaoFocoService.totalMinutosFoco(idUsuario);
        return ResponseEntity.ok(totalMinutos != null ? totalMinutos : 0);
    }
}
