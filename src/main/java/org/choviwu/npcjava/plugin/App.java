package org.choviwu.npcjava.plugin;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.choviwu.npcjava.plugin.conf.NpcConfig;
import org.choviwu.npcjava.plugin.constant.Constant;
import org.choviwu.npcjava.plugin.domain.TClient;
import org.choviwu.npcjava.plugin.exception.NpcException;
import org.choviwu.npcjava.plugin.mapper.TClientMapper;
import org.choviwu.npcjava.plugin.service.impl.ExecuteService;
import org.choviwu.npcjava.plugin.utils.PathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 0. 设置域名服务
 * 1. 是否需要向服务端发送vkey等信息
 * 2. 发送vkey http
 * 3. 设置客户端数据，包括目标8024端口， 泛解析域名，本地服务端口
 * 4. 将客户端配置文件保存下来
 * 5. 调用npc.exe并发送请求到服务端
 * 6. 长链接
 * 7. 关闭连接
 */
@Component
@Slf4j
public class App {

    @Autowired
    private TClientMapper tClientMapper;

    @Autowired
    private NpcConfig npcConfig;

    @Bean
    public String operateBean() {
        String property = System.getProperty("os.name");
        if (property.toLowerCase().contains("wind")) {
            InputStream exeStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("win/npc.exe");
            try {
                byte[] bytes = IOUtils.toByteArray(exeStream);

                IOUtils.write(bytes, new FileOutputStream(PathUtils.getExePath("npc.exe")));
            } catch (Exception e) {
                throw new RuntimeException("读取文件失败", e);
            }
            return PathUtils.getExePath("npc.exe");
        } else if (property.toLowerCase().contains("mac")) {
            InputStream exeStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("mac/npc");
            try {
                byte[] bytes = IOUtils.toByteArray(exeStream);

                IOUtils.write(bytes, new FileOutputStream(PathUtils.getExePath("npc")));
            } catch (Exception e) {
                throw new RuntimeException("读取文件失败", e);
            }
            return PathUtils.getExePath("npc");
        } else {
            InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("linux/npc");
            try {
                byte[] bytes = IOUtils.toByteArray(resourceAsStream);

                IOUtils.write(bytes, new FileOutputStream(PathUtils.getExePath("npc")));
            } catch (Exception e) {
                throw new RuntimeException("读取文件失败", e);
            }
        }
        return PathUtils.getExePath("npc");
    }

    public void init() {

        deleteConf();
        // readNpcConfig
        readList();

//         tClientMapper.delete();

        Thread hook = new Thread(() -> {
            //
            log.info("清理内存");

            Constant.CONNECT_MAP.forEach((k,v) -> {
                try {
                    log.info("------> {}", k);
                    v.stop(k);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
        Runtime.getRuntime().addShutdownHook(hook);

    }

    public List<TClient> readList() {
        List<TClient> tClients = tClientMapper.queryAll();
        return tClients;
    }

    private void deleteConf() {
        String confPath = npcConfig.getConfPath();
        try {
            Files.deleteIfExists(Paths.get(confPath));
        } catch (Exception e) {
            throw new NpcException(30001, "文件删除失败");
        }
    }

    // ./npc.exe -server=119.91.138.119:8024 -vkey="+vkey+" -type=tcp -password="+password+" -target="+target+" -local_port="+localport


}
