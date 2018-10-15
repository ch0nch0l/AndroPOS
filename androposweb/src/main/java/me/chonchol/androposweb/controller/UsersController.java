package me.chonchol.androposweb.controller;

import me.chonchol.androposweb.entity.Users;
import me.chonchol.androposweb.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsersController {


    @Autowired
    UsersRepository usersRepository;

    @GetMapping("/api/users")
    public List<Users> index(){
        return usersRepository.findAll();
    }

    @GetMapping("/api/users/{users_id}")
    public Users show(@PathVariable("users_id") Integer id){
        return usersRepository.getOne(id);
    }

    @PostMapping("/api/users")
    @ResponseBody
    public Users create(@RequestBody Users users){
        return usersRepository.save(users);
    }

    @PutMapping("/api/users/{users_id}")
    public Users update(@PathVariable("users_id") Integer id, @RequestBody Users users){
        Users newUsers = usersRepository.getOne(id);
        newUsers = users;
        return usersRepository.save(newUsers);
    }

    @DeleteMapping("/api/users/{users_id}")
    public Boolean delete(@PathVariable("users_id") Integer id){
        usersRepository.deleteById(id);
        return true;
    }
}
