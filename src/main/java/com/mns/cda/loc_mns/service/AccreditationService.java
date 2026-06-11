package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.AccreditationDao;
import com.mns.cda.loc_mns.dao.TypeDao;
import com.mns.cda.loc_mns.exception.IdNotFoundException;
import com.mns.cda.loc_mns.model.Accreditation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Accreditation> getAll() {
        return accreditationDao.findAll();
    }

    /**
     * Récupère une accréditation à partir de son identifiant.
     *
     * @param id identifiant unique de l'accréditation recherchée
     * @return l'accréditation correspondante
     * @throws IdNotFoundException si aucune accréditation ne correspond à cet identifiant
     */
    public Accreditation get(int id) throws IdNotFoundException {
        return accreditationDao.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Aucune accréditation ne correspond à cet identifiant"));
    }

    /**
     * Crée une nouvelle accréditation en base de données.
     *
     * @param newAccreditation données de l'accréditation à créer
     * @return l'accréditation créée
     */
    public Accreditation create(Accreditation newAccreditation) {
        newAccreditation.setId(null);
        return accreditationDao.save(newAccreditation);
    }

    /**
     * Supprime une accréditation à partir de son identifiant.
     *
     * @param id identifiant unique de l'accréditation à supprimer
     * @throws IdNotFoundException si aucune accréditation ne correspond à cet identifiant
     */
    public void delete(int id) throws IdNotFoundException {
        accreditationDao.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Aucune accréditation ne correspond à cet identifiant"));
        accreditationDao.deleteById(id);
    }

    /**
     * Met à jour une accréditation existante en remplaçant ses données.
     *
     * @param id identifiant unique de l'accréditation à mettre à jour
     * @param accreditationToUpdate nouvelles données de l'accréditation
     * @throws IdNotFoundException si aucune accréditation ne correspond à cet identifiant
     */
    public void update(int id, Accreditation accreditationToUpdate) throws IdNotFoundException {
        accreditationDao.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Aucune accréditation ne correspond à cet identifiant"));
        accreditationToUpdate.setId(id);
        accreditationDao.save(accreditationToUpdate);
    }
}
