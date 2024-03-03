package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.model.mapper.FileMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    private boolean checkFileExistByName(String fileName){
        File file = fileMapper.getFileExitsByName(fileName);
        return file != null;
    }

    public int insertFile(MultipartFile multipartFile, int userId) throws IOException {
        if (checkFileExistByName(multipartFile.getOriginalFilename())) return -1;
        File file = new File(
                null,
                multipartFile.getOriginalFilename(),
                multipartFile.getContentType(),
                String.valueOf(multipartFile.getSize()),
                userId,
                multipartFile.getBytes());
        return fileMapper.insert(file);
    }

    public int deleteFileByFileId(int fileId) {
        return fileMapper.delete(fileId);
    }

    public List<File> getFilesByUserid(int userId) {
        return fileMapper.getFilesByUserId(userId);
    }

    public File getFileByFileid(int fileid) {
        return fileMapper.getFileById(fileid);
    }
}