package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.DocumentationDao;
import com.mns.cda.loc_mns.model.Documentation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocumentationService {

    @Autowired
    protected DocumentationDao documentationDao;

    /**
     * Récupère l'ensemble des documentations enregistrées en base de données.
     *
     * @return une liste non nulle de documentations, éventuellement vide si aucune donnée n'est présente
     */
    public List<Documentation> getAllDocumentations() {
        return documentationDao.findAll();
    }

    /**
     * Récupère une documentation à partir de son identifiant.
     *
     * @param id identifiant unique de la documentation recherchée
     * @return la documentation correspondante
     * @throws IllegalArgumentException si aucune documentation ne correspond à cet identifiant
     */
    public Documentation getDocumentation(int id) {
        Optional<Documentation> optionalDocumentation = documentationDao.findById(id);
        if (optionalDocumentation.isEmpty()) {
            throw new IllegalArgumentException("Aucune documentation ne correspond à cet identifiant");
        }
        return optionalDocumentation.get();
    }

    /**
     * Crée une nouvelle documentation en base de données.
     *
     * @param newDocumentation données de la documentation à créer
     * @return la documentation créée
     */
    public Documentation createDocumentation(Documentation newDocumentation) {
        newDocumentation.setId(null);
        return documentationDao.save(newDocumentation);
    }

    /**
     * Supprime une documentation à partir de son identifiant.
     *
     * @param id identifiant unique de la documentation à supprimer
     * @throws IllegalArgumentException si aucune documentation ne correspond à cet identifiant
     */
    public void deleteDocumentation(int id) {
        Optional<Documentation> optionalDocumentation = documentationDao.findById(id);
        if (optionalDocumentation.isEmpty()) {
            throw new IllegalArgumentException("Aucune documentation ne correspond à cet identifiant");
        }
        documentationDao.deleteById(id);
    }

    /**
     * Met à jour une documentation existante en remplaçant ses données.
     *
     * @param id identifiant unique de la documentation à mettre à jour
     * @param documentationToUpdate nouvelles données de la documentation
     * @throws IllegalArgumentException si aucune documentation ne correspond à cet identifiant
     */
    public void updateDocumentation(int id, Documentation documentationToUpdate) {
        Optional<Documentation> optionalDocumentation = documentationDao.findById(id);
        if (optionalDocumentation.isEmpty()) {
            throw new IllegalArgumentException("Aucune documentation ne correspond à cet identifiant");
        }
        documentationToUpdate.setId(id);
        documentationDao.save(documentationToUpdate);
    }
}
