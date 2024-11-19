package Movie_Test;

import movies.pojo.movie_pojo;
import movies.repo.movie_repo;
import movies.service.Movie_Query;
import movies.service.movie_service;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;


public class movieMockitoTests {

    @InjectMocks
    private movie_service movieService;

    @Mock
    private movie_repo repo;

    @InjectMocks
    private Movie_Query movieQuery;


    // initialize all the @Mock classes cause i have removed @SpringBootTest
    @BeforeEach
    void SetUp(){
        MockitoAnnotations.initMocks(this);
    }


//    @Test
//    void SearchForMovie(){
//        // fake data
//        // when we call this method it will return this fake data
//    when( movieQuery.find_Movie_By_Name(ArgumentMatchers.anyString())).thenReturn(
//                movie_pojo.builder().name("Triangle").build()
//        );
//    movie_pojo moviePojo = movieQuery.find_Movie_By_Name("ok");
//        Assertions.assertNotNull(moviePojo);
//    }

//    @Test
//    void Book_Seats_of_the_hall_Test(){
//        movieService.Book_Seats_of_the_hall(Arrays.asList(true, false , true , true ,false, false));
//    }
}
