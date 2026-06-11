package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.CompositionDao;
import com.mns.cda.loc_mns.exception.IdNotFoundException;
import com.mns.cda.loc_mns.model.Composition;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Composition> getAll() {
        return compositionDao.findAll();
    }

    /**
     * Récupère une composition à partir de sa clé composée.
     *
     * @param key clé composée unique de la composition recherchée
     * @return la composition correspondante
     * @throws IdNotFoundException si aucune composition ne correspond à cette clé
     */
    public Composition get(Composition.Key key) throws IdNotFoundException {
        return compositionDao.findById(key)
                .orElseThrow(() -> new IdNotFoundException("Aucune composition ne correspond à cet identifiant"));
    }

    /**
     * Crée une nouvelle composition en base de données.
     *
     * @param newComposition données de la composition à créer
     * @return la composition créée
     */
    public Composition create(Composition newComposition) {
        newComposition.setId(null);
        return compositionDao.save(newComposition);
    }

    /**
     * Supprime une composition à partir de sa clé composée.
     *
     * @param key clé composée unique de la composition à supprimer
     * @throws IdNotFoundException si aucune composition ne correspond à cette clé
     */
    public void delete(Composition.Key key) throws IdNotFoundException {
        compositionDao.findById(key)
                .orElseThrow(() -> new IdNotFoundException("Aucune composition ne correspond à cet identifiant"));
        compositionDao.deleteById(key);
    }

    /**
     * Met à jour une composition existante en remplaçant ses données.
     *
     * @param key clé composée unique de la composition à mettre à jour
     * @param compositionToUpdate nouvelles données de la composition
     * @throws IdNotFoundException si aucune composition ne correspond à cette clé
     */
    public void update(Composition.Key key, Composition compositionToUpdate) throws IdNotFoundException {
        compositionDao.findById(key)
                .orElseThrow(() -> new IdNotFoundException("Aucune composition ne correspond à cet identifiant"));
        compositionToUpdate.setId(key);
        compositionDao.save(compositionToUpdate);
    }
}
