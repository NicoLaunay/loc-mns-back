package com.mns.cda.loc_mns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.view.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter // Crée les Getters
@Setter // Crée les Setters
@AllArgsConstructor // Crée un constructeur avec tous les attributs
@NoArgsConstructor // Crée un constructeur sans attributs
@Entity
public class Equipment {

    @Id // Clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({EquipmentView.class, LoanView.class, RequestView.class, AppUserView.class, ModificationView.class})
    protected Integer id;

    @NotBlank(message = "Le Nom ne peut pas être vide")
    @JsonView({EquipmentView.class, LoanView.class, RequestView.class, AppUserView.class, ModificationView.class})
    protected String name;

    @JsonView({EquipmentView.class, LoanView.class, RequestView.class, AppUserView.class, ModificationView.class})
    protected String condition;

    @ManyToOne
    @NotNull(message = "Le Modèle ne peut pas être vide")
    @JsonView({EquipmentView.class, LoanView.class, RequestView.class, AppUserView.class, ModificationView.class})
    protected Model model;

    @ManyToOne
    @JsonView({EquipmentView.class, LoanView.class, RequestView.class, AppUserView.class, ModificationView.class})
    protected Location location;

    @OneToMany(mappedBy = "equipment")
    @OrderBy("startDate DESC")
    @JsonView(EquipmentView.class)
    protected List<Loan> loans;
}
