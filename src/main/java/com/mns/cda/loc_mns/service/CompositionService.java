package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.CompositionDao;
import com.mns.cda.loc_mns.model.Composition;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompositionService {

    @Autowired
    protected CompositionDao compositionDao;

    /**
     * Récupère l'ensemble des compositions enregistrées en base de données.
     *
     * @return une liste non nulle de compositions, éventuellement vide si aucune donnée n'est présente
     */
    public List<Composition> getAllCompositions() {
        return compositionDao.findAll();
    }

    /**
     * Récupère une composition à partir de son identifiant.
     *
     * @param key clé composée unique de la composition recherchée
     * @return une réponse HTTP contenant la composition si elle existe (200 OK),
     *         ou un statut 404 (NOT_FOUND) si aucune composition ne correspond à cet identifiant
     */
    public ResponseEntity<Composition> getComposition(Composition.Key key) {
        Optional<Composition> optionalComposition = compositionDao.findById(key);
        if (optionalComposition.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalComposition.get(), HttpStatus.OK);
    }

    /**
     * Crée une nouvelle composition en base de données.
     *
     * @param newComposition données de la composition à créer
     * @return une réponse HTTP contenant la composition créée (201 CREATED)
     */
    public ResponseEntity<Composition> createComposition(Composition newComposition) {
        newComposition.setId(null);
        compositionDao.save(newComposition);
        return new ResponseEntity<>(newComposition, HttpStatus.CREATED);
    }

    /**
     * Supprime une composition à partir de son identifiant.
     *
     * @param key clé composée unique de la composition à supprimer
     * @return une réponse HTTP avec le statut 204 (NO_CONTENT) si la suppression est effectuée,
     *         ou 404 (NOT_FOUND) si aucune composition ne correspond à cet identifiant
     */
    public ResponseEntity<Void> deleteComposition(Composition.Key key) {
        Optional<Composition> optionalComposition = compositionDao.findById(key);

        if (optionalComposition.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        compositionDao.deleteById(key);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Met à jour une composition existante en remplaçant ses données.
     *
     * @param key clé composée unique de la composition à mettre à jour
     * @param compositionToUpdate nouvelles données de la composition
     * @return une réponse HTTP avec le statut 204 (NO_CONTENT) si la mise à jour est effectuée,
     *         ou 404 (NOT_FOUND) si aucune composition ne correspond à cet identifiant
     */
    public ResponseEntity<Void> updateComposition(Composition.Key key, Composition compositionToUpdate) {
        Optional<Composition> optionalComposition = compositionDao.findById(key);

        if (optionalComposition.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // On écrase l'id du JSON par celui en paramètre
        compositionToUpdate.setId(key);
        compositionDao.save(compositionToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
