package com.udacity.jwdnd.course1.cloudstorage.services;

import java.io.IOException;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;

@Service
public class FileService {

    private final UserMapper userMapper;
    private final FileMapper fileMapper;

    public FileService(UserMapper userMapper, FileMapper fileMapper) {
        this.userMapper = userMapper;
        this.fileMapper = fileMapper;
    }

    public int uploadFile(MultipartFile f) throws IOException {
        File file = new File(f.getOriginalFilename(),
                f.getContentType(),
                String.valueOf(f.getSize()),
                getCurrentUserId(),
                f.getBytes());

        return fileMapper.insert(file);
    }

    public List<File> getAll() {
        return fileMapper.getAll(getCurrentUserId());
    }

    public boolean isFilenameAvailable(String filename) {
        return fileMapper.getFileByFilename(filename) == null;
    }

    public File getFileByFileId(int fileId) {
        return fileMapper.getFileByFileId(fileId);
    }

    public Integer deleteFileByFileId(int fileId){
        return fileMapper.deleteFileByFileId(fileId);
    }

    private int getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();
        User user = userMapper.getUser(username);
        return user.getUserId();
    }

}
