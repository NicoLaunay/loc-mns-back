package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.AccreditationDao;
import com.mns.cda.loc_mns.dao.TypeDao;
import com.mns.cda.loc_mns.model.Accreditation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
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
     * @return l'accréditation correspondante
     * @throws IllegalArgumentException si aucune accréditation ne correspond à cet identifiant
     */
    public Accreditation getAccreditation(int id) {
        Optional<Accreditation> optionalAccreditation = accreditationDao.findById(id);
        if (optionalAccreditation.isEmpty()) {
            throw new IllegalArgumentException("Aucune accréditation ne correspond à cet identifiant");
        }
        return optionalAccreditation.get();
    }

    /**
     * Crée une nouvelle accréditation en base de données.
     *
     * @param newAccreditation données de l'accréditation à créer
     * @return l'accréditation créée
     */
    public Accreditation createAccreditation(Accreditation newAccreditation) {
        newAccreditation.setId(null);
        return accreditationDao.save(newAccreditation);
    }

    /**
     * Supprime une accréditation à partir de son identifiant.
     *
     * @param id identifiant unique de l'accréditation à supprimer
     * @throws IllegalArgumentException si aucune accréditation ne correspond à cet identifiant
     */
    public void deleteAccreditation(int id) {
        Optional<Accreditation> optionalAccreditation = accreditationDao.findById(id);
        if (optionalAccreditation.isEmpty()) {
            throw new IllegalArgumentException("Aucune accréditation ne correspond à cet identifiant");
        }
        accreditationDao.deleteById(id);
    }

    /**
     * Met à jour une accréditation existante en remplaçant ses données.
     *
     * @param id identifiant unique de l'accréditation à mettre à jour
     * @param accreditationToUpdate nouvelles données de l'accréditation
     * @throws IllegalArgumentException si aucune accréditation ne correspond à cet identifiant
     */
    public void updateAccreditation(int id, Accreditation accreditationToUpdate) {
        Optional<Accreditation> optionalAccreditation = accreditationDao.findById(id);
        if (optionalAccreditation.isEmpty()) {
            throw new IllegalArgumentException("Aucune accréditation ne correspond à cet identifiant");
        }
        accreditationToUpdate.setId(id);
        accreditationDao.save(accreditationToUpdate);
    }
}
