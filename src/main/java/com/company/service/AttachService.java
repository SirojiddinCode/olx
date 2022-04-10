package com.company.service;

import com.company.dto.AttachDTO;
import com.company.entity.AttachEntity;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.AttachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class AttachService {
    @Autowired
    private AttachRepository attachRepository;

    @Value("${attach.upload.folder}")
    private String uploadFolder;
    @Value("${attach.open.url}")
    private String attachOpenUrl;

    public AttachDTO saveFile(MultipartFile file) {

        String filePath = getYmDString(); // 2021/07/13
        String key = UUID.randomUUID().toString();
        String extension = getExtension(file.getOriginalFilename()); // .jpg,.pdf

        File folder = new File(uploadFolder + "/" + filePath); // uploads/2021/07/13
        if (!folder.exists()) {
            folder.mkdirs();
        }

        try {
            byte[] bytes = file.getBytes();
            //  uploads/2021/07/13/dasda_dasda_dasda_dasda.jpg
            Path path = Paths.get(uploadFolder + "/" + filePath + "/" + key + "." + extension);
            Files.write(path, bytes);

            AttachEntity entity = new AttachEntity();
            entity.setKey(key);
            entity.setExtension(extension);
            entity.setOriginName(file.getOriginalFilename());
            entity.setSize(file.getSize());
            entity.setFilePath(filePath);
            attachRepository.save(entity);

            AttachDTO attachDTO = new AttachDTO();
            attachDTO.setId(entity.getId());
            attachDTO.setKey(entity.getKey());
            attachDTO.setFilePath(entity.getFilePath());
            attachDTO.setCreatedDate(entity.getCreatedDate());
            attachDTO.setSize(entity.getSize());
            attachDTO.setOriginName(entity.getOriginName());
            attachDTO.setExtension(entity.getExtension());
            attachDTO.setUrl(attachOpenUrl + "/" + key);
            return attachDTO;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getPathToFile(String folder, String filePath, String key, String extension) {
        return folder + "/" + filePath + "/" + key + "." + extension;
    }

    public byte[] loadAttach(String key) {
        AttachEntity entity = getByKey(key);
        byte[] imageInByte = null;
        BufferedImage originalImage;
        try {
            String pathToFile = getPathToFile(uploadFolder, entity.getFilePath(),
                    entity.getKey(), entity.getExtension());
            originalImage = ImageIO.read(new File(pathToFile));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            ImageIO.write(originalImage, entity.getExtension(), baos);
            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();
        } catch (Exception e) {
            return new byte[0];
        }
        return imageInByte;
    }

    public static String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DATE);

        return year + "/" + month + "/" + day + "/";
    }

    public String getExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }

    public void delete(String key) {
        AttachEntity entity = getByKey(key);
        String filepath = getPathToFile(uploadFolder, entity.getFilePath(), key, entity.getExtension());
        attachRepository.deleteByKey(key);
        File deleteFile = new File(filepath);
        if (deleteFile.exists()) {
            deleteFile.delete();
        }
    }

    public AttachDTO toAttachDto(AttachEntity entity) {
        AttachDTO attachDTO = new AttachDTO();
        attachDTO.setId(entity.getId());
        attachDTO.setFilePath(entity.getFilePath());
        attachDTO.setKey(entity.getKey());
        attachDTO.setExtension(entity.getExtension());
        attachDTO.setCreatedDate(entity.getCreatedDate());
        attachDTO.setUrl(attachOpenUrl + "/" + entity.getKey());
        attachDTO.setOriginName(entity.getOriginName());
        attachDTO.setSize(entity.getSize());
        return attachDTO;
    }

    public AttachEntity getByKey(String key) {
        Optional<AttachEntity> op = attachRepository.findByKey(key);
        return op.orElseThrow(() -> new ItemNotFoundException("File Not Found"));

    }

    public Resource download(String fileName) {
        AttachEntity entity = getByKey(fileName);
        String filepath = getPathToFile(uploadFolder, entity.getFilePath(), fileName, entity.getExtension());
        Path file = Paths.get(filepath);
        try {
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Eror: " + e.getMessage());
        }
    }

    public AttachDTO update(MultipartFile file, String key) {
        AttachEntity entity = getByKey(key);
        delete(key);
        String newKey = UUID.randomUUID().toString();
        try {
            byte[] bytes = file.getBytes();
            String newFilePath = getPathToFile(uploadFolder, entity.getFilePath(),
                    newKey, getExtension(file.getOriginalFilename()));
            Path path = Paths.get(newFilePath);
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        entity.setSize(file.getSize());
        entity.setExtension(getExtension(Objects.requireNonNull(file.getOriginalFilename())));
        entity.setOriginName(file.getOriginalFilename());
        entity.setKey(newKey);
        entity.setSize(file.getSize());
        attachRepository.save(entity);
        return toAttachDto(entity);
    }

    public AttachEntity get(Integer id) {
        return attachRepository.findById(id).orElseThrow(()
                -> new ItemNotFoundException("Attach not Found"));
    }



}
