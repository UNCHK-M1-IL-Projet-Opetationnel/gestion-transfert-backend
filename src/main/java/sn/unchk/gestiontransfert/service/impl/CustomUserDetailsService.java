package sn.unchk.gestiontransfert.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sn.unchk.gestiontransfert.repository.UtilisateurRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UtilisateurRepository compteRepository;

    public CustomUserDetailsService(UtilisateurRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Retourner directement l'entité User qui implémente déjà UserDetails
        return compteRepository.findByTelephone(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
