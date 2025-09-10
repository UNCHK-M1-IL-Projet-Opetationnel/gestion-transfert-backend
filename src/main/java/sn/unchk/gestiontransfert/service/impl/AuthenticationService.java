package sn.unchk.gestiontransfert.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sn.unchk.gestiontransfert.model.UtilisateurModel;
import sn.unchk.gestiontransfert.model.enumeration.Role;
import sn.unchk.gestiontransfert.model.enumeration.Statut;
import sn.unchk.gestiontransfert.repository.UtilisateurRepository;
import sn.unchk.gestiontransfert.service.dto.request.LoginUserDto;
import sn.unchk.gestiontransfert.service.dto.request.RegisterDTO;
import sn.unchk.gestiontransfert.utils.QRCodeGenerator;

@Service
public class AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final UtilisateurRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(
            UtilisateurRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UtilisateurModel signup(RegisterDTO input) {
        logger.info("Début de l'inscription pour l'utilisateur: {}", input.getTelephone());

        try {
            if (userRepository.findByTelephone(input.getTelephone()).isPresent()) {
                logger.error("Téléphone {} déjà existant", input.getTelephone());
                throw new RuntimeException("Téléphone déjà existant");
            }

            UtilisateurModel user = createUser(input);

            logger.info("Génération du QR code");
            String qrData = input.getTelephone() + " " + input.getNom() + " " + input.getPrenom();
            BufferedImage qrCode = QRCodeGenerator.generateQRCode(qrData, 250, 250);
            if (qrCode == null) {
                throw new RuntimeException("Échec de la génération du QR code");
            }

            // ✅ Conversion en Base64
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(qrCode, "PNG", baos);
            byte[] qrBytes = baos.toByteArray();
            String qrBase64 = Base64.getEncoder().encodeToString(qrBytes);

            // Sauvegarde en base
            user.setQrcode(qrBase64);
            userRepository.save(user);

            logger.info("Inscription terminée avec succès");
            return user;

        } catch (Exception e) {
            logger.error("Erreur lors de l'inscription: ", e);
            throw new RuntimeException("Erreur lors de l'inscription de l'utilisateur: " + e.getMessage(), e);
        }
    }

    private UtilisateurModel createUser(RegisterDTO input) throws IOException {
        UtilisateurModel user = new UtilisateurModel();
        user.setEmail(input.getEmail());
        user.setTelephone(input.getTelephone());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setNom(input.getNom());
        user.setPrenom(input.getPrenom());
        user.setNumeroPiece(input.getNumeroPiece());

        if (input.getPhoto() != null && !input.getPhoto().isEmpty()) {
            user.setPhoto(input.getPhoto().getBytes());
        }
        if (input.getPhotoPiece() != null && !input.getPhotoPiece().isEmpty()) {
            user.setPhotoPiece(input.getPhotoPiece().getBytes());
        }

        user.setStatut(Statut.ACTIF);
        user.setRole(Role.USER);
        return userRepository.save(user);
    }


    public UtilisateurModel authenticate(LoginUserDto input) {
        try {
            UtilisateurModel user = userRepository.findByTelephone(input.getTelephone())
                    .orElseThrow(() -> new BadCredentialsException("Téléphone ou mot de passe invalide"));

            if (!passwordEncoder.matches(input.getPassword(), user.getPassword())) {
                throw new BadCredentialsException("Téléphone ou mot de passe invalide");
            }

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getTelephone(),
                            input.getPassword()
                    )
            );

            return user;
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Échec d'authentification : " + e.getMessage());
        }
    }
}
