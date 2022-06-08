package com.b635.notebook.Service;

import com.b635.notebook.Bean.User;
import com.b635.notebook.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public String selectUserName(User user){
        String userName=user.getUserName();
        String userPassword=user.getPassword();
        System.out.println(userName);
        System.out.println(userPassword);
        String result="-1";

        //将密码用md5进行加密
        String passwordMD5=passwordMD5(userName,userPassword);

        //用户不存在
        if(userMapper.selectUserName(userName)==null){
            result="0";
            return result;
            //有用户但密码输入错误
        }else if (!userMapper.selectUserPassword(userName).equals(passwordMD5)){
            result="1";
            return result;
            //登录成功
        }else if(userMapper.selectUserPassword(userName).equals(passwordMD5)){
            result="2";
            return result;
        }
        return result;
    }
    //添加用户
        public String addUser(User user){
            String userName=user.getUserName();
            //用户存在
            if(userMapper.selectUserName(userName)!=null)
                return  "0";
            String userPassword=user.getPassword();
            System.out.println(userName+"***"+userPassword);
            String passwordMD5=passwordMD5(userName,userPassword);
            userMapper.addUser(userName,passwordMD5);
            return "1";

        }
        //md5加密
    private String passwordMD5(String userName, String userPassword) {
        // 需要加密的字符串
        String src = userName + userPassword;
        try {
            // 加密对象，指定加密方式
            MessageDigest md5 = MessageDigest.getInstance("md5");
            // 准备要加密的数据
            byte[] b = src.getBytes();
            // 加密：MD5加密一种被广泛使用的密码散列函数，
            // 可以产生出一个128位（16字节）的散列值（hash value），用于确保信息传输完整一致
            byte[] digest = md5.digest(b);
            // 十六进制的字符
            char[] chars = new char[]{'0', '1', '2', '3', '4', '5',
                    '6', '7', 'A', 'B', 'C', 'd', 'o', '*', '#', '/'};
            StringBuffer sb = new StringBuffer();
            // 处理成十六进制的字符串(通常)
            // 遍历加密后的密码，将每个元素向右位移4位，然后与15进行与运算(byte变成数字)
            for (byte bb : digest) {
                sb.append(chars[(bb >> 4) & 15]);
                sb.append(chars[bb & 15]);
            }
            // 打印加密后的字符串
            System.out.println(sb);
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}






































