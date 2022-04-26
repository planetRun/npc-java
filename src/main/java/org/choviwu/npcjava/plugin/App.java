package org.choviwu.npcjava.plugin;

import org.choviwu.npcjava.plugin.conf.NpcConfig;
import org.choviwu.npcjava.plugin.domain.dto.TransferDTO;
import org.choviwu.npcjava.plugin.mapper.OptionsMapper;
import org.choviwu.npcjava.plugin.mapper.TClientMapper;
import org.choviwu.npcjava.plugin.service.impl.ClientService;
import org.choviwu.npcjava.plugin.service.impl.ExecuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
public class App  {


    @Autowired
    private OptionsMapper optionsMapper;

    @Autowired
    private TClientMapper tClientMapper;

    @Autowired
    private ClientService clientService;

    @Autowired
    private NpcConfig npcConfig;

    @Autowired
    private ExecuteService executeService;

     // ./npc.exe -server=119.91.138.119:8024 -vkey="+vkey+" -type=tcp -password="+password+" -target="+target+" -local_port="+localport

    public void init() {

        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setLocalAddress("localhost:8082");
        transferDTO.setRemoteAddress("119.91.138.119:8024");
        transferDTO.setTargetUrl("demo.test.choviwu.top");
        transferDTO.setNpcUid("test1");
        executeService.execCMD(transferDTO, null, npcConfig.getConfPath());

//        boolean fill = TemplateUtils.fill(transferDTO, npcConfig.getConfPath());
//        String result = clientService.addClient("http://nps.choviwu.top", "12111");
    }

}
