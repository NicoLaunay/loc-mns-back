package com.mns.cda.loc_mns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.view.EquipmentView;
import com.mns.cda.loc_mns.view.LoanView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter // Crée les Getters
@Setter // Crée les Setters
@AllArgsConstructor // Crée un constructeur avec tous les attributs
@NoArgsConstructor // Crée un constructeur sans attributs
@Entity
public class Loan {

    @Id // Clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(LoanView.class)
    protected Integer id;

    @NotNull
    @DateTimeFormat
    @JsonView(LoanView.class)
    protected Date startDate;

    @NotNull
    @DateTimeFormat
    @JsonView(LoanView.class)
    protected Date endDate;

    @DateTimeFormat
    @JsonView(LoanView.class)
    protected Date returnDate;

    @NotNull
    @ManyToOne
    @JsonView(LoanView.class)
    protected AppUser user;

    @NotNull
    @ManyToOne
    @JsonView(LoanView.class)
    protected Equipment equipment;




}
