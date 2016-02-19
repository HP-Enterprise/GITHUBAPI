//package com.incar.query;
//
//import ch.ethz.ssh2.Connection;
//import ch.ethz.ssh2.Session;
//import ch.ethz.ssh2.StreamGobbler;
//import com.hp.briair.entity.*;
//import com.hp.briair.monitor.AlarmType;
//import com.hp.briair.repository.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.util.Assert;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.*;
//
///**
// * Created by ct on 2016/1/25 0025.
// */
//@Service
//public class AlarmService {
//
//    private static final String CPU_CMD="vmstat 1 2";
//    private static final String MEM_CMD="free";
//    private static final String DISK_CMD = "df -h";
//    private static final String PROCES_CMD = "ps  -ef|grep -v grep|grep";
//    private static int DEFAULT_ALARM_LEVEL =2;
//
//
//    @Autowired
//    private AlarmRepository alarmRepository;
//
//    @Autowired
//    private AlarmLimitRepository alarmLimitRepository;
//
//    @Autowired
//    private MonitorRepository monitorRepository;
//
//    @Autowired
//    private ServerRepository serverRepository;
//
//    @Autowired
//    private ProcessRepository processRepository;
//
//    @Autowired
//    private AlarmHisRepository alarmHisRepository;
//
//    /**
//     * 建立连接
//     * @param hostName 被监测服务器ip
//     * @param username 用户名
//     * @param password 密码
//     * @return  conn 连接
//     */
//    public Connection conn(String hostName,String username,String password){
//        Connection conn = new Connection(hostName);
//        try {
//            conn.connect();
//            boolean isAuthenticated = conn.authenticateWithPassword(username, password);
//            if(isAuthenticated==false) {
//                throw new IOException("Authorication failed");
//            }
//            return conn;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * 执行查询
//     * @param command  指令名
//     * @param session  会话
//     * @return inputStreamReader 字节输入流
//     */
//    public InputStreamReader executeCmd(String command,Session session){
//        InputStreamReader isr = null;
//        InputStream stdout ;
//        try {
//            session.execCommand(command);
//            stdout = new StreamGobbler(session.getStdout());
//            isr = new InputStreamReader(stdout);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return isr;
//    }
//
//    /**
//     * 执行cpu查询命令
//     * @param inputStreamReader
//     * @return
//     */
//    public int parseCpuResult(InputStreamReader inputStreamReader){
//        BufferedReader br = new BufferedReader(inputStreamReader);
//        int result=0;
//        try {
//            //排除前三行
//            for (int j = 0; j < 3; j++) {
//                br.readLine();
//            }
//            String line = br.readLine();
//            String[] tokens = line.split("\\s+");
//            result = Integer.valueOf(tokens[13]);
//        }catch (IOException e){
//            e.printStackTrace();
//        }finally {
//            try {
//                inputStreamReader.close();
//                br.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return result;
//    }
//
//    /**
//     * 执行内存查询命令
//     * @param inputStreamReader
//     * @return
//     */
//    public int parseMemoryResult(InputStreamReader inputStreamReader){
//        BufferedReader br = new BufferedReader(inputStreamReader);
//        Double result = null;
//        try{
//            //排除首行
//            String line = br.readLine();
//            line = br.readLine();
//            String[] tokens =null;
//            if(line!=null){
//                tokens =line.split("\\s+");
//            }
//            Double memTotalVal = Double.valueOf(tokens[1]),
//                    memUsedVal = Double.valueOf(tokens[2]);
//           result  = memUsedVal/memTotalVal*100;
//
//        }catch(IOException e){
//            e.printStackTrace();
//        }finally {
//            try {
//                inputStreamReader.close();
//                br.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return result.intValue();
//    }
//
//    /**
//     * 执行硬盘查询命令
//     * @param inputStreamReader
//     * @param server
//     * @return
//     */
//    public Set<Disk> parseDiskResult(InputStreamReader inputStreamReader,Server server){
//        Assert.notNull(server);
//        BufferedReader br = new BufferedReader(inputStreamReader);
//        Set<Disk> diskSet = new HashSet<>();
//        try{
//            //排除首行
//            String line = br.readLine();
//            while(true){
//                Disk disk = new Disk();
//                line = br.readLine();
//                if(line==null) break;
//                String[] tokens = line.split("\\s+");
//                String perc = tokens[4].replace("%", "");
//                disk.setName(tokens[5]);
//                disk.setUsedPercent(Integer.valueOf(perc));
//                disk.setServer(server);
//                diskSet.add(disk);
//            }
//            return diskSet;
//        }catch (IOException  e){
//            e.printStackTrace();
//        }finally{
//            try {
//                inputStreamReader.close();
//                br.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return diskSet;
//    }
//
//
//    /**
//     * 获取关键进程信息
//     * @param processName
//     * @param session
//     * @return
//     */
//    public Process parseProcessInfo(String processName,Session session){
//        Process process = new Process();
//        try {
//            InputStreamReader isr = executeCmd(PROCES_CMD + " " + processName,session);
//            BufferedReader br = new BufferedReader(isr);
//            String line = br.readLine();
//            if(line==null){
//                process.setStatus(false);
//            }else {
//                String[] tokens = line.split("\\s+");
//                System.out.println("tokens"+Arrays.asList(tokens).toString());
//                process.setCommand(tokens[8]);
//                process.setStatus(true);
//            }
//            process.setName(processName);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return process;
//    }
//
//    /**
//     * 保存服务器信息
//     * @param monitor
//     * @return
//     */
//    @SuppressWarnings("unchecked")
//    public Server saveServerInfo(Monitor monitor){
//        Connection connection = null;
//        Session[] sessions = new Session[3];
//        HashMap<AlarmType,Object> results = new HashMap<>();
//        Server server = new Server();
//        try {
//            connection = conn(monitor.getServer(),monitor.getUsername(),monitor.getPassword());
//            for(int i=0;i<sessions.length;i++) {
//                sessions[i] = connection.openSession();
//            }
//            Integer cpuRet  = parseCpuResult(executeCmd(CPU_CMD, sessions[0]));
//            Integer memRet = parseMemoryResult(executeCmd(MEM_CMD, sessions[1]));
//            server.setUsedCpu(cpuRet);
//            server.setUsedMemery(memRet);
//            server.setHostname(monitor.getServer());
//            server = serverRepository.save(server);
//            Set<Disk> diskRets =parseDiskResult( executeCmd(DISK_CMD, sessions[2]), server);
//            server.setDisk(diskRets);
//            server.setCreateDate(new Date());
//            server = serverRepository.save(server);
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            for(int i=0;i<sessions.length;i++)
//                sessions[i].close();
//            connection.close();
//        }
//        return server;
//    }
//
//
//    /**
//     * 保存进程信息
//     * @param monitor
//     * @return
//     */
//    public List<Process> saveProcessInfo(Monitor monitor){
//        String[] processNames = monitor.getProcess().split(",");
//        Session[] sessions = new Session[processNames.length];
//        List<Process> processList = new ArrayList<>();
//        Connection connection =null;
//        try{
//            connection = conn(monitor.getServer(), monitor.getUsername(), monitor.getPassword());
//            for(int i=0;i<processNames.length;i++){
//                sessions[i] = connection.openSession();
//
//                //保存信息
//                Process process = parseProcessInfo(processNames[i], sessions[i]);
//                process.setCreateDate(new Date());
//                process.setHostname(monitor.getServer());
//                processRepository.save(process);
//                processList.add(process);
//            }
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }finally {
//            for(int i=0;i<sessions.length;i++)
//                sessions[i].close();
//            connection.close();
//        }
//        return  processList;
//    }
//
//    /**
//     * 检查服务器信息并生成警告
//     * @param server
//     */
//    public void checkServerAndAlarm(Server server){
//        Assert.notNull(server);
//        List<AlarmLimit> alarmLimits = alarmLimitRepository.findAll();
//
//        for(AlarmLimit alarmLimit:alarmLimits){
//
//            int alarmLimitValue = alarmLimit.getLimitValue();
//            int limitType = alarmLimit.getLimitType().getValue();
//            if(limitType==AlarmLimit.LimitType.CPU.getValue() && isBeyondAlarmLimit(alarmLimitValue,server.getUsedCpu())){
//                Alarm alarm = new Alarm();
//                alarm.setAlarmTime(new Date());
//                alarm.setLevel(DEFAULT_ALARM_LEVEL);
//                alarm.setIp(server.getHostname());
//                setAlarmTitleAndContext(AlarmType.CPU, alarm, server.getUsedCpu());
//                alarmRepository.save(alarm);
//            }
//            if(limitType==AlarmLimit.LimitType.MEM.getValue() && isBeyondAlarmLimit(alarmLimitValue,server.getUsedMemery())){
//                Alarm alarm = new Alarm();
//                alarm.setAlarmTime(new Date());
//                alarm.setLevel(DEFAULT_ALARM_LEVEL);
//                alarm.setIp(server.getHostname());
//                setAlarmTitleAndContext(AlarmType.MEM,alarm,server.getUsedMemery());
//                alarmRepository.save(alarm);
//            }
//            if(limitType==AlarmLimit.LimitType.DISK.getValue()){
//                Set<Disk> diskSet = server.getDisk();
//                Iterator<Disk> iterator = diskSet.iterator();
//                while (iterator.hasNext()){
//                    Disk disk = iterator.next();
//                    if(isBeyondAlarmLimit(alarmLimitValue,disk.getUsedPercent())){
//                        Alarm alarm = new Alarm();
//                        alarm.setAlarmTime(new Date());
//                        alarm.setLevel(DEFAULT_ALARM_LEVEL);
//                        alarm.setIp(server.getHostname());
//                        setAlarmTitleAndContext(AlarmType.DISK, alarm,disk.getUsedPercent());
//                        alarm.setContext("硬盘" + disk.getName() + "占用达到" + disk.getUsedPercent()+"%");
//                        alarmRepository.save(alarm);
//                    }
//                }
//            }
//        }
//    }
//
//
//    /**
//     * 检查进程信息并生成警告
//     * @param processList
//     */
//    public void checkProcessAndAlarm(List<Process> processList){
//        for (Process process : processList){
//            if(!process.getStatus()){
//                Alarm alarm = new Alarm();
//                String alarmTitle = "进程异常警告";
//                String alarmContent = "进程"+process.getName()+"异常";
//                alarm = new Alarm(process.getHostname(),AlarmType.PROCES,alarmTitle,alarmContent,DEFAULT_ALARM_LEVEL,new Date());
//                alarmRepository.save(alarm);
//            }
//        }
//    }
//
//    /**
//     * 判断是否超出阀值
//     * @param alarmLimitValue
//     * @param usedPerc
//     * @return
//     */
//    public boolean isBeyondAlarmLimit(Integer alarmLimitValue,Integer usedPerc){
//        return alarmLimitValue <= usedPerc;
//    }
//
//    /**
//     * 设置标题和内容
//     * @param alarmType
//     * @param alarm
//     * @param usedPerc
//     */
//    public void setAlarmTitleAndContext(AlarmType alarmType,Alarm alarm,Integer usedPerc){
//        Assert.notNull(alarm);
//        switch (alarmType){
//            case CPU:
//                alarm.setAlarmType(AlarmType.CPU);
//                alarm.setTitle("cpu警告");
//                alarm.setContext("cpu占用达到" + usedPerc + "%");
//                break;
//            case MEM:
//                alarm.setAlarmType(AlarmType.MEM);
//                alarm.setTitle("内存警告");
//                alarm.setContext("内存占用达到" + usedPerc + "%");
//                break;
//            case DISK:
//                alarm.setAlarmType(AlarmType.DISK);
//                alarm.setTitle("硬盘警告");
//                break;
//            default:
//                throw new IllegalArgumentException("This alarmType is invalid!");
//        }
//    }
//
//
//    /**
//     * 监控多台机器
//     */
//    public void monitorMultiMachine(){
//        List<Monitor> monitorList = monitorRepository.findAll();
//        for(Monitor monitor:monitorList){
//            if(Monitor.MonitorType.SERVER.equals(monitor.getMonitorType())){
//                Server server = saveServerInfo(monitor);
//                checkServerAndAlarm(server);
//            }else {
//                List<Process> processList = saveProcessInfo(monitor);
//                checkProcessAndAlarm(processList);
//            }
//        }
//    }
//
//    /**
//     * 将告警表信息转移到历史告警表中
//     */
//    public void transformAlarm(){
//        List<Alarm> alarms = alarmRepository.findAll();
//        if(!alarms.isEmpty()){
//            for(Alarm alarm : alarms){
//                AlarmHis alarmHis = new AlarmHis();
//                alarmHis.setIp(alarm.getIp());
//                alarmHis.setId(alarm.getId());
//                alarmHis.setTitle(alarm.getTitle());
//                alarmHis.setContext(alarm.getContext());
//                alarmHis.setAlarmType(alarm.getAlarmType());
//                alarmHis.setLevel(alarm.getLevel());
//                alarmHis.setAlarmTime(alarm.getAlarmTime());
//                alarmHisRepository.save(alarmHis);
//                alarmRepository.delete(alarm);
//            }
//        }
//    }
//}
