package com.mns.cda.loc_mns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.view.AppUserView;
import com.mns.cda.loc_mns.view.EquipmentView;
import com.mns.cda.loc_mns.view.LoanView;
import com.mns.cda.loc_mns.view.RequestView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter // Crée les Getters
@Setter // Crée les Setters
@AllArgsConstructor // Crée un constructeur avec tous les attributs
@NoArgsConstructor // Crée un constructeur sans attributs
@Entity
public class Loan {

    @Id // Clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({LoanView.class, RequestView.class, AppUserView.class})
    protected Integer id;

    @NotNull(message = "La Date de Début ne peut pas être vide")
    @DateTimeFormat
    @JsonView({LoanView.class, RequestView.class, AppUserView.class})
    protected Date startDate;

    @NotNull(message = "La Date de Fin ne peut pas être vide")
    @DateTimeFormat
    @JsonView({LoanView.class, RequestView.class, AppUserView.class})
    protected Date endDate;

    @DateTimeFormat
    @JsonView({LoanView.class, RequestView.class, AppUserView.class})
    protected Date returnDate;

    @NotNull(message = "L'Utilisateur ne peut pas être vide")
    @ManyToOne
    @JsonView({LoanView.class, RequestView.class})
    protected AppUser user;

    @NotNull(message = "L'Equipement ne peut pas être vide")
    @ManyToOne
    @JsonView({LoanView.class, RequestView.class, AppUserView.class})
    protected Equipment equipment;
}
