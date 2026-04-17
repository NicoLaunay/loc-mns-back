package com.mns.cda.loc_mns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.view.AccreditationView;
import com.mns.cda.loc_mns.view.TypeView;
import com.mns.cda.loc_mns.view.Views;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter // Crée les Getters
@Setter // Crée les Setters
@AllArgsConstructor // Crée un constructeur avec tous les attributs
@NoArgsConstructor // Crée un constructeur sans attributs
@Entity
public class Accreditation {

    @Id // Clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Read.class)
    protected Integer id;

    @JsonView({Views.Read.class, Views.Create.class, Views.Update.class})
    @Length(min = 3, max = 10)
    protected String name;

    @JsonView({Views.Create.class, Views.Update.class})
    protected List<Integer> borrowedTypesIds;

    @ManyToMany
    @JoinTable(
            name = "accreditation_type",
            joinColumns = @JoinColumn(name = "accreditation_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonView(Views.Read.class)
    protected List<Type> borrowedTypes;
}
