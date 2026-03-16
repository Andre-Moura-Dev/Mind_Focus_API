package mind_focus.Mind_Focus.api.controller;

import mind_focus.Mind_Focus.api.exceptions.DefaultExceptionHandler;
import mind_focus.Mind_Focus.api.dto.UsuarioDTO;
import mind_focus.Mind_Focus.api.model.UsuarioEntity;
import mind_focus.Mind_Focus.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping(value = "/listar")
    public ResponseEntity<List<UsuarioDTO>> listarTodos() throws DefaultExceptionHandler {
        List<UsuarioDTO> listaUsuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(listaUsuarios);
    }

    @PostMapping(value = "/cadastrar")
    public ResponseEntity<UsuarioDTO> cadastrar(@RequestBody UsuarioDTO request) throws DefaultExceptionHandler {
        UsuarioDTO usuario = usuarioService.cadastrarUsuario(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(usuario.getIdUsuario())
                .toUri();
        return ResponseEntity.created(location).body(usuario);
    }

    @PutMapping(value = "/atualizar/{id}")
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable Long id, @RequestBody UsuarioDTO request) throws DefaultExceptionHandler {
        UsuarioDTO usuarioAtualizado = usuarioService.atualizarUsuario(id, request);
        return ResponseEntity.ok().body(usuarioAtualizado);
    }

    @DeleteMapping(value = "/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws DefaultExceptionHandler {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/buscar-por-email/{email}")
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorEmail(@PathVariable String email) throws DefaultExceptionHandler {
        UsuarioDTO usuario = usuarioService.buscarPorEmail(email);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping(value = "/buscar-por-id/{id}")
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorId(@PathVariable Long id) throws DefaultExceptionHandler {
        UsuarioDTO usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }
}
