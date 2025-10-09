package com.example.Dobara1.usermaster;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

//    @Autowired
//    private UserMasterRepository userRepo;
//
//    public void saveUser(UserMaster user) { 
//        userRepo.save(user);
//    }

private  final UserRepo userRepo;

public UserService(UserRepo userRepo){
    this.userRepo = userRepo;
}



    public void save(UserMasterEntity entity) {
        userRepo.save(entity);
    }

   public UserMasterEntity login(String email , String password){
   Optional<UserMasterEntity> userMasterEntity = userRepo.findByEmailAndPassword(email , password);
    return userMasterEntity.orElse(null);
   }

//public void saveUser(UserMasterEntity userMasterEntity , MultipartFile file){
//    try{
//        if(file != null && !file.isEmpty()){
//            userMasterEntity.setProfile_pic(file.getBytes());
//        }
//        userRepo.save(userMasterEntity);
//    }catch (Exception e){
//        throw new RuntimeException("Error Saving user: " + e.getMessage());
//    }
//}


}
