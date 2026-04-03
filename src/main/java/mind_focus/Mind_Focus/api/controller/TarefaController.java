package mind_focus.Mind_Focus.api.controller;

import mind_focus.Mind_Focus.api.enums.Prioridade;
import mind_focus.Mind_Focus.api.exceptions.DefaultExceptionHandler;
import mind_focus.Mind_Focus.api.dto.TarefaDTO;
import mind_focus.Mind_Focus.api.model.TarefaEntity;
import mind_focus.Mind_Focus.api.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tarefa")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @GetMapping(value = "/listar")
    public ResponseEntity<List<TarefaDTO>> listarTodos() throws DefaultExceptionHandler {
        List<TarefaDTO> listaTarefas = tarefaService.listarTodos();
        return ResponseEntity.ok(listaTarefas);
    }

    @PostMapping(value = "/cadastrar")
    public ResponseEntity<TarefaDTO> cadastrar(@RequestBody TarefaDTO request) throws DefaultExceptionHandler {
        TarefaDTO tarefa = tarefaService.cadastrarTarefa(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(tarefa.getIdTarefa())
                .toUri();
        return ResponseEntity.created(location).body(tarefa);
    }

    @PutMapping(value = "/atualizar/{id}")
    public ResponseEntity<TarefaDTO> atualizar(@PathVariable Long id, @RequestBody TarefaDTO request) throws DefaultExceptionHandler {
        TarefaDTO tarefaAtualizada = tarefaService.atualizarTarefa(id, request);
        return ResponseEntity.ok().body(tarefaAtualizada);
    }

    @DeleteMapping(value = "/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws DefaultExceptionHandler {
        tarefaService.deletarTarefa(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/buscar-por-id/{id}")
    public ResponseEntity<TarefaEntity> buscarTarefaPorId(@PathVariable Long id) throws DefaultExceptionHandler {
        TarefaEntity tarefa = tarefaService.buscarPorId(id);
        return ResponseEntity.ok(tarefa);
    }

    @GetMapping(value = "/tarefas-usuario/{id}")
    public ResponseEntity<List<TarefaDTO>> buscarTarefasPorUsuario(@PathVariable Long id) throws DefaultExceptionHandler {
        List<TarefaDTO> tarefas = tarefaService.listarTarefasPorUsuario(id);
        return ResponseEntity.ok(tarefas);
    }

    @PutMapping(value = "/tarefas-concluidas/{id}")
    public ResponseEntity<TarefaDTO> concluirTarefa(@PathVariable Long id) throws DefaultExceptionHandler {
        TarefaDTO tarefa = tarefaService.concluirTarefa(id);
        return ResponseEntity.ok(tarefa);
    }

    @PutMapping(value = "/tarefas-reabertas/{id}")
    public ResponseEntity<TarefaDTO> reabrirTarefas(@PathVariable Long id) throws DefaultExceptionHandler {
        TarefaDTO tarefa = tarefaService.reabrirTarefa(id);
        return ResponseEntity.ok(tarefa);
    }

    @GetMapping(value = "/tarefas-pendentes")
    public ResponseEntity<List<TarefaDTO>> listarTarefasPendentes() throws DefaultExceptionHandler {
        List<TarefaDTO> tarefas = tarefaService.listarPendentes();
        return ResponseEntity.ok(tarefas);
    }

    @GetMapping(value = "/tarefas-atrasadas")
    public ResponseEntity<List<TarefaDTO>> listarTarefasAtrasadas() throws DefaultExceptionHandler {
        List<TarefaDTO> tarefas = tarefaService.listarAtrasadas();
        return ResponseEntity.ok(tarefas);
    }

    @GetMapping(value = "/prioridade-tarefas/{prioridade}")
    public ResponseEntity<List<TarefaDTO>> filtrarPorPrioridade(@PathVariable Prioridade prioridade) throws DefaultExceptionHandler {
        List<TarefaDTO> tarefas = tarefaService.filtrarPorPrioridade(prioridade);
        return ResponseEntity.ok(tarefas);
    }

    @GetMapping(value = "/data-tarefas/{data}")
    public ResponseEntity<List<TarefaDTO>> buscarPorData(@PathVariable java.time.LocalDate data) throws DefaultExceptionHandler {
        List<TarefaDTO> tarefas = tarefaService.buscarPorData(data);
        return ResponseEntity.ok(tarefas);
    }

    @GetMapping(value = "/contar-tarefas-usuario/{id}")
    public ResponseEntity<Long> contarTarefasPorUsuario(@PathVariable Long id) throws DefaultExceptionHandler {
        long totalTarefas = tarefaService.contarTarefasUsuario(id);
        return ResponseEntity.ok(totalTarefas);
    }
}
