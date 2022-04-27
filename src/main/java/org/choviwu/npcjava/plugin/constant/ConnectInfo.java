package org.choviwu.npcjava.plugin.constant;

import org.choviwu.npcjava.plugin.service.impl.ExecuteService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectInfo {

    public static final Map<String, ExecuteService> CONNECT_MAP = new ConcurrentHashMap<>();
}
