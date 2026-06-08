package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.RoleDao;
import com.mns.cda.loc_mns.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    @Autowired
    protected RoleDao roleDao;

    /**
     * Récupère l'ensemble des rôles enregistrés en base de données.
     *
     * @return une liste non nulle de rôles, éventuellement vide si aucune donnée n'est présente
     */
    public List<Role> getAllRoles() {
        return roleDao.findAll();
    }

    /**
     * Récupère un rôle à partir de son identifiant.
     *
     * @param id identifiant unique du rôle recherché
     * @return le rôle correspondant
     * @throws IllegalArgumentException si aucun rôle ne correspond à cet identifiant
     */
    public Role getRole(int id) {
        Optional<Role> optionalRole = roleDao.findById(id);
        if (optionalRole.isEmpty()) {
            throw new IllegalArgumentException("Aucun rôle ne correspond à cet identifiant");
        }
        return optionalRole.get();
    }

    /**
     * Crée un nouveau rôle en base de données.
     *
     * @param newRole données du rôle à créer
     * @return le rôle créé
     */
    public Role createRole(Role newRole) {
        newRole.setId(null);
        return roleDao.save(newRole);
    }

    /**
     * Supprime un rôle à partir de son identifiant.
     *
     * @param id identifiant unique du rôle à supprimer
     * @throws IllegalArgumentException si aucun rôle ne correspond à cet identifiant
     */
    public void deleteRole(int id) {
        Optional<Role> optionalRole = roleDao.findById(id);
        if (optionalRole.isEmpty()) {
            throw new IllegalArgumentException("Aucun rôle ne correspond à cet identifiant");
        }
        roleDao.deleteById(id);
    }

    /**
     * Met à jour un rôle existant en remplaçant ses données.
     *
     * @param id identifiant unique du rôle à mettre à jour
     * @param roleToUpdate nouvelles données du rôle
     * @throws IllegalArgumentException si aucun rôle ne correspond à cet identifiant
     */
    public void updateRole(int id, Role roleToUpdate) {
        Optional<Role> optionalRole = roleDao.findById(id);
        if (optionalRole.isEmpty()) {
            throw new IllegalArgumentException("Aucun rôle ne correspond à cet identifiant");
        }
        roleToUpdate.setId(id);
        roleDao.save(roleToUpdate);
    }
}
