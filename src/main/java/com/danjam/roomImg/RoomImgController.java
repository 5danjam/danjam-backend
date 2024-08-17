package com.danjam.roomImg;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestController
@CrossOrigin
@RequiredArgsConstructor
public class RoomImgController {

    private final RoomImgServiceImpl ROOMIMGSERVICE;

    @RequestMapping(value = "/roomImg/insert", method = RequestMethod.POST)
    public Map<String, Object> FileUploads(HttpServletRequest request,
                                             @RequestParam(value = "roomId", required = false) Long roomId,
                                             @RequestParam(value = "file", required = false) MultipartFile[] files) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        StringBuilder fileNames = new StringBuilder();

        Path uploadPath = Paths.get("src/main/resources/static/uploads");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        for (MultipartFile mf : files) {
            if (mf.isEmpty()) {
                continue;
            }

            String originalFileName = mf.getOriginalFilename();
            long fileSize = mf.getSize();
            String extension = "";

            if (originalFileName != null && originalFileName.contains(".")) {
                extension = originalFileName.substring(originalFileName.lastIndexOf('.') + 1);
            }

            String uuid = UUID.randomUUID().toString();
            String safeFile = uuid + "." + extension;
            fileNames.append(",").append(safeFile);

            Path filePath = uploadPath.resolve(safeFile);
            try (InputStream inputStream = mf.getInputStream()) {
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

                // Save file info to the database
                RoomImgAddDTO roomImgAddDTO = new RoomImgAddDTO();
                roomImgAddDTO.setRoomId(roomId);
                roomImgAddDTO.setName(uuid); // UUID
                roomImgAddDTO.setNameOriginal(originalFileName); // Original filename
                roomImgAddDTO.setSize(String.valueOf(fileSize)); // File size
                roomImgAddDTO.setExt(extension); // File extension
                System.out.println("roomImgAddDTO: " + roomImgAddDTO);
                ROOMIMGSERVICE.insert(roomImgAddDTO);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String fileNamesString = fileNames.length() > 0 ? fileNames.substring(1) : "";
        resultMap.put("FileNames", fileNamesString);

        return resultMap;
    }
}
