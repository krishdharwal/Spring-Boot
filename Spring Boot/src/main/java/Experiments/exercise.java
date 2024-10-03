//package Experiments;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//
//@RestController
//@RequestMapping("/yt")
//public class CommandController {
//
//    @GetMapping("/download/{link}")
//    public String runCommand(@PathVariable String link) {
//        StringBuilder output = new StringBuilder();
//        ProcessBuilder processBuilder = new ProcessBuilder();
//
//        processBuilder.directory(new java.io.File("/home/jarvis/Downloads"));
//        processBuilder.command( "bash","-c","yt-dlp" + link);
//
//        try {
//            Process process = processBuilder.start();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            String line;
//
//            while ((line = reader.readLine()) != null) {
//                output.append(line).append("\n");
//            }
//
//            int exitCode = process.waitFor();
//            if (exitCode == 0) {
//                return "Command executed successfully:\n" + output.toString();
//            } else {
//                return "Error executing command. Exit code: " + exitCode;
//            }
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//            return "Exception occurred: " + e.getMessage();
//        }
//    }
//}