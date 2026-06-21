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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Getter // Crée les Getters
@Setter // Crée les Setters
@AllArgsConstructor // Crée un constructeur avec tous les attributs
@NoArgsConstructor // Crée un constructeur sans attributs
@Entity
public class Model {

    @Id // Clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({CompositionView.class, EquipmentView.class, LoanView.class, RequestView.class, AppUserView.class})
    protected Integer id;

    @NotBlank(message = "Le Nom ne peut pas être vide")
    @JsonView({CompositionView.class, EquipmentView.class, LoanView.class, RequestView.class, AppUserView.class, ModificationView.class})
    protected String name;

    @JsonView({EquipmentView.class, LoanView.class, RequestView.class, AppUserView.class, ModificationView.class})
    protected String description;

    protected Boolean isComponent;

    @NotNull(message = "Le Type ne peut pas être vide")
    @ManyToOne
    @JsonView({CompositionView.class, EquipmentView.class, LoanView.class, RequestView.class, AppUserView.class, ModificationView.class})
    protected Type type;

    @ManyToMany
    @JoinTable(
            name = "model_documentation",
            joinColumns = @JoinColumn(name = "model_id"),
            inverseJoinColumns = @JoinColumn(name = "documentation_id")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonView({EquipmentView.class, LoanView.class, RequestView.class, AppUserView.class, ModificationView.class})
    protected List<Documentation> documentations;

    @OneToMany(mappedBy = "parent")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonView({EquipmentView.class, ModificationView.class})
    protected List<Composition> components;

    @OneToMany(mappedBy = "component")
    @OnDelete(action = OnDeleteAction.CASCADE)
    protected List<Composition> parents;
}
