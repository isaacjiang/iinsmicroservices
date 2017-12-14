package com.iins.security;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserModel,String> {
//
   //public UserDetails findAll(String Id);
    public void save(String username);
//    public UserModel findbyUsername(String username);
//    public UserModel findbyEmail(String email);
}
