package com.mns.cda.loc_mns.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.view.CompositionView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;

@Getter // Crée les Getters
@Setter // Crée les Setters
@AllArgsConstructor // Crée un constructeur avec tous les attributs
@NoArgsConstructor // Crée un constructeur sans attributs
@Entity
public class Composition {

    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Key implements Serializable {
        @Column(name = "parent_id")
        @JsonView(CompositionView.class)
        Integer parentId;
        @Column(name = "component_id")
        @JsonView(CompositionView.class)
        Integer componentId;
    }

    @JsonView(CompositionView.class)
    protected int amount = 1;

    @EmbeddedId
    @JsonView(CompositionView.class)
    private Key id;

    @ManyToOne
    @MapsId("parentId")
    @JoinColumn(name = "parent_id")
    @JsonView(CompositionView.class)
    @OnDelete(action = OnDeleteAction.CASCADE)
    protected Model parent;

    @ManyToOne
    @MapsId("componentId")
    @JoinColumn(name = "component_id")
    @JsonView(CompositionView.class)
    @OnDelete(action = OnDeleteAction.CASCADE)
    protected Model component;

}

