package com.br.vida_plus.sghss.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document(collection = "users")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class User {

    @Id
    private String id;

    @NotNull
    @NotBlank
    private String username;

    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    private String password;

    private List<RoleName> roles;
}
