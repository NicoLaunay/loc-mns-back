package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.EquipmentDao;
import com.mns.cda.loc_mns.model.Equipment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EquipmentService {

    @Autowired
    protected EquipmentDao equipmentDao;

    public List<Equipment> getAllEquipments() {
        return equipmentDao.findAll();
    }

    public ResponseEntity<Equipment> getEquipment(int id) {
        Optional<Equipment> optionalEquipment = equipmentDao.findById(id);
        if (optionalEquipment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalEquipment.get(), HttpStatus.OK);
    }

    public ResponseEntity<Equipment> createEquipment(Equipment newEquipment) {
        newEquipment.setId(null);
        equipmentDao.save(newEquipment);
        return new ResponseEntity<>(newEquipment, HttpStatus.CREATED);
    }

    public ResponseEntity<Void> deleteEquipment(int id) {
        Optional<Equipment> optionalEquipment = equipmentDao.findById(id);

        if (optionalEquipment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        equipmentDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Void> updateEquipment(int id, Equipment equipmentToUpdate) {
        Optional<Equipment> optionalEquipment = equipmentDao.findById(id);

        if (optionalEquipment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // On écrase l'id du JSON par celui en paramètre
        equipmentToUpdate.setId(id);
        equipmentDao.save(equipmentToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
