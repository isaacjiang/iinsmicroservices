package com.iins.security;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<UserModel,String> {

  public List<UserModel> findAll();
  //public UserModel findbyUsername(String username);
//    public UserModel findbyEmail(String email);
}
