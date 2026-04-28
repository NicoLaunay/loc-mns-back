package com.mns.cda.loc_mns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.view.AppUserView;
import com.mns.cda.loc_mns.view.EquipmentView;
import com.mns.cda.loc_mns.view.LoanView;
import com.mns.cda.loc_mns.view.RequestView;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Type {

    @Id // Clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({EquipmentView.class, LoanView.class, RequestView.class, AppUserView.class})
    protected Integer id;

    @NotBlank(message = "Le Nom ne peut pas être vide")
    @JsonView({EquipmentView.class, LoanView.class, RequestView.class, AppUserView.class})
    protected String name;
}
