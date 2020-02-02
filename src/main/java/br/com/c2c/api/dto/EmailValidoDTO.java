package br.com.c2c.api.dto;

import java.io.Serializable;

public class EmailValidoDTO implements Serializable {
    private boolean valido;
    private String email;

    public boolean isValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
