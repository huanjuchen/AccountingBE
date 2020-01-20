package huanju.chen.app.dao;

import java.util.List;
import java.util.Map;

/**
 * @author HuanJu
 */
public interface BaseMapper<T> {

    /**
     * 存储实体
     *
     * @param obj 实体
     * @return 受影响的行数
     */
    int save(T obj);

    /**
     * 更新实体
     *
     * @param obj 实体
     * @return 受影响的行数
     */
    int update(T obj);

    /**
     * 删除实体
     *
     * @param key key
     * @return 受影响的行数
     */
    int delete(Object key);

    /**
     * 根据key查找实体
     *
     * @param key key
     * @return 实体
     */
    T find(Object key);

    /**
     * 根据name查找实体
     *
     * @param name name
     * @return 实体
     */
    T findByName(String name);


    /**
     * 查询多个
     *
     * @param map 条件
     * @return 实体list
     */
    List<T> list(Map<String,Object> map);

}
