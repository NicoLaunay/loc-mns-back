package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.ModelDao;
import com.mns.cda.loc_mns.dto.ModelDto;
import com.mns.cda.loc_mns.mapper.ModelMapper;
import com.mns.cda.loc_mns.model.Model;
import com.mns.cda.loc_mns.repository.ModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        System.out.println(
            repository.findAll()
                    .stream()
                    .map(mapper::toDto)
                    .toList()
        );
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    /**
     * Récupère un modèle à partir de son identifiant.
     *
     * @param id identifiant unique du modèle recherché
     * @return une réponse HTTP contenant le modèle sous forme de DTO si il existe (200 OK),
     *         ou un statut 404 (NOT_FOUND) si aucun modèle ne correspond à cet identifiant
     */
    public ResponseEntity<ModelDto> getModel(int id) {
        Optional<Model> optionalModel = modelDao.findById(id);
        if (optionalModel.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(mapper.toDto(optionalModel.get()), HttpStatus.OK);
    }

    /**
     * Crée un nouveau modèle en base de données.
     *
     * @param newModel données du modèle à créer
     * @return une réponse HTTP contenant le modèle créé sous forme de DTO (201 CREATED)
     */
    public ResponseEntity<ModelDto> createModel(Model newModel) {
        newModel.setId(null);
        modelDao.save(newModel);
        return new ResponseEntity<>(mapper.toDto(newModel), HttpStatus.CREATED);
    }

    /**
     * Supprime un modèle à partir de son identifiant.
     *
     * @param id identifiant unique du modèle à supprimer
     * @return une réponse HTTP avec le statut 204 (NO_CONTENT) si la suppression est effectuée,
     *         ou 404 (NOT_FOUND) si aucun modèle ne correspond à cet identifiant
     */
    public ResponseEntity<Void> deleteModel(int id) {
        Optional<Model> optionalModel = modelDao.findById(id);

        if (optionalModel.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        modelDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Met à jour un modèle existant en remplaçant ses données.
     *
     * @param id identifiant unique du modèle à mettre à jour
     * @param modelToUpdate nouvelles données du modèle
     * @return une réponse HTTP avec le statut 204 (NO_CONTENT) si la mise à jour est effectuée,
     *         ou 404 (NOT_FOUND) si aucun modèle ne correspond à cet identifiant
     */
    public ResponseEntity<Void> updateModel(int id, Model modelToUpdate) {
        Optional<Model> optionalModel = modelDao.findById(id);

        if (optionalModel.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // On écrase l'id du JSON par celui en paramètre
        modelToUpdate.setId(id);
        modelDao.save(modelToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
//
//    private List<Model> cleanListRelations(List<Model> models) {
//        if (models != null) {
//            for(Model model : models) {
//                cleanModelRelations(model);
//            }
//        }
//        return models;
//    }
//
//    private Model cleanModelRelations(Model model) {
//        List<Model> components = model.getComponents();
//        if (components != null) {
//            for (Model component : model.getComponents()) {
//                component.setComponents(null);
//                component.setParents(null);
//            }
//        }
//        List<Model> parents = model.getParents();
//        if (parents != null) {
//            for (Model parent : model.getParents()) {
//                parent.setComponents(null);
//                parent.setParents(null);
//            }
//        }
//        return model;
//    }
}
