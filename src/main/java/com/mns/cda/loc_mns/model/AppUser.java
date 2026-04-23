package com.mns.cda.loc_mns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.view.AppUserView;
import com.mns.cda.loc_mns.view.LoanView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;


@Getter // Crée les Getters
@Setter // Crée les Setters
@AllArgsConstructor // Crée un constructeur avec tous les attributs
@NoArgsConstructor // Crée un constructeur sans attributs
@Entity
public class AppUser {

    @Id // Clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({AppUserView.class, LoanView.class})
    protected Integer id;

    @NotBlank(message = "Le Prénom ne peut pas être vide")
    @JsonView({AppUserView.class, LoanView.class})
    protected String name;

    @NotBlank(message = "Le Nom de Famille ne peut pas être vide")
    @JsonView({AppUserView.class, LoanView.class})
    protected String surname;

    @NotBlank(message = "L'Email ne peut pas être vide")
    @Email(message = "Le format de l'Email n'est pas valide")
    @JsonView({AppUserView.class, LoanView.class})
    protected String email;

    @NotBlank(message = "Le Mot de Passe ne peut pas être vide")
    @Size(min = 8, max = 20, message = "Le Mot de Passe doit faire entre 8 et 20 caractères")
    protected String password;

    @ManyToOne
    @JsonView(LoanView.class)
    protected Accreditation accreditation;

    @ManyToOne
    @JsonView({AppUserView.class, LoanView.class})
    protected Role role;
}
