package CapstoneDesign.Backendserver.service;

import CapstoneDesign.Backendserver.domain.User;
import CapstoneDesign.Backendserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {

    private final UserRepository userRepository;


    @Transactional(readOnly = true)
    public boolean validateDuplicateMember(String id) {
        log.info("중복체크함수실행 id={}",id);
        return userRepository.findById(id).isPresent();
        }

    public Object findUser(String id){
        if(userRepository.findById(id).isPresent()){
        return userRepository.findById(id).get();
    } else return "ID_NOT_FOUND";
    }

    public void join(User user) {
        userRepository.save(user);
    }

 }

