package me.chonchol.androposweb.controller;

import me.chonchol.androposweb.entity.User;
import me.chonchol.androposweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {


    @Autowired
    UserRepository userRepository;

    @GetMapping("/api/user")
    public List<User> index(){
        return userRepository.findAll();
    }

    @GetMapping("/api/user/{user_id}")
    public User show(@PathVariable("user_id") Integer id){
        return userRepository.getOne(id);
    }


    @GetMapping("/api/userbyname/{user_name}")
    public User getUserByUserName(@PathVariable("user_name") String name){
        return userRepository.getUserByUserName(name);
    }

    @PostMapping("/api/user")
    @ResponseBody
    public User create(@RequestBody User user){
        return userRepository.save(user);
    }

    @PutMapping("/api/user/{user_id}")
    public User update(@PathVariable("user_id") Integer id, @RequestBody User user){
        User newUser = userRepository.getOne(id);
        newUser = user;
        return userRepository.save(newUser);
    }

    @DeleteMapping("/api/user/{user_id}")
    public Boolean delete(@PathVariable("user_id") Integer id){
        userRepository.deleteById(id);
        return true;
    }
}
