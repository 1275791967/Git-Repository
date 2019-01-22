package com.irongteng.web.management;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.irongteng.conf.DirConfig;
import com.irongteng.conf.GlobalConfig;
import com.irongteng.web.BaseController;

import dwz.common.util.InetUtils;

@Controller("management.indexController")
@RequestMapping("/management")
public class IndexController extends BaseController{
    
    private static final String MAP_HOME = new DirConfig().getHomeDir() + "/tomcat/webapps/OfflineMap"; //地图所在的根目录
    private static final File MAP_JAR = new File(MAP_HOME, "offline-map.jar");    //地图所在的jar文件
    private static JarFile jarFile;//jar文件包装类
    
    static {
        loadOfflineMap();
    }
    
    /**
     * 加载离线地图
     */
    private static void loadOfflineMap () {
        try {
            jarFile = new JarFile(MAP_JAR);
        } catch (IOException e) {
            System.out.println("not find offline map file...");
        }
    }
    
    /**
     * 登录后管理界面
     * 
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("")
    public String index(Model model, HttpServletRequest request) {
    	
    	HttpSession session = request.getSession();
    	//出事后通用session信息
    	initCommonSession(session);
    	//初始化全局变量
        //new InitGlobalParam(request).start();
        
        return "/management/index";
    }
    
    public static boolean initCommonSession(HttpSession session) {
        boolean isOnline = InetUtils.isOnline(); //百度地图是否可以连接，如果可以连接，则加载百度地图在线库
    	//如果离线，加载离线地图文件
    	if (!isOnline && MAP_JAR.exists() && jarFile==null) {
    	    loadOfflineMap();
    	}
    	session.setAttribute("isOnline", isOnline);
        return false;
    }
    
    /**
     * 打开重新登陆弹出框
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/logindialog")
    public String loginDialog(Model model, HttpServletRequest request) {
        return "/management/loginDialog";
    }
    
    /**
     * 加载地图片
     * 
     * @param model
     * @param x
     * @param y
     * @param z
     * @param response
     */
    @RequestMapping(value="/tiles/{x}/{y}/{z}/{random}", method = RequestMethod.GET)
    public void loadMapTiles(Model model, @PathVariable("x") String x, @PathVariable("y") 
                String y, @PathVariable("z") String z, HttpServletResponse response) {
        
        try {
            String tile = "tiles/" + x + "/" + y + "/" + z + ".png";
            File mapTile = new File(MAP_HOME + "/" + tile);  //解压缓存的地图片文件
            
            if (mapTile.exists()) { //如果本地存在缓存文件，则直接调用缓存文件
                response.setContentType("image/png"); // 设置返回内容格式
                try (InputStream in = new FileInputStream(mapTile);
                	        OutputStream os = response.getOutputStream()) {  //创建输出流
                    byte[] bytes = new byte[1024];
                    int size;
                    while((size = in.read(bytes))!= -1) { 
                        os.write(bytes, 0, size);     
                    }
                }
            } else { //如果本地不存在缓存地图文件，则判断地图jar包中是否存在缓存文件，存在则加载地图文件到本地缓存文件
                
                if (MAP_JAR.exists()) {   //如果文件存在
                    
                    JarEntry entry = jarFile.getJarEntry(tile);
                    
                    if (entry == null) {
                        response.setStatus(404);
                        return;
                    }
                    response.setContentType("image/png"); // 设置返回内容格式
                    try (InputStream in = jarFile.getInputStream(entry);
                    		) {  //创建输出流
                        ByteBuffer buffer = ByteBuffer.allocate(in.available());
                        
                        byte[] bytes = new byte[1024];
                        int size;
                        while ((size=in.read(bytes)) != -1) {
                        	//将数组写入输出流
                            buffer.put(bytes, 0, size);
                        }
                        //获取有效数据
                        byte[] data = buffer.array();
                        try (OutputStream os = response.getOutputStream()) {
                            os.write(data);
                        }
                        //查看地图文件流文件夹是否存在，如果不存在则创建父文件夹
                        if (!mapTile.getParentFile().exists()) {
                            mapTile.getParentFile().mkdirs();
                        }
                        try (FileOutputStream ops = new FileOutputStream(mapTile)) {  //创建输出流
                            ops.write(data);
                        }
                    }
                    response.flushBuffer();
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 初始化全局变量
     * @author OMC
     *
     */
    protected class InitGlobalParam extends Thread {
        
        private final HttpServletRequest request;
        
        public InitGlobalParam(HttpServletRequest request) {
            this.request = request;
        }
        
        @Override
        public void run() {
            GlobalConfig gConfig = new GlobalConfig();
            
            HttpSession session = request.getSession();
            //短信通知开关
//            session.setAttribute("isInformSwitch", gConfig.isInformSwitch()); 
        }
    }
    
    /**
     * 判断百度地图是否在线
     * 
     * @return
     */
    @RequestMapping(value="/isOnline", method = RequestMethod.POST)
    @ResponseBody
    public boolean isOnline() {
        return InetUtils.isOnline();
    }
    
    /**
     * 查找带回经纬度、地址等信息
     
     * @return
     */
    @RequestMapping("/load_lookup_map")
    public String loadMapView() {
        return "/management/map/MapLookup";
    }
    
}