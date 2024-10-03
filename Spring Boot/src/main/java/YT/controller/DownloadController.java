package YT.controller;

import YT.entity.Download;
import YT.repo.DownloadRepository;
import YT.service.DownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/download")
public class DownloadController {

    @Autowired
    private DownloadService downloadService;

    @Autowired
    private DownloadRepository downloadRepository;

    @PostMapping("/submit")
    public ResponseEntity<String> submitDownloadRequest(@RequestBody String videoUrl) {
        downloadService.submitDownload(videoUrl);
        return ResponseEntity.ok("Download request submitted!");
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<Download> getDownloadStatus(@PathVariable String id) {
        return downloadRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

