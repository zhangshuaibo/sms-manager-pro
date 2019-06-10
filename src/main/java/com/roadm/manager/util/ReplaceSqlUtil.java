package com.roadm.manager.util;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ReplaceSqlUtil extends Thread{
	
	 private static Logger log = Logger.getLogger(ReplaceSqlUtil.class);
	    private static List<String> sqlList = new ArrayList<String>();
	    private static String file_path = SystemMessage.getString("sync_file_path") + File.separator + "replace_sql" + File.separator;
	    private static String user_file = file_path + "sql";
	    private static String user_file_done = file_path + "sql.done";
	    private static long lastmodify = 0l;
	    private static ReplaceSqlUtil instance = new ReplaceSqlUtil();
	    private ReplaceSqlUtil(){
	    	this.start();
	    }

	    public static ReplaceSqlUtil getInstance() {
	        return instance;
	    }

	    public void process() {
	        try {
	            File done_file = new File(user_file_done);
	            if (done_file.exists()) {
	                long done_lastmodify = done_file.lastModified();
	                if (done_lastmodify > lastmodify) {
	                    log.debug("file_done new modify time " + new Date(done_lastmodify) + ",lastmodify " + new Date(lastmodify));
	                    sqlList = readFile();
	                    lastmodify = done_lastmodify;
	                }
	            } else {
	            	sqlList = readFile();
	                log.debug("file_done no exists");
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    @Override
	    public void run() {
	        while (true) {
	        	 process();
	            try {
	                Thread.sleep(5*60 * 1000);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	           
	        }
	    }

	    private  List<String> readFile() {
	        LineNumberReader read = null;
	        List<String> list = new ArrayList<String>();
	        try {
	            log.debug("read file:" + user_file);
	            File file = new File(user_file);
	            if(file.exists()){
	            	read = new LineNumberReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
		            String line = null;
		            while ((line = read.readLine()) != null) {
		            	if(line!=null&&!"".equals(line)){
		            		list.add(line);
		            	}
		            }
	            }
	            
	            log.debug("size:" + list.size());
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	            	if(read!=null){
	            		read.close();
	            	}
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        return list;
	    }


		public String replaceSql(String sql){
			String new_sql=sql;
			try{
				for(int i=0;i<sqlList.size();i++){
					String replace_sql = sqlList.get(i);
					if(sql!=null&&!"".equals(sql)){
						if(sql.contains(replace_sql)){
							new_sql=sql.replaceAll(replace_sql, "");
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return new_sql;
		}
	  
}
