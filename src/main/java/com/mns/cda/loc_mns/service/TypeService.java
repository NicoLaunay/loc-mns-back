package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.TypeDao;
import com.mns.cda.loc_mns.exception.IdNotFoundException;
import com.mns.cda.loc_mns.model.Accreditation;
import com.mns.cda.loc_mns.model.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TypeService {

    private final TypeDao typeDao;

    /**
     * Récupère l'ensemble des types enregistrés en base de données.
     *
     * @return une liste non nulle de types, éventuellement vide si aucune donnée n'est présente
     */
    public List<Type> getAll() {
        return typeDao.findAll();
    }

    /**
     * Récupère l'ensemble des types enregistrés en base de données.
     *
     * @return une liste non nulle de types, éventuellement vide si aucune donnée n'est présente
     */
    public List<Type> getAllBorrowableByAccreditation(Accreditation accreditation) {
        return accreditation.getBorrowedTypes();
    }

    /**
     * Récupère un type à partir de son identifiant.
     *
     * @param id identifiant unique du type recherché
     * @return le type correspondant
     * @throws IdNotFoundException si aucun type ne correspond à cet identifiant
     */
    public Type get(int id) throws IdNotFoundException {
        return typeDao.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Aucun type ne correspond à cet identifiant"));
    }

    /**
     * Crée un nouveau type en base de données.
     *
     * @param newType données du type à créer
     * @return le type créé
     */
    public Type create(Type newType) {
        newType.setId(null);
        return typeDao.save(newType);
    }

    /**
     * Supprime un type à partir de son identifiant.
     *
     * @param id identifiant unique du type à supprimer
     * @throws IdNotFoundException si aucun type ne correspond à cet identifiant
     */
    public void delete(int id) throws IdNotFoundException {
        typeDao.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Aucun type ne correspond à cet identifiant"));
        typeDao.deleteById(id);
    }

    /**
     * Met à jour un type existant en remplaçant ses données.
     *
     * @param id identifiant unique du type à mettre à jour
     * @param typeToUpdate nouvelles données du type
     * @throws IdNotFoundException si aucun type ne correspond à cet identifiant
     */
    public void update(int id, Type typeToUpdate) throws IdNotFoundException {
        typeDao.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Aucun type ne correspond à cet identifiant"));
        typeToUpdate.setId(id);
        typeDao.save(typeToUpdate);
    }
}
