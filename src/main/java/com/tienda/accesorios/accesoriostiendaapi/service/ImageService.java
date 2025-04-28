package com.tienda.accesorios.accesoriostiendaapi.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageService {

    private static final String IMAGE_DIRECTORY = "uploads/";

    public String saveImage(MultipartFile imageFile) throws IOException {
        if (imageFile.isEmpty()) {
            System.out.println("Archivo vacío, no se guardará.");
            return null;
        }

        // Asegurar que el directorio de imágenes exista
        File directory = new File(IMAGE_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Generar un nombre único para la imagen
        String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
        Path filePath = Paths.get(IMAGE_DIRECTORY, fileName);

        if (imageFile != null) {
            System.out.println("Nombre original de la imagen: " + imageFile.getOriginalFilename());
            System.out.println("Tamaño del archivo: " + imageFile.getSize());
            System.out.println("¿Archivo vacío?: " + imageFile.isEmpty());
        }

        // Guardar la imagen en el servidor
        Files.write(filePath, imageFile.getBytes());

        System.out.println("Imagen guardada en: " + filePath);

        return fileName;  // Retorna el nombre del archivo guardado
    }

    public byte[] getImage(String fileName) throws IOException {
        Path filePath = Paths.get(IMAGE_DIRECTORY, fileName);
        return Files.readAllBytes(filePath);
    }

    public void deleteImage(String fileName) throws IOException {
        if (fileName == null || fileName.isEmpty()) {
            return;
        }

        Path filePath = Paths.get(IMAGE_DIRECTORY, fileName);
        if (Files.exists(filePath)) {
            Files.delete(filePath);
            System.out.println("Imagen eliminada: " + filePath);
        } else {
            System.out.println("La imagen no existe en: " + filePath);
        }
    }
}
