package org.choviwu.npcjava.plugin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.choviwu.npcjava.plugin.domain.TClient;

import java.util.List;

/**
* @author wucw
* @description 针对表【t_client】的数据库操作Mapper
* @createDate 2022-04-26 16:19:08
* @Entity org.choviwu.npcjava.plugin.domain.TClient
*/
@Mapper
public interface TClientMapper {

    @Select("select * from t_client")
    public List<TClient> query();

}
