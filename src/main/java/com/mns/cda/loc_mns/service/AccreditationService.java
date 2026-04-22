package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.AccreditationDao;
import com.mns.cda.loc_mns.dao.TypeDao;
import com.mns.cda.loc_mns.model.Accreditation;
import com.mns.cda.loc_mns.model.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor // Crée un constructeur avec tous les champs requis (final)
public class AccreditationService {

    protected final AccreditationDao accreditationDao;
    private final TypeDao typeDao;


    /**
     * Récupère l'ensemble des accréditations enregistrées en base de données.
     *
     * @return une liste non nulle d'accréditations, éventuellement vide si aucune donnée n'est présente
     */
    public List<Accreditation> getAllAccreditations() {
        return accreditationDao.findAll();
    }

    /**
     * Récupère une accréditation à partir de son identifiant.
     *
     * @param id identifiant unique de l'accréditation recherchée
     * @return une réponse HTTP contenant l'accréditation si elle existe (200 OK),
     *         ou un statut 404 (NOT_FOUND) si aucune accréditation ne correspond à cet identifiant
     */
    public ResponseEntity<Accreditation> getAccreditation(int id) {
        Optional<Accreditation> optionalAccreditation = accreditationDao.findById(id);
        if (optionalAccreditation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalAccreditation.get(), HttpStatus.OK);
    }

    /**
     * Crée une nouvelle accréditation en base de données.
     *
     * @param newAccreditation données de l'accréditation à créer
     * @return une réponse HTTP contenant l'accréditation créée (201 CREATED)
     */
    public ResponseEntity<Accreditation> createAccreditation(Accreditation newAccreditation) {
        newAccreditation.setId(null);

        accreditationDao.save(newAccreditation);
        return new ResponseEntity<>(newAccreditation, HttpStatus.CREATED);
    }

    /**
     * Supprime une accréditation à partir de son identifiant.
     *
     * @param id identifiant unique de l'accréditation à supprimer
     * @return une réponse HTTP avec le statut 204 (NO_CONTENT) si la suppression est effectuée,
     *         ou 404 (NOT_FOUND) si aucune accréditation ne correspond à cet identifiant
     */
    public ResponseEntity<Void> deleteAccreditation(int id) {
        Optional<Accreditation> optionalAccreditation = accreditationDao.findById(id);

        if (optionalAccreditation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        accreditationDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Met à jour une accréditation existante en remplaçant ses données.
     *
     * @param id identifiant unique de l'accréditation à mettre à jour
     * @param accreditationToUpdate nouvelles données de l'accréditation
     * @return une réponse HTTP avec le statut 204 (NO_CONTENT) si la mise à jour est effectuée,
     *         ou 404 (NOT_FOUND) si aucune accréditation ne correspond à cet identifiant
     */
    public ResponseEntity<Void> updateAccreditation(int id, Accreditation accreditationToUpdate) {
        Optional<Accreditation> optionalAccreditation = accreditationDao.findById(id);

        if (optionalAccreditation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // On écrase l'id du JSON par celui en paramètre
        accreditationToUpdate.setId(id);

        accreditationDao.save(accreditationToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
