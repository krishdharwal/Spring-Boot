package YT.controller;


import YT.entity.linkPojo;
import YT.service.DownloadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import java.io.*;


@RestController
@RequestMapping("/yt")
@Slf4j
public class DownloadController {

    @Autowired
    private DownloadService downloadService;


    @GetMapping("/health")
    public String health(){
        return "jarvis -> yt app is running sir !";
    }

    @GetMapping("/history")
    public ResponseEntity<?> showall()  {
        try {
            return new ResponseEntity<>(downloadService.SHowAllHistory(), HttpStatus.ACCEPTED);
        }catch (Exception e){
            log.error("-- error in showall in controller yt");
            return null;
        }
    }

    @PostMapping("/download")
    public String runCommand(@RequestBody linkPojo link) {
        StringBuilder output = new StringBuilder();
        ProcessBuilder processBuilder = new ProcessBuilder();

        processBuilder.directory(new java.io.File("/home/jarvis/Downloads"));
        processBuilder.command( "bash","-c","yt-dlp " + link.getLink());

        try {
            // saving the history in db
            downloadService.SAveYThistory(link);

            // command running here
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return "Command executed successfully:\n" + output.toString();
            } else {
                return "Error executing command. Exit code: " + exitCode;
            }
        } catch (Exception e) {
            return "Exception occurred: " + e.getMessage();
        }
    }
}
