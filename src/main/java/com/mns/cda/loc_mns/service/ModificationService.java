package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.ModificationDao;
import com.mns.cda.loc_mns.model.Modification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ModificationService {

    @Autowired
    protected ModificationDao modificationDao;

    /**
     * Récupère l'ensemble des modifications enregistrées en base de données.
     *
     * @return une liste non nulle de modifications, éventuellement vide si aucune donnée n'est présente
     */
    public List<Modification> getAllModifications() {
        return modificationDao.findAll();
    }

    /**
     * Récupère une modification à partir de son identifiant.
     *
     * @param id identifiant unique de la modification recherchée
     * @return une réponse HTTP contenant la modification si elle existe (200 OK),
     *         ou un statut 404 (NOT_FOUND) si aucune modification ne correspond à cet identifiant
     */
    public ResponseEntity<Modification> getModification(int id) {
        Optional<Modification> optionalModification = modificationDao.findById(id);
        if (optionalModification.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalModification.get(), HttpStatus.OK);
    }

    /**
     * Crée une nouvelle modification en base de données.
     *
     * @param newModification données de la modification à créer
     * @return une réponse HTTP contenant la modification créée (201 CREATED)
     */
    public ResponseEntity<Modification> createModification(Modification newModification) {
        newModification.setId(null);
        modificationDao.save(newModification);
        return new ResponseEntity<>(newModification, HttpStatus.CREATED);
    }

    /**
     * Supprime une modification à partir de son identifiant.
     *
     * @param id identifiant unique de la modification à supprimer
     * @return une réponse HTTP avec le statut 204 (NO_CONTENT) si la suppression est effectuée,
     *         ou 404 (NOT_FOUND) si aucune modification ne correspond à cet identifiant
     */
    public ResponseEntity<Void> deleteModification(int id) {
        Optional<Modification> optionalModification = modificationDao.findById(id);

        if (optionalModification.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        modificationDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Met à jour une modification existante en remplaçant ses données.
     *
     * @param id identifiant unique de la modification à mettre à jour
     * @param modificationToUpdate nouvelles données de la modification
     * @return une réponse HTTP avec le statut 204 (NO_CONTENT) si la mise à jour est effectuée,
     *         ou 404 (NOT_FOUND) si aucune modification ne correspond à cet identifiant
     */
    public ResponseEntity<Void> updateModification(int id, Modification modificationToUpdate) {
        Optional<Modification> optionalModification = modificationDao.findById(id);

        if (optionalModification.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // On écrase l'id du JSON par celui en paramètre
        modificationToUpdate.setId(id);
        modificationDao.save(modificationToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
