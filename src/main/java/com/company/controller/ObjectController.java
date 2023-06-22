package com.company.controller;

import com.company.dto.object.ObjectShortInfoDTO;
import com.company.dto.ResponseInfoDTO;
import com.company.dto.object.ObjectCreatedDTO;
import com.company.dto.object.ObjectFullInfoDTO;
import com.company.dto.object.ObjectUpdateDTO;
import com.company.service.ObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/object")
public class ObjectController {

    @Autowired
    private ObjectService objectService;

    @PostMapping("")
    public ResponseEntity<?> created(@RequestBody @Valid ObjectCreatedDTO dto) {

        ResponseInfoDTO responseInfoDTO = objectService.createObject(dto);
        return ResponseEntity.ok(responseInfoDTO);
    }

    @PutMapping("/man/{id}")
    public ResponseEntity<?> update(@RequestBody ObjectUpdateDTO dto,
                                    @PathVariable("id") String videoId) {

        ResponseInfoDTO update = objectService.update(dto, videoId);
        return ResponseEntity.ok(update);
    }

    @GetMapping("/man/pagination")
    public ResponseEntity<?> pagination() {

        List<ObjectShortInfoDTO> dtoList = objectService.pagination();
        return ResponseEntity.ok(dtoList);

    }

    @GetMapping("/adm/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String videoId) {

        ObjectFullInfoDTO dto = objectService.getById(videoId);
        return ResponseEntity.ok(dto);

    }
    @PutMapping("/adm/changeStatus/{id}")
    public ResponseEntity<?> publish(@PathVariable("id") String videoId) {

        ResponseInfoDTO dto = objectService.publish(videoId);
        return ResponseEntity.ok(dto);

    }

    @DeleteMapping("/adm/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String videoId) {

        ResponseInfoDTO dto = objectService.delete(videoId);
        return ResponseEntity.ok(dto);

    }



}
