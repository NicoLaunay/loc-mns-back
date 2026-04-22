package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.TypeDao;
import com.mns.cda.loc_mns.model.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
     * @return une réponse HTTP contenant le type si il existe (200 OK),
     *         ou un statut 404 (NOT_FOUND) si aucun type ne correspond à cet identifiant
     */
    public ResponseEntity<Type> getType(int id) {
        Optional<Type> optionalType = typeDao.findById(id);
        if (optionalType.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalType.get(), HttpStatus.OK);
    }

    /**
     * Crée un nouveau type en base de données.
     *
     * @param newType données du type à créer
     * @return une réponse HTTP contenant le type créé (201 CREATED)
     */
    public ResponseEntity<Type> createType(Type newType) {
        newType.setId(null);
        typeDao.save(newType);
        return new ResponseEntity<>(newType, HttpStatus.CREATED);
    }

    /**
     * Supprime un type à partir de son identifiant.
     *
     * @param id identifiant unique du type à supprimer
     * @return une réponse HTTP avec le statut 204 (NO_CONTENT) si la suppression est effectuée,
     *         ou 404 (NOT_FOUND) si aucun type ne correspond à cet identifiant
     */
    public ResponseEntity<Void> deleteType(int id) {
        Optional<Type> optionalType = typeDao.findById(id);

        if (optionalType.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        typeDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Met à jour un type existant en remplaçant ses données.
     *
     * @param id identifiant unique du type à mettre à jour
     * @param typeToUpdate nouvelles données du type
     * @return une réponse HTTP avec le statut 204 (NO_CONTENT) si la mise à jour est effectuée,
     *         ou 404 (NOT_FOUND) si aucun type ne correspond à cet identifiant
     */
    public ResponseEntity<Void> updateType(int id, Type typeToUpdate) {
        Optional<Type> optionalType = typeDao.findById(id);

        if (optionalType.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // On écrase l'id du JSON par celui en paramètre
        typeToUpdate.setId(id);
        typeDao.save(typeToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
