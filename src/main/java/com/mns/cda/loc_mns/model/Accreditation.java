package com.mns.cda.loc_mns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.view.LoanView;
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
    @JsonView(LoanView.class)
    protected Integer id;

    @Length(min = 3, max = 10)
    @JsonView(LoanView.class)
    protected String name;

    @ManyToMany
    @JoinTable(
            name = "accreditation_type",
            joinColumns = @JoinColumn(name = "accreditation_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    protected List<Type> borrowedTypes;
}
