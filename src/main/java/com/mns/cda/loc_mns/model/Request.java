package com.mns.cda.loc_mns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.view.LoanView;
import com.mns.cda.loc_mns.view.RequestView;
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
    @JsonView({LoanView.class, RequestView.class})
    protected Integer id;

    @NotNull(message = "La Date ne peut pas être vide")
    @JsonView({LoanView.class, RequestView.class})
    protected Date date;

    @NotBlank(message = "Le Contenu ne peut pas être vide")
    @JsonView({LoanView.class, RequestView.class})
    protected String content;

    @ManyToOne
    @NotNull(message = "L'Emprunt ne peut pas être vide")
    @JsonView(RequestView.class)
    protected Loan loan;
}
