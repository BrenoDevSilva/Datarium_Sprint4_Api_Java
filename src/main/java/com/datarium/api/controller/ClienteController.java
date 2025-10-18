package com.datarium.api.controller;

import com.datarium.api.model.LoginRequest;
import com.datarium.api.model.Cliente;
import com.datarium.api.model.LoginResponse;
import com.datarium.api.service.ClienteService;
import com.datarium.api.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest credenciais) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(credenciais.email(), credenciais.senha());

        Authentication auth = authenticationManager.authenticate(usernamePassword);

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        Cliente clienteAutenticado = clienteService.buscarClientePorEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado após autenticação"));

        String token = tokenService.generateToken(clienteAutenticado);

        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente salvarCliente(@RequestBody @Valid Cliente cliente) {
        cliente.setSenha(passwordEncoder.encode(cliente.getSenha()));
        return clienteService.salvarCliente(cliente);
    }

    @GetMapping
    public List<Cliente> buscarTodosClientes() {
        return clienteService.buscarTodosClientes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarClientePorId(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.buscarClientePorId(id);
        return cliente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable Long id, @RequestBody @Valid Cliente cliente) {
        if (clienteService.buscarClientePorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        cliente.setId(id); // Garante que estamos atualizando o cliente correto
        cliente.setSenha(passwordEncoder.encode(cliente.getSenha())); // Criptografa a senha na atualização também
        Cliente clienteAtualizado = clienteService.salvarCliente(cliente);
        return ResponseEntity.ok(clienteAtualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarCliente(@PathVariable Long id) {
        clienteService.deletarCliente(id);
    }
}