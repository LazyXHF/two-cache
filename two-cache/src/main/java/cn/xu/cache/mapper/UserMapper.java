package cn.xu.cache.mapper;

import cn.xu.cache.entity.User;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

/**
 * @author ~许小桀
 * @date 2019/10/17 15:38
 */
@Repository
public interface UserMapper {

    @Select("SELECT * FROM test t WHERE t.`id` = #{id}")
    User getUserById(String id);
}
