package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.ModelDao;
import com.mns.cda.loc_mns.dto.ModelDto;
import com.mns.cda.loc_mns.mapper.ModelMapper;
import com.mns.cda.loc_mns.model.Model;
import com.mns.cda.loc_mns.repository.ModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public List<ModelDto> getAllModels() {
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
     * @throws IllegalArgumentException si aucun modèle ne correspond à cet identifiant
     */
    public ModelDto getModel(int id) {
        Optional<Model> optionalModel = modelDao.findById(id);
        if (optionalModel.isEmpty()) {
            throw new IllegalArgumentException("Aucun modèle ne correspond à cet identifiant");
        }
        return mapper.toDto(optionalModel.get());
    }

    /**
     * Crée un nouveau modèle en base de données.
     *
     * @param newModel données du modèle à créer
     * @return le modèle créé sous forme de DTO
     */
    public ModelDto createModel(Model newModel) {
        newModel.setId(null);
        modelDao.save(newModel);
        return mapper.toDto(newModel);
    }

    /**
     * Supprime un modèle à partir de son identifiant.
     *
     * @param id identifiant unique du modèle à supprimer
     * @throws IllegalArgumentException si aucun modèle ne correspond à cet identifiant
     */
    public void deleteModel(int id) {
        Optional<Model> optionalModel = modelDao.findById(id);
        if (optionalModel.isEmpty()) {
            throw new IllegalArgumentException("Aucun modèle ne correspond à cet identifiant");
        }
        modelDao.deleteById(id);
    }

    /**
     * Met à jour un modèle existant en remplaçant ses données.
     *
     * @param id identifiant unique du modèle à mettre à jour
     * @param modelToUpdate nouvelles données du modèle
     * @throws IllegalArgumentException si aucun modèle ne correspond à cet identifiant
     */
    public void updateModel(int id, Model modelToUpdate) {
        Optional<Model> optionalModel = modelDao.findById(id);
        if (optionalModel.isEmpty()) {
            throw new IllegalArgumentException("Aucun modèle ne correspond à cet identifiant");
        }
        modelToUpdate.setId(id);
        modelDao.save(modelToUpdate);
    }
}
