package com.mns.cda.loc_mns.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter // Crée les Getters
@Setter // Crée les Setters
@AllArgsConstructor // Crée un constructeur avec tous les attributs
@NoArgsConstructor // Crée un constructeur sans attributs
@Entity
public class Request {

    @Id // Clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @NotNull(message = "La Date ne peut pas être vide")
    protected Date date;

    @NotBlank(message = "Le Contenu ne peut pas être vide")
    protected String content;

    @NotNull(message = "L'Emprunt ne peut pas être vide")
    protected Loan loan;
}
