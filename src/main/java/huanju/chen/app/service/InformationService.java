package huanju.chen.app.service;

import huanju.chen.app.domain.dto.Information;

import java.util.List;
import java.util.Map;

/**
 * @author HuanJu
 * @date 2020/3/6 17:26
 */
public interface InformationService {

    void add(Information information,String tokenId);

    Information find(Integer id);

    List<Information> list(Map<String, Object> map);

    Integer count(Map<String,Object> map);

    void delete(Integer id,String tokenId);


}
