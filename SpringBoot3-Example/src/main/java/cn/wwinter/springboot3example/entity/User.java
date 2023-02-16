package cn.wwinter.springboot3example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author zhangdongdong
 * @Date 2023/2/14
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "_user")
public class User {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String email;
}
