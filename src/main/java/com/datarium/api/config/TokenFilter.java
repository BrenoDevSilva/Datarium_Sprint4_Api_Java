package com.datarium.api.config;

import com.datarium.api.model.Cliente;
import com.datarium.api.service.ClienteService;
import com.datarium.api.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class TokenFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ClienteService clienteService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = getTokenFromHeader(request);

        if (token != null && tokenService.isTokenValid(token)) {
            Long clienteId = tokenService.getClienteId(token);
            Optional<Cliente> clienteOptional = clienteService.buscarClientePorId(clienteId);

            if (clienteOptional.isPresent()) {
                Cliente cliente = clienteOptional.get();
                // Informa ao Spring que o usuário está autenticado
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(cliente, null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromHeader(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7); // Remove o prefixo "Bearer "
        }
        return null;
    }
}