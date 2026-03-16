package mind_focus.Mind_Focus.api.controller;

import mind_focus.Mind_Focus.api.exceptions.DefaultExceptionHandler;
import mind_focus.Mind_Focus.api.dto.DespejoCerebralDTO;
import mind_focus.Mind_Focus.api.model.DespejoCerebralEntity;
import mind_focus.Mind_Focus.api.service.DespejoCerebralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/despejo-cerebral")
public class DespejoCerebralController {

    @Autowired
    private DespejoCerebralService despejoCerebralService;

    @GetMapping(value = "/listar")
    public ResponseEntity<List<DespejoCerebralDTO>> listarTodos() throws DefaultExceptionHandler {
        List<DespejoCerebralDTO> listaDespejosCerebrais = despejoCerebralService.listarTodos();
        return ResponseEntity.ok(listaDespejosCerebrais);
    }

    @PostMapping(value = "/cadastrar")
    public ResponseEntity<DespejoCerebralDTO> cadastrar(@RequestBody DespejoCerebralDTO request) throws DefaultExceptionHandler {
        DespejoCerebralDTO despejoCerebral = despejoCerebralService.cadastrarDespejoCerebral(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(despejoCerebral.getIdDespejoCerebral())
                .toUri();
        return ResponseEntity.created(location).body(despejoCerebral);
    }

    @PutMapping(value = "/atualizar/{id}")
    public ResponseEntity<DespejoCerebralDTO> atualizar(@PathVariable Long id, @RequestBody DespejoCerebralDTO request) throws DefaultExceptionHandler {
        DespejoCerebralDTO despejoCerebralAtualizado = despejoCerebralService.atualizarDespejoCerebral(id, request);
        return ResponseEntity.ok().body(despejoCerebralAtualizado);
    }

    @DeleteMapping(value = "/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws DefaultExceptionHandler {
        despejoCerebralService.deletarDespejoCerebral(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/buscar-por-id/{id}")
    public ResponseEntity<DespejoCerebralEntity> buscarDespejoCerebralPorId(@PathVariable Long id) throws DefaultExceptionHandler {
        DespejoCerebralEntity despejoCerebral = despejoCerebralService.buscarPorId(id);
        return ResponseEntity.ok(despejoCerebral);
    }
}
