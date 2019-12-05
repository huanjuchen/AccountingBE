package huanju.chen.app.utils;

import huanju.chen.app.model.entity.User;
import huanju.chen.app.model.vo.UserVo;

import java.util.ArrayList;
import java.util.List;

public class UserEntityUtils {


    public static List<UserVo> covertToVoList(List<User> users) {
        List<UserVo> userVos = new ArrayList<>(users.size());
        for (int i = 0; i < users.size(); i++) {
            userVos.add(i, users.get(i).covert());
        }
        return userVos;
    }

    public static List<User> covertToEntityList(List<UserVo> userVos) {
        List<User> userList = new ArrayList<>(userVos.size());
        for (int i = 0; i < userVos.size(); i++) {
            userList.add(i, userVos.get(i).covert());
        }
        return userList;
    }
}
