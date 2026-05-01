/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.edu.senai.fatesg.ads3.car_repair.core.controllers;

import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Gabriel
 */
public interface IGenereicController {
    
    ResponseEntity findById(UUID id);

    ResponseEntity findAll(Pageable pageable);

    ResponseEntity insert(Object dto);

    ResponseEntity update(UUID id, Object dto);

    ResponseEntity delete(UUID id);
}
