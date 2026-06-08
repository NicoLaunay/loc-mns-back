package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.TypeDao;
import com.mns.cda.loc_mns.model.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TypeService {

    @Autowired
    protected TypeDao typeDao;

    /**
     * Récupère l'ensemble des types enregistrés en base de données.
     *
     * @return une liste non nulle de types, éventuellement vide si aucune donnée n'est présente
     */
    public List<Type> getAllTypes() {
        return typeDao.findAll();
    }

    /**
     * Récupère un type à partir de son identifiant.
     *
     * @param id identifiant unique du type recherché
     * @return le type correspondant
     * @throws IllegalArgumentException si aucun type ne correspond à cet identifiant
     */
    public Type getType(int id) {
        Optional<Type> optionalType = typeDao.findById(id);
        if (optionalType.isEmpty()) {
            throw new IllegalArgumentException("Aucun type ne correspond à cet identifiant");
        }
        return optionalType.get();
    }

    /**
     * Crée un nouveau type en base de données.
     *
     * @param newType données du type à créer
     * @return le type créé
     */
    public Type createType(Type newType) {
        newType.setId(null);
        return typeDao.save(newType);
    }

    /**
     * Supprime un type à partir de son identifiant.
     *
     * @param id identifiant unique du type à supprimer
     * @throws IllegalArgumentException si aucun type ne correspond à cet identifiant
     */
    public void deleteType(int id) {
        Optional<Type> optionalType = typeDao.findById(id);
        if (optionalType.isEmpty()) {
            throw new IllegalArgumentException("Aucun type ne correspond à cet identifiant");
        }
        typeDao.deleteById(id);
    }

    /**
     * Met à jour un type existant en remplaçant ses données.
     *
     * @param id identifiant unique du type à mettre à jour
     * @param typeToUpdate nouvelles données du type
     * @throws IllegalArgumentException si aucun type ne correspond à cet identifiant
     */
    public void updateType(int id, Type typeToUpdate) {
        Optional<Type> optionalType = typeDao.findById(id);
        if (optionalType.isEmpty()) {
            throw new IllegalArgumentException("Aucun type ne correspond à cet identifiant");
        }
        typeToUpdate.setId(id);
        typeDao.save(typeToUpdate);
    }
}
