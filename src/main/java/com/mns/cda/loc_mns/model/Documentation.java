package com.mns.cda.loc_mns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.view.DocumentationView;
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
public class Documentation {

    @Id // Clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(DocumentationView.class)
    protected Integer id;

    @NotBlank
    @JsonView(DocumentationView.class)
    protected String title;

    @NotBlank
    @JsonView(DocumentationView.class)
    protected String url;
}
