package YT.service;

import YT.entity.Download;
import YT.repo.DownloadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;

@Service
public class DownloadService {

    @Autowired
    private DownloadRepository downloadRepository;

    public void submitDownload(String videoUrl) {
        Download download = new Download();
        download.setVideoUrl(videoUrl);
        download.setDownloadStatus("PENDING");
        download.setRequestTime(new Date());
        downloadRepository.save(download);

        // Asynchronously start the download
        new Thread(() -> downloadVideo(download)).start();
    }

    private void downloadVideo(Download download) {
        try {
            download.setDownloadStatus("IN_PROGRESS");
            downloadRepository.save(download);

            // Specify the directory to save the video (Linux path as example)
            String outputDir = "/home/jarvis/Downloads/%(title)s.%(ext)s";  // Adjust the path for your OS

            // Run yt-dlp command with output path
            ProcessBuilder builder = new ProcessBuilder(" yt-dlp " , download.getVideoUrl());
            Process process = builder.start();

            // Capture process output and errors
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);  // For debugging, or use a logger here
            }

            process.waitFor();

            if (process.exitValue() == 0) {
                // On successful download
                download.setDownloadStatus("COMPLETED");
                download.setFilePath(outputDir.replace("%(title)s", download.getVideoUrl())  // Just for demonstration, adjust as needed
                        .replace("%(ext)s", "mp4")); // Assuming mp4, but you can extract actual extension

                download.setCompletionTime(new Date());
            } else {
                // Handle non-zero exit codes
                download.setDownloadStatus("FAILED");
            }

            downloadRepository.save(download);

        } catch (Exception e) {
            // Log the exception and mark as failed
            e.printStackTrace();
            download.setDownloadStatus("FAILED");
            downloadRepository.save(download);
        }
    }
}
