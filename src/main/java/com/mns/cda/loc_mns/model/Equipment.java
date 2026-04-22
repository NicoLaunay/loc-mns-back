package com.mns.cda.loc_mns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.view.EquipmentView;
import com.mns.cda.loc_mns.view.LoanView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter // Crée les Getters
@Setter // Crée les Setters
@AllArgsConstructor // Crée un constructeur avec tous les attributs
@NoArgsConstructor // Crée un constructeur sans attributs
@Entity
public class Equipment {

    @Id // Clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({EquipmentView.class, LoanView.class})
    protected Integer id;

    @NotBlank
    @JsonView({EquipmentView.class, LoanView.class})
    protected String name;

    @JsonView({EquipmentView.class, LoanView.class})
    protected String condition;

    @ManyToOne
    @JsonView({EquipmentView.class, LoanView.class})
    protected Model model;

    @ManyToOne
    @JsonView({EquipmentView.class, LoanView.class})
    protected Location location;
}
