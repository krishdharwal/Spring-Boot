package ReviewsMS.Producer;

import ReviewsMS.DTOs.review_DTo;
import ReviewsMS.pojo.reviews_pojo;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewMessageProducer {
    @Autowired
    public RabbitTemplate rabbitTemplate;

    @Autowired
    private ModelMapper modelMapper;

    public void SendMessage(review_DTo reviewsPojo){
        assert reviewsPojo != null;
        rabbitTemplate.convertAndSend("CompanyRatingQueue", reviewsPojo );

    }

    public review_DTo toDto(reviews_pojo reviewsPojo){
        assert reviewsPojo != null;
        return modelMapper.map(reviewsPojo, review_DTo.class);
    }
}
