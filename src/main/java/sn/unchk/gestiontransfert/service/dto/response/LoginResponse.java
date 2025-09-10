package sn.unchk.gestiontransfert.service.dto.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}