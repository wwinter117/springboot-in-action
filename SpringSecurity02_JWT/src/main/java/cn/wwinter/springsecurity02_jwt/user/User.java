package cn.wwinter.springsecurity02_jwt.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: zhangdongdong
 * @CreateTime: 2023-02-08
 */
@Data
@Entity
@Table(name = "_user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String email;

    private String password;
}
