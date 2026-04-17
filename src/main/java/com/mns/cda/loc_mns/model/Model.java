package com.mns.cda.loc_mns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.view.CompositionView;
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
    protected Integer id;

    @NotBlank
    @JsonView(CompositionView.class)
    protected String name;

    protected String description;

    protected Boolean isComponent;

    @NotNull
    @ManyToOne
    @JsonView(CompositionView.class)
    protected Type type;

    @ManyToMany
    @JoinTable(
            name = "model_documentation",
            joinColumns = @JoinColumn(name = "model_id"),
            inverseJoinColumns = @JoinColumn(name = "documentation_id")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    protected List<Documentation> documentations;

    @OneToMany(mappedBy = "parent")
    @OnDelete(action = OnDeleteAction.CASCADE)
    protected List<Composition> components;

    @OneToMany(mappedBy = "component")
    @OnDelete(action = OnDeleteAction.CASCADE)
    protected List<Composition> parents;
}
