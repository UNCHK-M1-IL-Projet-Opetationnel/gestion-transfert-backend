package sn.unchk.gestiontransfert.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.unchk.gestiontransfert.model.UtilisateurModel;
import sn.unchk.gestiontransfert.service.dto.request.LoginUserDto;
import sn.unchk.gestiontransfert.service.dto.request.RegisterDTO;
import sn.unchk.gestiontransfert.service.dto.response.LoginResponse;
import sn.unchk.gestiontransfert.service.impl.AuthenticationService;
import sn.unchk.gestiontransfert.service.impl.JwtService;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth ", description = "API pour gérer les users")
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/signup", consumes = {"multipart/form-data"})
    public ResponseEntity<UtilisateurModel> register(@ModelAttribute RegisterDTO registerDTO) {
        UtilisateurModel registeredUser = authenticationService.signup(registerDTO);
        return ResponseEntity.ok(registeredUser);
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        UtilisateurModel authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        return ResponseEntity.ok(loginResponse);
    }
}