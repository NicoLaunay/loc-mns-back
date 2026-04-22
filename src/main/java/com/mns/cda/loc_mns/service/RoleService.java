package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.RoleDao;
import com.mns.cda.loc_mns.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
     * @return une réponse HTTP contenant le rôle si il existe (200 OK),
     *         ou un statut 404 (NOT_FOUND) si aucun rôle ne correspond à cet identifiant
     */
    public ResponseEntity<Role> getRole(int id) {
        Optional<Role> optionalRole = roleDao.findById(id);
        if (optionalRole.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalRole.get(), HttpStatus.OK);
    }

    /**
     * Crée un nouveau rôle en base de données.
     *
     * @param newRole données du rôle à créer
     * @return une réponse HTTP contenant le rôle créé (201 CREATED)
     */
    public ResponseEntity<Role> createRole(Role newRole) {
        newRole.setId(null);
        roleDao.save(newRole);
        return new ResponseEntity<>(newRole, HttpStatus.CREATED);
    }

    /**
     * Supprime un rôle à partir de son identifiant.
     *
     * @param id identifiant unique du rôle à supprimer
     * @return une réponse HTTP avec le statut 204 (NO_CONTENT) si la suppression est effectuée,
     *         ou 404 (NOT_FOUND) si aucun rôle ne correspond à cet identifiant
     */
    public ResponseEntity<Void> deleteRole(int id) {
        Optional<Role> optionalRole = roleDao.findById(id);

        if (optionalRole.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        roleDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Met à jour un rôle existant en remplaçant ses données.
     *
     * @param id identifiant unique du rôle à mettre à jour
     * @param roleToUpdate nouvelles données du rôle
     * @return une réponse HTTP avec le statut 204 (NO_CONTENT) si la mise à jour est effectuée,
     *         ou 404 (NOT_FOUND) si aucun rôle ne correspond à cet identifiant
     */
    public ResponseEntity<Void> updateRole(int id, Role roleToUpdate) {
        Optional<Role> optionalRole = roleDao.findById(id);

        if (optionalRole.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // On écrase l'id du JSON par celui en paramètre
        roleToUpdate.setId(id);
        roleDao.save(roleToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
