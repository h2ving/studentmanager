package sda.studentmanagement.studentmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sda.studentmanagement.studentmanager.domain.User;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserCache {

    //!!!!! Not sure if we need it since authentication is handled by JWT filters already
/*    @Autowired
    private UserService userService;

    private final ConcurrentHashMap<String, User> store = new ConcurrentHashMap<>(256);

    public User getByUsername(String username) {
        return store.computeIfAbsent(username, k -> userService.getUserByEmail(username));
    }

    public void evictUser(String username) {
        store.remove(username);
    }*/
}
