package com.catsaredope.inkparadise.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.catsaredope.inkparadise.Repositories.MangaFolderRepository;
import com.catsaredope.inkparadise.Models.MangaFolder;

@RestController
@RequestMapping("/api/v1")
public class MangaFolderController {
    @Autowired
    private MangaFolderRepository mangaFolderRepository;

    @GetMapping("/manga_folders")
    public List<MangaFolder> getAllMangaFolders() {
        return mangaFolderRepository.findAll();
    }

    @GetMapping("/manga_folders/{id}")
    public ResponseEntity<MangaFolder> getMangaFolderById(@PathVariable(value = "id") Long mangaFolderId)
            throws Exception {
        if (mangaFolderId == null) {
            throw new IllegalArgumentException("Manga folder id cannot be null");
        }
        MangaFolder mangaFolder = mangaFolderRepository.findById(mangaFolderId)
                .orElseThrow(() -> new Exception("Manga folder not found for this id :: " + mangaFolderId));
        return ResponseEntity.ok().body(mangaFolder);
    }

    @PostMapping("/manga_folders/new")
    public MangaFolder createMangaFolder(@Valid @RequestBody MangaFolder mangaFolder) {
        if (mangaFolder.getUserId() == 0 || mangaFolder.getFolderName() == null) {
            throw new IllegalArgumentException("Manga folder user id and folder name cannot be null");
        }
        return mangaFolderRepository.save(mangaFolder);
    }

    @PutMapping("/manga_folders/update/{id}")
    public ResponseEntity<MangaFolder> updateMangaFolder(@PathVariable(value = "id") Long mangaFolderId,
            @Valid @RequestBody MangaFolder mangaFolderDetails) throws Exception {
        if (mangaFolderId == null) {
            throw new IllegalArgumentException("Manga folder id cannot be null");
        }
        MangaFolder mangaFolder = mangaFolderRepository.findById(mangaFolderId)
                .orElseThrow(() -> new Exception("Manga folder not found for this id :: " + mangaFolderId));
        mangaFolder.setUserId(mangaFolderDetails.getUserId());
        mangaFolder.setFolderName(mangaFolderDetails.getFolderName());
        final MangaFolder updatedMangaFolder = mangaFolderRepository.save(mangaFolder);
        return ResponseEntity.ok(updatedMangaFolder);
    }

    @DeleteMapping("/manga_folders/delete/{id}")
    public Map<String, Boolean> deleteMangaFolder(@PathVariable(value = "id") Long mangaFolderId) throws Exception {
        if (mangaFolderId == null) {
            throw new IllegalArgumentException("Manga folder id cannot be null");
        }
        MangaFolder mangaFolder = mangaFolderRepository.findById(mangaFolderId)
                .orElseThrow(() -> new Exception("Manga folder not found for this id :: " + mangaFolderId));

        if (mangaFolder == null) {
            throw new Exception("Manga folder not found for this id :: " + mangaFolderId);
        }
        mangaFolderRepository.delete(mangaFolder);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}