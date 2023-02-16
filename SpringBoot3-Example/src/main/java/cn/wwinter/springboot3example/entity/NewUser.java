package cn.wwinter.springboot3example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author zhangdongdong
 * @Date 2023/2/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUser {
    private String name;
    private String email;
}
