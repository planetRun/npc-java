package org.choviwu.npcjava.plugin.mapper;
import org.apache.ibatis.annotations.Select;
import org.choviwu.npcjava.plugin.domain.Blog;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogMapper {
    @Select(value = "select * from blog")
    List<Blog> query();
}
