package com.mns.cda.loc_mns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.view.AppUserView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter // Crée les Getters
@Setter // Crée les Setters
@AllArgsConstructor // Crée un constructeur avec tous les attributs
@NoArgsConstructor // Crée un constructeur sans attributs
@Entity
public class AppUser {

    @Id // Clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(AppUserView.class)
    protected Integer id;

    @NotBlank
    @JsonView(AppUserView.class)
    protected String name;

    @NotBlank
    @JsonView(AppUserView.class)
    protected String surname;

    @NotNull
    @Email
    protected String email;

    @NotNull
    protected String password;

    @ManyToOne
    protected Accreditation accreditation;

    @ManyToOne
    @JsonView(AppUserView.class)
    protected Role role;

}
