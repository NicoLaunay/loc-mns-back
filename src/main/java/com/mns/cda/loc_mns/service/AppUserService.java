package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.*;
import com.mns.cda.loc_mns.exception.IdNotFoundException;
import com.mns.cda.loc_mns.model.AppUser;
import com.mns.cda.loc_mns.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserService implements IAppUserService {

    private final PasswordEncoder encoder;

    private final AppUserDao appUserDao;
    private final LoanDao loanDao;
    private final RequestDao requestDao;
    private final ModificationDao modificationDao;
    private final RoleDao roleDao;
    private final AccreditationDao accreditationDao;

    /**
     * Récupère l'ensemble des utilisateurs enregistrés en base de données.
     *
     * @return une liste non nulle d'utilisateurs, éventuellement vide si aucune donnée n'est présente
     */
    @Override
    public List<AppUser> getAll() {
        return appUserDao.findAll();
    }

    /**
     * Récupère un utilisateur à partir de son identifiant.
     *
     * @param id identifiant unique de l'utilisateur recherché
     * @return l'utilisateur correspondant
     * @throws IdNotFoundException si aucun utilisateur ne correspond à cet identifiant
     */
    @Override
    public AppUser get(int id) throws IdNotFoundException {
        return appUserDao.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Aucun utilisateur ne correspond à cet identifiant"));
    }

    /**
     * Récupère un utilisateur à partir de son adresse email.
     *
     * @param email adresse email de l'utilisateur recherché
     * @return l'utilisateur correspondant
     * @throws IdNotFoundException si aucun utilisateur ne correspond à cette adresse email
     */
    @Override
    public AppUser getByEmail(String email) throws IdNotFoundException {
        return appUserDao.findByEmail(email)
                .orElseThrow(() -> new IdNotFoundException("Aucun utilisateur ne correspond à cette adresse email"));
    }

    @Override
    public boolean emailExists(String email) {
        return appUserDao.existsByEmail(email);
    }

    /**
     * Crée un nouvel utilisateur en base de données.
     *
     * @param newAppUser données de l'utilisateur à créer
     * @return l'utilisateur créé
     */
    @Override
    public AppUser create(AppUser newAppUser) {
        newAppUser.setId(null);
        newAppUser.setRole(roleDao.getReferenceById(3));
        newAppUser.setAccreditation(accreditationDao.getReferenceById(2));
        newAppUser.setPassword(encoder.encode(newAppUser.getPassword()));
        return appUserDao.save(newAppUser);
    }

    /**
     * Supprime un utilisateur à partir de son identifiant.
     *
     * @param id identifiant unique de l'utilisateur à supprimer
     * @throws IdNotFoundException si aucun utilisateur ne correspond à cet identifiant
     */
    @Override
    public void delete(int id) throws IdNotFoundException, AccessDeniedException {
        AppUser userToDelete = appUserDao.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Aucun utilisateur ne correspond à cet identifiant"));

        if (userToDelete.getRole().getName().equals("OWNER")) {
            throw new AccessDeniedException("Impossible de supprimer le propriétaire");
        }

        modificationDao.deleteAllByUserId(id);
        requestDao.deleteAllByUserId(id);
        loanDao.deleteAllByUserId(id);
        appUserDao.deleteById(id);
    }

    /**
     * Met à jour un utilisateur existant en remplaçant ses données personnelles.
     *
     * @param id identifiant unique de l'utilisateur à mettre à jour
     * @param appUserToUpdate nouvelles données de l'utilisateur
     * @throws IdNotFoundException si aucun utilisateur ne correspond à cet identifiant
     */
    @Override
    public void update(int id, AppUser appUserToUpdate) throws IdNotFoundException {
        AppUser oldAppUser = appUserDao.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Aucun utilisateur ne correspond à cet identifiant"));
        appUserToUpdate.setId(id);
        appUserToUpdate.setRole(oldAppUser.getRole());
        appUserToUpdate.setAccreditation(oldAppUser.getAccreditation());
        appUserToUpdate.setPassword(oldAppUser.getPassword());
        appUserDao.save(appUserToUpdate);
    }

    /**
     * Met à jour le mot de passe d'un utilisateur existant.
     *
     * @param id identifiant unique de l'utilisateur à mettre à jour
     * @param newPassword nouveau MdP de l'utilisateur
     * @throws IdNotFoundException si aucun utilisateur ne correspond à cet identifiant
     */
    @Override
    public void changePassword(int id, String newPassword) throws IdNotFoundException {
        AppUser appUser = appUserDao.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Aucun utilisateur ne correspond à cet identifiant"));
        appUser.setPassword(encoder.encode(newPassword));
        appUserDao.save(appUser);
    }

    /**
     * Met à jour le role d'un utilisateur existant.
     *
     * @param id identifiant unique de l'utilisateur à mettre à jour
     * @param newRole nouveau role de l'utilisateur
     * @throws IdNotFoundException si aucun utilisateur ne correspond à cet identifiant
     * @throws AccessDeniedException si la modification concerne le role propriétaire
     */
    @Override
    public void changeRole(int id, Role newRole) throws IdNotFoundException, AccessDeniedException {
        AppUser appUser = appUserDao.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Aucun utilisateur ne correspond à cet identifiant"));

        if (appUser.getRole().getName().equals("OWNER")) {
            throw new AccessDeniedException("impossible de supprimer le propriétaire");
        }
        if (newRole.getName().equals("OWNER")) {
            throw new AccessDeniedException("impossible d'ajouter un propriétaire'");
        }

        appUser.setRole(newRole);
        appUserDao.save(appUser);
    }

    /**
     * Transfère la propriété de l'application à un utilisateur existant.
     *
     * @param idOldOwner identifiant unique du propriétaire actuel
     * @param idNewOwner identifiant unique du nouveau propriétaire
     * @throws IdNotFoundException si aucun utilisateur ne correspond à l'un des identifiants
     */
    @Override
    public void transferOwnership(int idOldOwner, int idNewOwner) throws IdNotFoundException {
        AppUser oldOwner = appUserDao.findById(idOldOwner)
                .orElseThrow(() -> new IdNotFoundException("Aucun utilisateur ne correspond à cet identifiant"));
        AppUser newOwner = appUserDao.findById(idNewOwner)
                .orElseThrow(() -> new IdNotFoundException("Aucun utilisateur ne correspond à cet identifiant"));

        oldOwner.setRole(roleDao.findAll().get(1));
        newOwner.setRole(roleDao.findAll().get(0));

        appUserDao.save(oldOwner);
        appUserDao.save(newOwner);
    }
}
