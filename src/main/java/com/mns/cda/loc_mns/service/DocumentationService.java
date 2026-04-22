package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.DocumentationDao;
import com.mns.cda.loc_mns.model.Documentation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
     * @return une réponse HTTP contenant la documentation si elle existe (200 OK),
     *         ou un statut 404 (NOT_FOUND) si aucune documentation ne correspond à cet identifiant
     */
    public ResponseEntity<Documentation> getDocumentation(int id) {
        Optional<Documentation> optionalDocumentation = documentationDao.findById(id);
        if (optionalDocumentation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalDocumentation.get(), HttpStatus.OK);
    }

    /**
     * Crée une nouvelle documentation en base de données.
     *
     * @param newDocumentation données de la documentation à créer
     * @return une réponse HTTP contenant la documentation créée (201 CREATED)
     */
    public ResponseEntity<Documentation> createDocumentation(Documentation newDocumentation) {
        newDocumentation.setId(null);
        documentationDao.save(newDocumentation);
        return new ResponseEntity<>(newDocumentation, HttpStatus.CREATED);
    }

    /**
     * Supprime une documentation à partir de son identifiant.
     *
     * @param id identifiant unique de la documentation à supprimer
     * @return une réponse HTTP avec le statut 204 (NO_CONTENT) si la suppression est effectuée,
     *         ou 404 (NOT_FOUND) si aucune documentation ne correspond à cet identifiant
     */
    public ResponseEntity<Void> deleteDocumentation(int id) {
        Optional<Documentation> optionalDocumentation = documentationDao.findById(id);

        if (optionalDocumentation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        documentationDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Met à jour une documentation existante en remplaçant ses données.
     *
     * @param id identifiant unique de la documentation à mettre à jour
     * @param documentationToUpdate nouvelles données de la documentation
     * @return une réponse HTTP avec le statut 204 (NO_CONTENT) si la mise à jour est effectuée,
     *         ou 404 (NOT_FOUND) si aucune documentation ne correspond à cet identifiant
     */
    public ResponseEntity<Void> updateDocumentation(int id, Documentation documentationToUpdate) {
        Optional<Documentation> optionalDocumentation = documentationDao.findById(id);

        if (optionalDocumentation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // On écrase l'id du JSON par celui en paramètre
        documentationToUpdate.setId(id);
        documentationDao.save(documentationToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
