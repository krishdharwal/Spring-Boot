package ReviewsMS.services;


import ReviewsMS.DTOs.review_DTo;
import ReviewsMS.pojo.reviews_pojo;
import ReviewsMS.repo.review_repo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class review_service {

    @Autowired
    private review_repo repo;

    @Autowired
    private ModelMapper modelMapper;


    // save
    public void save(review_DTo reviewDTo){
        try{
        assert reviewDTo != null;
        repo.save(toReview(reviewDTo));
        }catch (Exception e) {
            log.error("error in save in review services ");
        }
    }

    public reviews_pojo findById(ObjectId id){
        try {
            reviews_pojo users = repo.findById(id).orElse(null);
            if (users  != null) {
                return users;
            }
        }catch (Exception e){
            log.error("-- error in findbyid in  JOB services --");
            return null;
        }
        return null;
    }

    // delete
    public void delete(ObjectId id){
        try {
            repo.deleteById(id);
        }catch (Exception e){
            log.error("-- error in delete in review serices --");
        }
    }


    // mapper
    public reviews_pojo toReview(review_DTo reviewDTo){
        assert reviewDTo != null;
        return modelMapper.map(reviewDTo, reviews_pojo.class);
    }

    public List<reviews_pojo> findAll() {
        try {
            return repo.findAll();
        } catch (Exception e) {
            log.error("-- errror in find all in service review ");        }
        return null;
    }


    // update
    public void update(review_DTo reviewDTo) {
        try{
            reviews_pojo reviewsPojo = repo.findById(
                         reviewDTo.getId())
                         .orElseThrow(() -> new NotFoundException("Review not found")
                         );
            reviewsPojo.setRating(reviewDTo.getRating());
            reviewsPojo.setReview(reviewDTo.getReview());
            repo.save(reviewsPojo);
        }catch (Exception e){
            log.error("-- error while updatating the review --");
        }
    }
}
