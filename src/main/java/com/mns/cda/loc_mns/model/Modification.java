package com.mns.cda.loc_mns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.view.EquipmentView;
import com.mns.cda.loc_mns.view.ModificationView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
public class Modification {

    @Id // Clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(ModificationView.class)
    protected Integer id;

    @JsonView(ModificationView.class)
    protected Date date;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonView(ModificationView.class)
    protected AppUser author;

    @ManyToOne
    @JoinColumn(name = "equipment_id")
    @JsonView(ModificationView.class)
    protected Equipment equipment;

    @ManyToOne
    @JoinColumn(name = "new_state_id")
    @JsonView(ModificationView.class)
    protected State newState;
}
