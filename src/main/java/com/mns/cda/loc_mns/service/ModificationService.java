package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.ModificationDao;
import com.mns.cda.loc_mns.exception.IdNotFoundException;
import com.mns.cda.loc_mns.model.Modification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModificationService {

    private final ModificationDao modificationDao;

    /**
     * Récupère l'ensemble des modifications enregistrées en base de données.
     *
     * @return une liste non nulle de modifications, éventuellement vide si aucune donnée n'est présente
     */
    public List<Modification> getAll() {
        return modificationDao.findAll();
    }

    /**
     * Récupère une modification à partir de son identifiant.
     *
     * @param id identifiant unique de la modification recherchée
     * @return la modification correspondante
     * @throws IdNotFoundException si aucune modification ne correspond à cet identifiant
     */
    public Modification get(int id) throws IdNotFoundException {
        return modificationDao.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Aucune modification ne correspond à cet identifiant"));
    }

    /**
     * Crée une nouvelle modification en base de données.
     *
     * @param newModification données de la modification à créer
     * @return la modification créée
     */
    public Modification create(Modification newModification) {
        newModification.setId(null);
        return modificationDao.save(newModification);
    }

    /**
     * Supprime une modification à partir de son identifiant.
     *
     * @param id identifiant unique de la modification à supprimer
     * @throws IdNotFoundException si aucune modification ne correspond à cet identifiant
     */
    public void delete(int id) throws IdNotFoundException {
        modificationDao.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Aucune modification ne correspond à cet identifiant"));
        modificationDao.deleteById(id);
    }

    /**
     * Met à jour une modification existante en remplaçant ses données.
     *
     * @param id identifiant unique de la modification à mettre à jour
     * @param modificationToUpdate nouvelles données de la modification
     * @throws IdNotFoundException si aucune modification ne correspond à cet identifiant
     */
    public void update(int id, Modification modificationToUpdate) throws IdNotFoundException {
        modificationDao.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Aucune modification ne correspond à cet identifiant"));
        modificationToUpdate.setId(id);
        modificationDao.save(modificationToUpdate);
    }
}
