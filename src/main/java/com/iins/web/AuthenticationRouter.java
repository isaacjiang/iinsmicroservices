package com.iins.web;

import com.iins.security.UserModel;
import com.iins.security.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
 
@RestController
public class AuthenticationRouter {
 
   // @Autowired
    UserService userService =new UserService();  //Service which will do all data retrieval/manipulation work
 
     
    //-------------------Retrieve All Users--------------------------------------------------------
     
    @RequestMapping(value = "/api/user/", method = RequestMethod.GET)
    public ResponseEntity<List<UserModel>> listAllUsers() {
        List<UserModel> users = userService.findAllUsers();
        if(users.isEmpty()){
            return new ResponseEntity<List<UserModel>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<UserModel>>(users, HttpStatus.OK);
    }
 
 
    //-------------------Retrieve Single UserModel--------------------------------------------------------
     
    @RequestMapping(value = "/api/user/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserModel> getUser(@PathVariable("id") String id) {
        System.out.println("Fetching UserModel with id " + id);
        UserModel user = null; //userService.loadUserByUsername(id);
        if (user == null) {
            System.out.println("UserModel with id " + id + " not found");
            return new ResponseEntity<UserModel>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<UserModel>(user, HttpStatus.OK);
    }
 
     
//
//    //-------------------Create a UserModel--------------------------------------------------------
//
//    @RequestMapping(value = "/user/", method = RequestMethod.POST)
//    public ResponseEntity<Void> createUser(@RequestBody UserModel user, UriComponentsBuilder ucBuilder) {
//        System.out.println("Creating UserModel " + user.getName());
//
//        if (userService.isUserExist(user)) {
//            System.out.println("A UserModel with name " + user.getName() + " already exist");
//            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
//        }
//
//        userService.saveUser(user);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
//        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
//    }
//
//
//    //------------------- Update a UserModel --------------------------------------------------------
//
//    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
//    public ResponseEntity<UserModel> updateUser(@PathVariable("id") long id, @RequestBody UserModel user) {
//        System.out.println("Updating UserModel " + id);
//
//        UserModel currentUser = userService.findById(id);
//
//        if (currentUser==null) {
//            System.out.println("UserModel with id " + id + " not found");
//            return new ResponseEntity<UserModel>(HttpStatus.NOT_FOUND);
//        }
//
//        currentUser.setName(user.getName());
//        currentUser.setAge(user.getAge());
//        currentUser.setSalary(user.getSalary());
//
//        userService.updateUser(currentUser);
//        return new ResponseEntity<UserModel>(currentUser, HttpStatus.OK);
//    }
//
//    //------------------- Delete a UserModel --------------------------------------------------------
//
//    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
//    public ResponseEntity<UserModel> deleteUser(@PathVariable("id") long id) {
//        System.out.println("Fetching & Deleting UserModel with id " + id);
//
//        UserModel user = userService.findById(id);
//        if (user == null) {
//            System.out.println("Unable to delete. UserModel with id " + id + " not found");
//            return new ResponseEntity<UserModel>(HttpStatus.NOT_FOUND);
//        }
//
//        userService.deleteUserById(id);
//        return new ResponseEntity<UserModel>(HttpStatus.NO_CONTENT);
//    }
//
//
//    //------------------- Delete All Users --------------------------------------------------------
//
//    @RequestMapping(value = "/user/", method = RequestMethod.DELETE)
//    public ResponseEntity<UserModel> deleteAllUsers() {
//        System.out.println("Deleting All Users");
//
//        userService.deleteAllUsers();
//        return new ResponseEntity<UserModel>(HttpStatus.NO_CONTENT);
//    }
//
}