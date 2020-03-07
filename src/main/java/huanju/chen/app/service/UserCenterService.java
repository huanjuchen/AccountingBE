package huanju.chen.app.service;

import huanju.chen.app.domain.dto.User;

/**
 * @author HuanJu
 */
public interface UserCenterService {

    User getUser(String tokenId);

    void changeName(String newName, String tokenId);

    void changePhone(String newPhone, String tokenId);

    void changePassword(String newPwd, String tokenId);

}
