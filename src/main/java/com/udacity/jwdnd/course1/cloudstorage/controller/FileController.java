package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;

@Controller
@RequestMapping("/file-upload")
public class FileController {

    private final FileService service;

    public FileController(FileService service) {
        this.service = service;
    }

    @PostMapping()
    public String handleUploadFile(@RequestParam("fileUpload") MultipartFile multipartFile, Model model) {
        String uploadSuccess = "Upload file success";
        String uploadError = null;
        if (multipartFile.isEmpty()) {
            uploadError = "Please select file.";
        }
        if (!service.isFilenameAvailable(multipartFile.getOriginalFilename())) {
            uploadError = "The filename already exists.";
        }
        if (uploadError == null) {
            try {
                if (service.uploadFile(multipartFile) < 0) {
                    uploadError = "There was an error uploading. Please try again.";
                }
            } catch (Exception ex) {
                uploadError = "There was an error uploading. Please try again.";
            }
        }

        if (uploadError == null) {
            model.addAttribute("uploadSuccess", uploadSuccess);
        } else {
            model.addAttribute("uploadError", uploadError);
        }

        loadAllFiles(model);

        return "home";
    }

    @GetMapping()
    public void handleDownloadFile(HttpServletResponse response, Model model, @RequestParam("fileId") String fileId) {
        try {
            File file = service.findByFileId(Integer.parseInt(fileId));
            if (file == null) {
                model.addAttribute("uploadError", "File is not existed. Please try again.");
                loadAllFiles(model);
            }
            byte[] data = file.getFiledata();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment; filename=" + file.getFilename());
            response.setContentLength(data.length);
            InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(data));
            FileCopyUtils.copy(inputStream, response.getOutputStream());
        } catch (IOException ex) {
            model.addAttribute("uploadError", "File is not existed. Please try again.");
            loadAllFiles(model);
        }
    }

    @GetMapping(value = "/delete")
    private String handleDeleteFile(Model model, @RequestParam("fileId") String fileId) {
        String uploadSuccess = "Delete file success";
        String uploadError = null;
        if (service.deleteByFileId(Integer.parseInt(fileId)) < 0) {
            uploadError = "File is not existed. Please try again.";
        }

        if (uploadError == null) {
            model.addAttribute("uploadSuccess", uploadSuccess);
        } else {
            model.addAttribute("uploadError", uploadError);
        }

        loadAllFiles(model);

        return "home";
    }

    private void loadAllFiles(Model model) {
        List<File> files = service.findAll();
        model.addAttribute("files", files);
    }
}
