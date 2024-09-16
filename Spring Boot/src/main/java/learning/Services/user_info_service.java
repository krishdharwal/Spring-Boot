package learning.Services;


  import learning.Mongo_Repositories.user_info_repo;
import learning.POJO.user;
import learning.POJO.user_info;
  import org.bson.types.ObjectId;
  import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class user_info_service {

    @Autowired
    private user_info_repo repo_info;

    @Autowired
    private user_service repo_user;


    @Autowired
    private user_service userService;

    public Object showall() {
        return repo_info.findAll();
    }

    public void save(user_info body,String userName) {
        user User =  userService.findByname(userName);
        User.getInfo().add(body);
        repo_info.save(body);
        repo_user.save_simple(User);
    }

//    public void update(user_info newInfo) {
//        user_info info = repo_info.findById(id).orElse(null);
//        info.setInfo(newInfo.getInfo());
//        repo_info.save(info);
//
//    }

    public void deleteById(String userName, ObjectId id) {
        user User = repo_user.findByname(userName);
        User.getInfo().removeIf(x -> x.getId().equals(id));
        repo_info.deleteById(id);
        repo_user.save_simple(User);
    }

    public Object findByid(ObjectId id) {
        return repo_info.findById(id);
    }

    public void update(user_info new_info, ObjectId id) {
       user_info info_body = repo_info.findById(id).orElse(null);
       if (info_body != null){
           info_body.setInfo(new_info.getInfo());
           repo_info.save(info_body);
       }
    }
}
