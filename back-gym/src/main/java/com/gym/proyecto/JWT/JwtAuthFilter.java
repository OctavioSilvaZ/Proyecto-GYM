package com.gym.proyecto.JWT;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.gym.proyecto.models.PersonalModel;
import com.gym.proyecto.services.PersonalService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private PersonalService personalService;

    @Autowired
    private JwtService jwtService;

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String correo = null;

        // Veridica si existe el Header autorizacion en la petición y comienza por
        // Bearer
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            //Guarda el token quitando la palabra Bearer
            token = authHeader.substring(7);
            //Estrae el correo del token
            correo = jwtService.extractCorreo(token);
        }

        //Verifica que el usuario no este autenticado y que exista en la base de datos
        if (correo != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            PersonalModel userDetails = this.personalService.buscarPorCorreo(correo);

            //Valida el token
            if (this.jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                        null, null);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }

}
