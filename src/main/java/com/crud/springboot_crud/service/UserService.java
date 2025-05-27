package com.crud.springboot_crud.service;


import com.crud.springboot_crud.DTO.UserDTO;
import com.crud.springboot_crud.entity.User;
import com.crud.springboot_crud.exception.EmailAlreadyExistException;
import com.crud.springboot_crud.exception.InvalidPasswordException;
import com.crud.springboot_crud.exception.UserNotFoundException;
import com.crud.springboot_crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;



    @Transactional
    public UserDTO register(User user) throws Exception{
        Optional<User> user1 = userRepository.findByemail(user.getEmail());
        if(user1.isPresent()){
            throw new EmailAlreadyExistException("Email Is already Registered!");
        }else{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setCreatedAt(LocalDateTime.now());
            User saveduser = userRepository.save(user);
            return new UserDTO(saveduser);
        }
    }

    @Transactional
    public UserDTO login(User user) throws Exception{
        Optional<User> optionalUser = userRepository.findByemail(user.getEmail());
        if(optionalUser.isPresent()){
            User foundUser = optionalUser.get();
            if(passwordEncoder.matches(user.getPassword(),foundUser.getPassword())){
                foundUser.setLastLogin(LocalDateTime.now());
                userRepository.save(foundUser);
                return new UserDTO(foundUser);
            }
            else{
                throw new InvalidPasswordException("Password is Wrong!");
            }
        }else{
            throw new UserNotFoundException("No User Registered With Provided Email!");
        }
    }


    public List<UserDTO> getUsers(){
        List<UserDTO> userDTOList = userRepository.findAll()
                .stream()
                .map(UserDTO::new)
                .toList();
        return userDTOList;
    }

    @Transactional
    public String deleteUser(int id) throws UserNotFoundException{
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            String username = optionalUser.get().getName();
            userRepository.deleteById(id);
            return "User %s Deleted Successfully!".formatted(username);
        }else {
            throw new UserNotFoundException("User With Provided Id Doesn't Exist");
        }
    }

    @Transactional
    public UserDTO updateUser(int id, User user) throws Exception {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException("User With the Provided Id not Found!");
        }
        User user1 = optionalUser.get();
        if(user.getPassword()!=null && !user.getPassword().isBlank()){
            user1.setPassword(passwordEncoder.encode(user.getPassword()));
        }if(user.getName()!=null){
            user1.setName(user.getName());
        }if(user.getEmail()!=null){
            Optional<User> optional = userRepository.findByemail(user.getEmail());
            if(optional.isPresent()){
                throw new EmailAlreadyExistException("Provided Email is Already Registered!");
            }else{
                user1.setEmail(user.getEmail());
            }
        }if(user.getPhone()!=null){
            user1.setPhone(user.getPhone());
        }
        userRepository.save(user1);
        return new UserDTO(user1);
    }


}
