package org.choviwu.npcjava.plugin.mapper;
import org.apache.ibatis.annotations.Select;
import org.choviwu.npcjava.plugin.domain.Blog;
import org.choviwu.npcjava.plugin.domain.TOptions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionsMapper {
    @Select(value = "select * from t_options")
    List<TOptions> query();
}
