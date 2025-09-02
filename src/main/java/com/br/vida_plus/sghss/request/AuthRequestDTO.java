package com.br.vida_plus.sghss.request;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class AuthRequestDTO {
    private String username;
    private String email;
    private String password;
}
