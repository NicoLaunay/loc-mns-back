package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.ModelDao;
import com.mns.cda.loc_mns.dto.ModelDto;
import com.mns.cda.loc_mns.exception.IdNotFoundException;
import com.mns.cda.loc_mns.mapper.ModelMapper;
import com.mns.cda.loc_mns.model.Model;
import com.mns.cda.loc_mns.repository.ModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModelService {

    @Autowired
    protected ModelDao modelDao;

    private final ModelMapper mapper;
    private final ModelRepository repository;

    /**
     * Récupère l'ensemble des modèles enregistrés en base de données.
     *
     * @return une liste non nulle de modèles sous forme de DTO, éventuellement vide si aucune donnée n'est présente
     */
    public List<ModelDto> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    /**
     * Récupère l'ensemble des modèles d'un type donné enregistrés en base de données.
     *
     * @param typeId identifiant du type recherché
     * @return une liste non nulle de modèles sous forme de DTO, éventuellement vide si aucune donnée n'est présente
     */
    public List<ModelDto> getAllOfType(int typeId) {
        return modelDao.findAllOfType(typeId)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    /**
     * Récupère un modèle à partir de son identifiant.
     *
     * @param id identifiant unique du modèle recherché
     * @return le modèle correspondant sous forme de DTO
     * @throws IdNotFoundException si aucun modèle ne correspond à cet identifiant
     */
    public ModelDto get(int id) throws IdNotFoundException {
        return modelDao.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new IdNotFoundException("Aucun modèle ne correspond à cet identifiant"));
    }

    /**
     * Crée un nouveau modèle en base de données.
     *
     * @param newModel données du modèle à créer
     * @return le modèle créé sous forme de DTO
     */
    public ModelDto create(Model newModel) {
        newModel.setId(null);
        modelDao.save(newModel);
        return mapper.toDto(newModel);
    }

    /**
     * Supprime un modèle à partir de son identifiant.
     *
     * @param id identifiant unique du modèle à supprimer
     * @throws IdNotFoundException si aucun modèle ne correspond à cet identifiant
     */
    public void delete(int id) throws IdNotFoundException {
        modelDao.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Aucun modèle ne correspond à cet identifiant"));
        modelDao.deleteById(id);
    }

    /**
     * Met à jour un modèle existant en remplaçant ses données.
     *
     * @param id identifiant unique du modèle à mettre à jour
     * @param modelToUpdate nouvelles données du modèle
     * @throws IdNotFoundException si aucun modèle ne correspond à cet identifiant
     */
    public void update(int id, Model modelToUpdate) throws IdNotFoundException {
        modelDao.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Aucun modèle ne correspond à cet identifiant"));
        modelToUpdate.setId(id);
        modelDao.save(modelToUpdate);
    }
}
