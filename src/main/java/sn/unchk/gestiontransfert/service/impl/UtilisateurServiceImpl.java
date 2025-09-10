package sn.unchk.gestiontransfert.service.impl;

import org.springframework.stereotype.Service;
import sn.unchk.gestiontransfert.model.UtilisateurModel;
import sn.unchk.gestiontransfert.repository.UtilisateurRepository;
import sn.unchk.gestiontransfert.service.UtilisateurService;

import java.util.Optional;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository userRepository;
    private final JwtService jwtService;

    public UtilisateurServiceImpl(UtilisateurRepository userRepository, JwtService jwtService) {
        super();
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public Optional<UtilisateurModel> getUserProfile(String telephone ) {
        return userRepository.findByTelephone(telephone);
    }
}