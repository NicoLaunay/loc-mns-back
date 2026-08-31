package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.DocumentationDao;
import com.mns.cda.loc_mns.exception.IdNotFoundException;
import com.mns.cda.loc_mns.model.Documentation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentationService {

    private final DocumentationDao documentationDao;

    /**
     * Récupère l'ensemble des documentations enregistrées en base de données.
     *
     * @return une liste non nulle de documentations, éventuellement vide si aucune donnée n'est présente
     */
    public List<Documentation> getAll() {
        return documentationDao.findAll();
    }

    /**
     * Récupère une documentation à partir de son identifiant.
     *
     * @param id identifiant unique de la documentation recherchée
     * @return la documentation correspondante
     * @throws IdNotFoundException si aucune documentation ne correspond à cet identifiant
     */
    public Documentation get(int id) throws IdNotFoundException {
        return documentationDao.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Aucune documentation ne correspond à cet identifiant"));
    }

    /**
     * Crée une nouvelle documentation en base de données.
     *
     * @param newDocumentation données de la documentation à créer
     * @return la documentation créée
     */
    public Documentation create(Documentation newDocumentation) {
        newDocumentation.setId(null);
        return documentationDao.save(newDocumentation);
    }

    /**
     * Supprime une documentation à partir de son identifiant.
     *
     * @param id identifiant unique de la documentation à supprimer
     * @throws IdNotFoundException si aucune documentation ne correspond à cet identifiant
     */
    public void delete(int id) throws IdNotFoundException {
        documentationDao.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Aucune documentation ne correspond à cet identifiant"));
        documentationDao.deleteById(id);
    }

    /**
     * Met à jour une documentation existante en remplaçant ses données.
     *
     * @param id identifiant unique de la documentation à mettre à jour
     * @param documentationToUpdate nouvelles données de la documentation
     * @throws IdNotFoundException si aucune documentation ne correspond à cet identifiant
     */
    public void update(int id, Documentation documentationToUpdate) throws IdNotFoundException {
        documentationDao.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Aucune documentation ne correspond à cet identifiant"));
        documentationToUpdate.setId(id);
        documentationDao.save(documentationToUpdate);
    }
}
