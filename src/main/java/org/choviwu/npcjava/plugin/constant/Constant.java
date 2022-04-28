package org.choviwu.npcjava.plugin.constant;

import org.choviwu.npcjava.plugin.domain.dto.TransferDTO;
import org.choviwu.npcjava.plugin.service.impl.ExecuteService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public interface Constant {

   Map<String, ExecuteService> CONNECT_MAP = new ConcurrentHashMap<>();

   List<TransferDTO> TRANSFER_ONLINE_LIST = new CopyOnWriteArrayList<>();

   String NPC_UID_NAME = "npc_uid";

   String MESSAGE = "success";

   String FAIL = "fail";

   Integer SUCCESS_CODE = 1;

   Integer ERROR_CODE = 20001;
}
