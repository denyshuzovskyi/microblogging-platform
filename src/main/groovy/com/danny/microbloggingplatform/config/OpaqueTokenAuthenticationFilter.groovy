package com.danny.microbloggingplatform.config

import com.danny.microbloggingplatform.model.Token
import com.danny.microbloggingplatform.repository.TokenRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class OpaqueTokenAuthenticationFilter extends OncePerRequestFilter{
    @Autowired
    TokenRepository tokenRepository

    @Autowired
    UserDetailsService userDetailsService

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequest(request)

        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Token opaqueToken = tokenRepository.findByToken(token).orElse(null)

            if (Objects.nonNull(opaqueToken) && opaqueToken.expiresAt.after(new Date())) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(opaqueToken.username)
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, null)

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request))

                SecurityContextHolder.getContext().setAuthentication(authentication)
            }
        }

        filterChain.doFilter(request, response)
    }

    private static String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization")
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7)
        }
        return null
    }
}
