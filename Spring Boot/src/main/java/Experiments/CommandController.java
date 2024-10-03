package Experiments;


import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/yt")
public class CommandController {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private MongoTemplate mongoDbFactory;

    @GetMapping("/download/{ttl}")
    public String runCommand( @PathVariable long ttl) {
        String link = "https://youtu.be/c8ItaxCEsnM?si=DIbJ3OB2gY7GdCVl";
        StringBuilder output = new StringBuilder();
        ProcessBuilder processBuilder = new ProcessBuilder();

        // Temporary directory for storing downloaded video
        String tempDir = "/home/jarvis/Downloads";
        processBuilder.directory(new java.io.File(tempDir));
        processBuilder.command("bash", "-c", "yt-dlp " + link);

        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                // Assume yt-dlp outputs the downloaded file path
                String downloadedFilePath = tempDir + "/downloaded_video.mp4"; // You may need to adjust this based on actual file name

                // Read the file and upload to MongoDB GridFS
                Path videoPath = Paths.get(downloadedFilePath);
                try (InputStream videoStream = Files.newInputStream(videoPath)) {
                    GridFSBucket gridFSBucket = GridFSBuckets.create(mongoDbFactory.getDb());

                    // Set upload options including the TTL (expiration time)
                    GridFSUploadOptions options = new GridFSUploadOptions()
                            .metadata(new Document("uploadDate", new Date())
                                    .append("expireAt", new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(ttl))));

                    gridFSBucket.uploadFromStream("video_" + System.currentTimeMillis() + ".mp4", videoStream, options);
                }

                // Delete the local file after upload
                Files.delete(videoPath);

                return "Video uploaded to MongoDB successfully with TTL of " + ttl + " hours.";
            } else {
                return "Error executing command. Exit code: " + exitCode;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Exception occurred: " + e.getMessage();
        }
    }
}
