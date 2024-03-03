package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.model.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class HomeController {

    private final UserService userService;
    private final FileService fileService;
    private final NoteService noteService;
    private final CredentialsService credentialsService;

    private final EncryptionService encryptionService;

    public HomeController(UserService userService, FileService fileService, NoteService noteService, CredentialsService credentialsService, EncryptionService encryptionService) {
        this.userService = userService;
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialsService = credentialsService;
        this.encryptionService = encryptionService;
    }

    @GetMapping("/home")
    public String homeView(Authentication authentication, Model model) {
        User currentUser = userService.getUser(authentication.getName());
        if (currentUser == null) return "/login";
        int currentUserId = userService.getUser(authentication.getName()).getUserId();
        List<File> files = fileService.getFilesByUserid(currentUserId);
        files.forEach(file -> {
            System.out.println("homeView - file: " + file.getFileid() + file.getFileName());
        });
        model.addAttribute("files", fileService.getFilesByUserid(currentUserId));
        model.addAttribute("notes", noteService.getNotesByUserid(currentUserId));
        model.addAttribute("credentials", credentialsService.getCredentialsByUserid(currentUserId));
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }

    @PostMapping("/home/file/upload")
    public String uploadFile(
            @RequestParam("fileUpload") MultipartFile file,
            Authentication authentication,
            Model model
    ) {
        int currentUserId = userService.getUser(authentication.getName()).getUserId();
        if (file == null) {
            model.addAttribute("success", false);
        } else {
            try {
                int fileId = fileService.insertFile(file, currentUserId);
                if (fileId > 0) {
                    model.addAttribute("success", true);
                } else {
                    model.addAttribute("success", false);
                }
            } catch (IOException ioException) {
                model.addAttribute("success", false);
            }
        }
        return "result";
    }

    @PostMapping("/home/file/view")
    public ResponseEntity<ByteArrayResource> downloadFile(
            String fileid,
            Authentication authentication,
            Model model
    ) {
        File file = fileService.getFileByFileid(Integer.parseInt(fileid));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(new ByteArrayResource(file.getFileData()));

    }

    @PostMapping("/home/file/delete")
    public String deleteFile(
            String fileid,
            Authentication authentication,
            Model model
    ) {
        System.out.println("deleteFile - fileid: " + fileid);
        int fileId = fileService.deleteFileByFileId(Integer.parseInt(fileid));
        if (fileId > 0) {
            model.addAttribute("success", true);
        } else {
            model.addAttribute("success", false);
        }
        return "result";
    }

    @PostMapping("home/note/add")
    public String addNote(
            Note note,
            Authentication authentication,
            Model model
    ) {
        int currentUserId = userService.getUser(authentication.getName()).getUserId();
        int result;
        if (note.getNoteId() == null) {
            result = noteService.insertNote(note, currentUserId);
        }
        else {
            note.setUserid(currentUserId);
            result = noteService.updateNote(note);
        }
        if (result > 0) {
            model.addAttribute("success", true);
        } else {
            model.addAttribute("success", false);
        }
        return "result";
    }

    @PostMapping("home/note/delete")
    public String deleteNote(
            String noteId,
            Authentication authentication,
            Model model
    ) {
        int fileId = noteService.deleteNote(Integer.parseInt(noteId));
        if (fileId > 0) {
            model.addAttribute("success", true);
        } else {
            model.addAttribute("success", false);
        }
        return "result";
    }

    @PostMapping("home/note/modify")
    public String modifyNote(
            Note note,
            Authentication authentication,
            Model model
    ) {
        int result = noteService.updateNote(note);
        if (result > 0) {
            model.addAttribute("success", true);
        } else {
            model.addAttribute("success", false);
        }
        return "result";
    }


    @PostMapping("home/credential/add")
    public String addCredential(
            Credential credential,
            Authentication authentication,
            Model model
    ) {
        int currentUserId = userService.getUser(authentication.getName()).getUserId();
        Integer currentCredentialId = credential.getCredentialId();
        int result;
        if (currentCredentialId == null) {
            result = credentialsService.insertCredential(credential, currentUserId);
        } else {
            credential.setUserid(currentUserId);
            result = credentialsService.updateCredential(credential);
        }

        if (result > 0) {
            model.addAttribute("success", true);
        } else {
            model.addAttribute("success", false);
        }
        return "result";
    }

    @PostMapping("home/credential/delete")
    public String deleteCredential(
            String credentialId,
            Authentication authentication,
            Model model
    ) {
        int fileId = credentialsService.deleteCredential(Integer.parseInt(credentialId));
        if (fileId > 0) {
            model.addAttribute("success", true);
        } else {
            model.addAttribute("success", false);
        }
        return "result";
    }

    @PostMapping("home/credential/modify")
    public String modifyCredential(
            Credential credential,
            Authentication authentication,
            Model model
    ) {
        int result = credentialsService.updateCredential(credential);
        if (result > 0) {
            model.addAttribute("success", true);
        } else {
            model.addAttribute("success", false);
        }
        return "result";
    }


}



