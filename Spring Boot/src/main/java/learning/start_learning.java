//package learning;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.data.mongodb.MongoDatabaseFactory;
//import org.springframework.data.mongodb.MongoTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//
//@SpringBootApplication
//@EnableTransactionManagement
//public class start_learning {
//    public static void main
//            (String[] args) {
//        SpringApplication.run(start_learning.class,args);
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager(MongoDatabaseFactory factory){
//        return new MongoTransactionManager(factory);
//    }
//
////    @Bean
////    public RedisTemplate redisTemplate(){
////        return new RedisTemplate();
////    }
//}
