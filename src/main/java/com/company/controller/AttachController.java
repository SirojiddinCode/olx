package com.company.controller;

import com.company.dto.AttachDTO;
import com.company.service.AttachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/attach")
public class AttachController {
    @Autowired
    private AttachService attachService;

    @PostMapping("/upload")
    public ResponseEntity<AttachDTO > fileUpload(@RequestParam("file") MultipartFile file) {
        AttachDTO dto = attachService.saveFile(file);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping(value = "/get/{fileName}", produces = MediaType.ALL_VALUE)
    public byte[] getImage(@PathVariable("fileName") String fileName) {
        if (fileName != null && fileName.length() > 0) {
            try {
                return this.attachService.loadAttach(fileName);
            } catch (Exception e) {
                e.printStackTrace();
                return new byte[0];
            }
        }
        return null;
    }

    @DeleteMapping(value = "/delete/")
    public ResponseEntity<String> delete(@RequestParam("key") String key){
        attachService.delete(key);
        return ResponseEntity.ok("Delete");
    }

    @PutMapping("/update")
    public ResponseEntity<AttachDTO> update(@RequestParam("updating file") MultipartFile file,
                                            @RequestParam("key") String key){
        AttachDTO dto =  attachService.update(file,key);
        return ResponseEntity.ok(dto);
    }
}
