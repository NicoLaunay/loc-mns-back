package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.RoleDao;
import com.mns.cda.loc_mns.exception.IdNotFoundException;
import com.mns.cda.loc_mns.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleDao roleDao;

    /**
     * Récupère l'ensemble des rôles enregistrés en base de données.
     *
     * @return une liste non nulle de rôles, éventuellement vide si aucune donnée n'est présente
     */
    public List<Role> getAll() {
        return roleDao.findAll();
    }

    /**
     * Récupère un rôle à partir de son identifiant.
     *
     * @param id identifiant unique du rôle recherché
     * @return le rôle correspondant
     * @throws IdNotFoundException si aucun rôle ne correspond à cet identifiant
     */
    public Role get(int id) throws IdNotFoundException {
        return roleDao.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Aucun rôle ne correspond à cet identifiant"));
    }

    /**
     * Crée un nouveau rôle en base de données.
     *
     * @param newRole données du rôle à créer
     * @return le rôle créé
     */
    public Role create(Role newRole) {
        newRole.setId(null);
        return roleDao.save(newRole);
    }

    /**
     * Supprime un rôle à partir de son identifiant.
     *
     * @param id identifiant unique du rôle à supprimer
     * @throws IdNotFoundException si aucun rôle ne correspond à cet identifiant
     */
    public void delete(int id) throws IdNotFoundException {
        roleDao.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Aucun rôle ne correspond à cet identifiant"));
        roleDao.deleteById(id);
    }

    /**
     * Met à jour un rôle existant en remplaçant ses données.
     *
     * @param id identifiant unique du rôle à mettre à jour
     * @param roleToUpdate nouvelles données du rôle
     * @throws IdNotFoundException si aucun rôle ne correspond à cet identifiant
     */
    public void update(int id, Role roleToUpdate) throws IdNotFoundException {
        roleDao.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Aucun rôle ne correspond à cet identifiant"));
        roleToUpdate.setId(id);
        roleDao.save(roleToUpdate);
    }
}
