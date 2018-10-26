package me.chonchol.andropospanel.controller;

import me.chonchol.andropospanel.entity.Client;
import me.chonchol.andropospanel.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClientController {

    @Autowired
    ClientRepository clientRepository;

    @GetMapping("/api/client")
    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }

    @GetMapping("/api/client/{client_id}")
    public Client show(@PathVariable("client_id") Integer id){
        return clientRepository.getOne(id);
    }


    @GetMapping("/api/client/byname/{client_db}")
    public Client getClientByDBName(@PathVariable("client_db") String clientDBName){
        return clientRepository.getClientByDBName(clientDBName);
    }

    @PostMapping("/api/client")
    @ResponseBody
    public Client insertNewClient(@RequestBody Client client){
        return clientRepository.save(client);
    }

    @PutMapping("/api/client/{client_id}")
    public Client updateClient(@PathVariable("client_id") Integer id, @RequestBody Client client){
        Client newClient = clientRepository.getOne(id);
        newClient = client;
        return clientRepository.save(newClient);
    }

    @DeleteMapping("/api/client/{client_id}")
    public Boolean delete(@PathVariable("client_id") Integer id){
        clientRepository.deleteById(id);
        return true;
    }
}
