package org.choviwu.npcjava.plugin.mapper;

import org.apache.ibatis.annotations.*;
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

    @Select("select * from t_client where npc_uid = #{npcUid}")
    public TClient query(@Param("npcUid") String npcUid);

    @Insert("INSERT INTO  `t_client` (`target_url`, `client_vkey`, `client_basic_name`, " +
            "`client_basic_password`, `local_url`, `server_addr`, " +
            "`conn_type`, `trans_type`, `status`, `create_time`, `npc_uid`) VALUES (#{targetUrl}," +
            "#{clientVkey},#{clientBasicName},#{clientBasicPassword},#{localUrl},#{serverAddr}," +
            "#{connType},#{transType},#{status},#{createTime},#{npcUid}" +
            ")")
    int insert(TClient tClient);

    @Update("update t_client set status = #{status} where id = #{id}")
    int updateById(@Param("status") int status,@Param("id")int id);


    @Delete("delete from t_client ")
    public int delete();
}
