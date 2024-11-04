package YT.service;

import YT.entity.linkPojo;
import YT.repo.DownloadRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Slf4j
public class DownloadService {

    @Autowired
    private DownloadRepository downloadRepository;

    public void SAveYThistory(linkPojo body){
        try {
            body.setDateTime(LocalDateTime.now());
            downloadRepository.save(body);
        }catch (Exception e){
            log.error(" error in save history service layer");
        }
    }

    public List<linkPojo> SHowAllHistory(){
        try{
           return downloadRepository.findAll();
        }catch (Exception e){
            log.error("error in showAllHistory in service layer ");
            return null;
        }
    }

}
