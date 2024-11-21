//package movies.impl;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import movies.pojo.user_pojo;
//import movies.service.User_Queries;
//
//@Service
//@Slf4j
//public class user_Impl implements UserDetailsService {
//
//    @Autowired
//    private User_Queries userRepo;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        try {
//            user_pojo user = userRepo.findByName(username);
//            assert user != null;
//            return User.builder()
//                    .username(user.getName())
//                    .password(user.getPassword())
//                    .roles(user.getRole().toString())
//                    .build();
//        } catch (Exception e) {
//            log.error(" -- error while fetching user -- ");
//            throw new UsernameNotFoundException(" -- error while fetching user -- ");
//        }
//    }
//}
