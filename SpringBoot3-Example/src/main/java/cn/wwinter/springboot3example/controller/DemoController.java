package cn.wwinter.springboot3example.controller;

import cn.wwinter.springboot3example.entity.NewUser;
import cn.wwinter.springboot3example.entity.User;
import cn.wwinter.springboot3example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author zhangdongdong
 * @Date 2023/2/14
 */
@RestController
@RequestMapping("demo")
public class DemoController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("findAll")
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @PostMapping("save")
    public void save(@RequestBody NewUser newUser) {
        User user = new User(null, newUser.getName(), newUser.getEmail());
        userRepository.save(user);
    }

    @DeleteMapping("{userId}")
    public void delete(@PathVariable("userId") Integer id) {
        userRepository.deleteById(id);
    }
}
