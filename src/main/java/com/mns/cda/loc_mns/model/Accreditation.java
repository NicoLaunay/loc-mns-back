package com.mns.cda.loc_mns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.view.AccreditationView;
import com.mns.cda.loc_mns.view.TypeView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
public class Accreditation {

    @Id // Clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({AccreditationView.class, TypeView.class})
    protected Integer id;

    @ManyToMany
    @JoinTable(
            name = "accreditation_type",
            joinColumns = @JoinColumn(name = "accreditation_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonView(AccreditationView.class)
    protected List<Type> borrowedTypes;
}
