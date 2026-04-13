package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.StateDao;
import com.mns.cda.loc_mns.dto.StateDto;
import com.mns.cda.loc_mns.model.State;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StateService {

    @Autowired
    protected StateDao stateDao;

    public List<StateDto> getAllStates() {
        return stateDao.findAll()
                .stream()
                .map(state -> new StateDto(state.getId(), state.getName()))
                .toList();
    }

    public ResponseEntity<State> getState(int id) {
        Optional<State> optionalState = stateDao.findById(id);
        if (optionalState.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalState.get(), HttpStatus.OK);
    }

    public ResponseEntity<State> createState(State newState) {
        newState.setId(null);
        stateDao.save(newState);
        return new ResponseEntity<>(newState, HttpStatus.CREATED);
    }

    public ResponseEntity<Void> deleteState(int id) {
        Optional<State> optionalState = stateDao.findById(id);

        if (optionalState.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        stateDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Void> updateState(int id, State stateToUpdate) {
        Optional<State> optionalState = stateDao.findById(id);

        if (optionalState.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // On écrase l'id du JSON par celui en paramètre
        stateToUpdate.setId(id);
        stateDao.save(stateToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
