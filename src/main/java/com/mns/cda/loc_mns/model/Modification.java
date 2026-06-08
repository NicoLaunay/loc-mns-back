package com.mns.cda.loc_mns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.view.ModificationView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Getter // Crée les Getters
@Setter // Crée les Setters
@AllArgsConstructor // Crée un constructeur avec tous les attributs
@NoArgsConstructor // Crée un constructeur sans attributs
@Entity
public class Modification {

    @Id // Clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(ModificationView.class)
    protected Integer id;

    @NotNull(message = "La date ne peut pas être vide")
    @DateTimeFormat
    @JsonView(ModificationView.class)
    protected LocalDate date;

    @ManyToOne
    @NotNull(message = "L'auteur ne peut pas être vide")
    @JoinColumn(name = "author_id")
    @JsonView(ModificationView.class)
    protected AppUser author;

    @ManyToOne
    @NotNull(message = "L'équipement ne peut pas être vide")
    @JoinColumn(name = "equipment_id")
    @JsonView(ModificationView.class)
    protected Equipment equipment;

    @ManyToOne
    @NotNull(message = "Le nouvel état ne peut pas être vide")
    @JoinColumn(name = "new_state_id")
    @JsonView(ModificationView.class)
    protected State newState;
}
