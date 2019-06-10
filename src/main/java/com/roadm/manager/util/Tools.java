package com.roadm.manager.util;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class Tools {
	
	private static Logger logger = Logger.getLogger(Tools.class);
	
	private static String strDate;
	private static Date outputDate;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");
	private static HttpClient handler = null;
	private static String mdnURL = SystemMessage.getString("mdnURL");
	private static String feeURL = SystemMessage.getString("feeURL");
	private static String feeMmsURL = SystemMessage.getString("feeMmsURL");
	public static String smtpServer = SystemMessage.getString("smtpServer");
	public static String username = SystemMessage.getString("username");
	public static String password = SystemMessage.getString("password");
	public static String meMail = SystemMessage.getString("meMail");
	public static String paConfigInitURL = SystemMessage.getString("paConfigInitURL");
	public static String paUnicomURL = SystemMessage.getString("paUnicomURL");
	public static String paMobileURL = SystemMessage.getString("paMobileURL");
	public static String paTelecomURL = SystemMessage.getString("paTelecomURL");
	public static String UnicomMobileTelecom_pmId = SystemMessage.getString("UnicomMobileTelecom_pmId");
	public static String Http_pmId = SystemMessage.getString("Http_pmId");
	
	public static String paUnicomConfigURL = SystemMessage.getString("paUnicomConfigURL");
	public static String paMobileConfigURL = SystemMessage.getString("paMobileConfigURL");
	public static String paTelecomConfigURL = SystemMessage.getString("paTelecomConfigURL");
	public static String pmConfigAddURL = SystemMessage.getString("pmConfigAddURL");
	public static String pmConfigUpdateOrRmURL = SystemMessage.getString("pmConfigUpdateOrRmURL");
	
	private static Calendar cal = Calendar.getInstance();
//	private static MongoManager m = MongoManager.getInstance();

	private static Client client = Client.create();
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };
	public static Integer maxDirectDownNumber = SystemMessage.getInteger("maxDirectDownNumber");
	public static String excelFilePath = SystemMessage.getString("excelFilePath");


	static {
		MultiThreadedHttpConnectionManager manager = new MultiThreadedHttpConnectionManager();
		HttpConnectionManagerParams params = manager.getParams();
		params.setDefaultMaxConnectionsPerHost(100);
		params.setMaxTotalConnections(50);
		params.setConnectionTimeout(5000);
		params.setSoTimeout(10000);
		handler = new HttpClient(manager);
	}

	

	/**
	 * 检测字符串是否不为空(null,"","null")
	 * 
	 * @param s
	 * @return 不为空则返回true，否则返回false
	 */
	public static boolean notEmpty(String s) {
		return s != null && !"".equals(s) && !"null".equals(s);
	}

	/**
	 * 检测字符串是否为空(null,"","null")
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		return s == null || "".equals(s) || "null".equals(s);
	}

	/**
	 * 字符串转换为字符串数组
	 * 
	 * @param str
	 *            字符串
	 * @param splitRegex
	 *            分隔符
	 * @return
	 */
	public static String[] str2StrArray(String str, String splitRegex) {
		if (isEmpty(str)) {
			return null;
		}
		return str.split(splitRegex);
	}

	/**
	 * 用默认的分隔符(,)将字符串转换为字符串数组
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static String[] str2StrArray(String str) {
		return str2StrArray(str, ",\\s*");
	}

	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，日期转字符串
	 * 
	 * @param date
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String date2Str(Date date) {
		return date2Str(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，字符串转日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date str2Date(String date) {
		if (notEmpty(date)) {
			try {
				return sdf.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return new Date();
		} else {
			return null;
		}
	}

	/**
	 *
	 * 获取当月一号的日志
	 * @return
	 */
	public static String monthBeginDay(String format){
		Date date=new Date();
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
        calendar.add(Calendar.MONTH,0);
		calendar.set(Calendar.DAY_OF_MONTH,1);
		Date oneDate=calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(oneDate);
	}

	/**
	 * 按照参数format的格式，日期转字符串
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2Str(Date date, String format) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		} else {
			return "";
		}
	}

	public static String Nextdate2Str(String format) {
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 1);
		if (format != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(cal.getTime());
		} else {
			return "";
		}
	}

	/**
	 * 对日期格式进行处理，传入Date类型的对象，输出yyyy-MM-dd HH:mm:ss的Date类型的时间
	 */
	public static Date dateFormat(Date inputDate) {
		try {
			cal.setTime(inputDate);
			strDate = sdf.format(cal.getTime());
			outputDate = sdf.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return outputDate;
	}

	/**
	 * 对日期格式进行处理，传入Date类型的对象，输出yyyy-MM-dd的Date类型的时间
	 */
	public static Date dateFormat2(Date inputDate) {
		try {
			cal.setTime(inputDate);
			strDate = sdf1.format(cal.getTime());
			outputDate = sdf1.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return outputDate;
	}

	/**
	 * 对日期格式进行处理，传入Date类型的对象，输出yyyy-MM的Date类型的时间
	 */
	public static Date dateFormatMonth(Date inputDate) {
		try {
			cal.setTime(inputDate);
			strDate = sdf2.format(cal.getTime());
			outputDate = sdf2.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return outputDate;
	}

	/**
	 * 获取号码归属地
	 * 
	 * @param mobile
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static JSONObject getMdnRegion(String mobile) throws Exception {
		PostMethod post = new PostMethod(mdnURL);
		handler.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		post.addParameter("mobile", mobile);
		handler.executeMethod(post);
		StringBuffer sb = new StringBuffer();
		InputStream in = post.getResponseBodyAsStream();
		BufferedReader buff = new BufferedReader(new InputStreamReader(in));
		String line = "";
		while ((line = buff.readLine()) != null) {
			sb.append(line);
		}
		post.releaseConnection();
		return JSONObject.fromObject(sb.toString());

	}
	
    /**
     * 初始化企业余额
     * @param spid
     * @param leftover
     * @throws Exception
     */
	public static void InitSpFee(String spid,String leftover)throws Exception {
		PostMethod post = new PostMethod(feeURL);
		handler.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		post.addParameter("spId", spid);
		post.addParameter("num", leftover);
		post.addParameter("type", "post");
		handler.executeMethod(post);
		StringBuffer sb = new StringBuffer();
		InputStream in = post.getResponseBodyAsStream();
		BufferedReader buff = new BufferedReader(new InputStreamReader(in));
		String line = "";
		while ((line = buff.readLine()) != null) {
			sb.append(line);
		}
		logger.info("spId:"+spid+",num"+leftover+",response:"+sb.toString());
		post.releaseConnection();
	}
	/**
	 *  审核失败，余额返还
	 * @param spid
	 * @param userid
	 * @param allnumber
	 * @param dateStr
	 * @throws Exception
	 */
	public static String sendFailedSpFee(String spid,String userid,String allnumber,String dateStr) {
		PostMethod post = new PostMethod(feeURL);
		handler.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		post.addParameter("spid", spid);
		post.addParameter("userid", userid);
		post.addParameter("allnumber", allnumber);
		post.addParameter("dateStr", dateStr);
		post.addParameter("opertor", "auditReject");
//		auditReject
		StringBuffer sb = new StringBuffer();
		try {
			handler.executeMethod(post);
			InputStream in = post.getResponseBodyAsStream();
			BufferedReader buff = new BufferedReader(new InputStreamReader(in));
			String line = "";
			while ((line = buff.readLine()) != null) {
				sb.append(line);
			}
			post.releaseConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	/**
	 *  审核失败，余额返还
	 * @param spid
	 * @param userid
	 * @param allnumber
	 * @param dateStr
	 * @throws Exception
	 */
	public static String sendFailedSpFeeMms(String spid,String userid,String allnumber,String dateStr) {
		PostMethod post = new PostMethod(feeMmsURL);
		handler.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		post.addParameter("spid", spid);
		post.addParameter("userid", userid);
		post.addParameter("allnumber", allnumber);
		post.addParameter("dateStr", dateStr);
		post.addParameter("opertor", "auditReject");
//		auditReject
		StringBuffer sb = new StringBuffer();
		try {
			handler.executeMethod(post);
			InputStream in = post.getResponseBodyAsStream();
			BufferedReader buff = new BufferedReader(new InputStreamReader(in));
			String line = "";
			while ((line = buff.readLine()) != null) {
				sb.append(line);
			}
			post.releaseConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	/**
     * 初始化企业余额
     *      或者给企业充值
     * @param spid
     * @param leftover
     * @throws Exception
     */
    public static Map<String,Integer> InitOrRechargeSpFee(String spid, String leftover) throws Exception {
        int result = 1;
        Map<String,Integer> map = new HashMap<String,Integer>();
        map.put("result", result);
        PostMethod post = new PostMethod(feeURL);
        handler.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        post.addParameter("spid", spid);
        post.addParameter("allnumber", leftover);
        post.addParameter("opertor", "recharge");
        try{
        	handler.executeMethod(post);
        	 String resultStr = post.getResponseBodyAsString();
        	 logger.info("response:[" + resultStr + "]");
             JSONObject json = JSONObject.fromObject(resultStr);
             String returnCode = json.getString("returnCode");
             if ("0".equals(returnCode)) {
                 result = 0;
                 map.put("result", result);
                 String remain = json.getString("leftover");
                 int leftoverNum=0; 
                 if(remain!=null&&!"".equals(remain)){
                 	try{
                 		leftoverNum=Integer.valueOf(remain);
                 	}catch(NumberFormatException e){
                 		e.printStackTrace();
                 	}
                 }
                 map.put("remain", leftoverNum);
                 logger.info("spId:" + spid + ",init or recharge:" + leftover 
                         + ",now remain:" + remain + ",response:[" + resultStr + "]");
             } else {
                 logger.info("init or recharge error:" + json.getString("description"));
             }
        }catch(IOException e){
        	e.printStackTrace();
        }finally{
        	post.releaseConnection();
        }
        return map;
    }

	/**
	 * 初始化企业余额
	 *      或者给企业充值
	 * @param spid
	 * @param leftover
	 * @throws Exception
	 */
	public static int InitOrRechargeSpFeeMms(String spid, String leftover) throws Exception {
		int result = 1;

		PostMethod post = new PostMethod(feeMmsURL);
		handler.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		post.addParameter("spid", spid);
		post.addParameter("allnumber", leftover);
		post.addParameter("opertor", "recharge");
		handler.executeMethod(post);
		String resultStr = post.getResponseBodyAsString();

		logger.info("response:[" + resultStr + "]");
		JSONObject json = JSONObject.fromObject(resultStr);
		String returnCode = json.getString("returnCode");
		if ("0".equals(returnCode)) {
			result = 0;

			String remain = json.getString("leftover");
			logger.info("spId:" + spid + ",init or rechargeMms:" + leftover
					+ ",now remain:" + remain + ",response:[" + resultStr + "]");
		} else {
			logger.info("init or rechargeMms error:" + json.getString("description"));
		}

		post.releaseConnection();

		return result;
	}


    /**
     * 企业短信扣减
     * 
     * @param spid
     * @param leftover
     * @throws Exception
     */
    public static Map<String,Integer> deductionSpFee(String spid, String leftover) throws Exception {
        int result = 1;
        Map<String,Integer> map = new HashMap<String,Integer>();
        map.put("result", result);
        PostMethod post = new PostMethod(feeURL);
        handler.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        post.addParameter("spid", spid);
        post.addParameter("allnumber", leftover);
        post.addParameter("opertor", "deduction");
        try{
	        handler.executeMethod(post);
	        String resultStr = post.getResponseBodyAsString();
	        
	        logger.info("response:[" + resultStr + "]");
	        JSONObject json = JSONObject.fromObject(resultStr);
	        String returnCode = json.getString("returnCode");
	        if ("0".equals(returnCode)) {
	            result = 0;
	            map.put("result", result);
	            String remain = json.getString("leftover");
	            int leftoverNum=0; 
                if(remain!=null&&!"".equals(remain)){
                	try{
                		leftoverNum=Integer.valueOf(remain);
                	}catch(NumberFormatException e){
                		e.printStackTrace();
                	}
                }
                map.put("remain", leftoverNum);
	           logger.info("spId:" + spid + ",deduction:" + leftover 
	                    + ",now remain:" + remain);
	        } else {
	            logger.info("deduction error:" + json.getString("description"));
	        }
        }catch(IOException e){
        	e.printStackTrace();
        }finally{
        	post.releaseConnection();
        }
        
        return map;
    }

	/**
	 * 企业彩信扣减
	 *
	 * @param spid
	 * @param leftover
	 * @throws Exception
	 */
	public static int deductionSpFeeMms(String spid, String leftover) throws Exception {
		int result = 1;

		PostMethod post = new PostMethod(feeMmsURL);
		handler.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		post.addParameter("spid", spid);
		post.addParameter("allnumber", leftover);
		post.addParameter("opertor", "deduction");
		handler.executeMethod(post);
		String resultStr = post.getResponseBodyAsString();

		logger.info("response:[" + resultStr + "]");
		JSONObject json = JSONObject.fromObject(resultStr);
		String returnCode = json.getString("returnCode");
		if ("0".equals(returnCode)) {
			result = 0;

			String remain = json.getString("leftover");
			logger.info("spId:" + spid + ",deduction:" + leftover
					+ ",now remain:" + remain);
		} else {
			logger.info("deduction error:" + json.getString("description"));
		}

		post.releaseConnection();

		return result;
	}
	
	   /**
     * 企业短信扣减
     * 
     * @param spid
     * @param leftover
     * @throws Exception
     */
    public static Map<String,Integer> returnAmount(String spid, String leftover) throws Exception {
        int result = 1;
        Map<String,Integer> map = new HashMap<String,Integer>();
        map.put("result", result);
        PostMethod post = new PostMethod(feeURL);
        handler.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        post.addParameter("spid", spid);
        post.addParameter("allnumber", leftover);
        post.addParameter("opertor", "returnAmount");
        try{
	        handler.executeMethod(post);
	        String resultStr = post.getResponseBodyAsString();
	        logger.info("response:[" + resultStr + "]");
        }catch(IOException e){
        	e.printStackTrace();
        }finally{
        	post.releaseConnection();
        }
        
        return map;
    }
	
	
	
	/**
	 * 获取当月最后一天'yyyy-MM' 空或不支持格式返回当前月最后一天
	 * 
	 * @param date
	 * @return 'yyyy-MM-dd'
	 */
	public static String getMonthLastDay(String date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		Date theDate;
		try {
			theDate = df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			theDate = new Date();
		}
		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		gcLast.setTime(theDate);
		gcLast.set(Calendar.DAY_OF_MONTH, 1);
		gcLast.roll(Calendar.DAY_OF_MONTH, -1);
		return df1.format(gcLast.getTime());
	}



	/**
	 * 信息审核
	 */
	public static int reserveSend(String taskid, String spid, String audit_type, String allnumber, String total, String sp_audit_number) {
		int lockReserveResult = 0;
		try {
			String audit_url = SystemMessage.getString("audit_url");
			logger.debug("auditTask url ----------> " + audit_url);
			WebResource r = client.resource(audit_url);
			JSONObject conditions = new JSONObject();
			conditions.put("taskid", taskid);
			conditions.put("spid", spid);
			conditions.put("audit_type", audit_type);
			conditions.put("allnumber", allnumber);
			conditions.put("total", total);
			conditions.put("sp_audit_number", sp_audit_number);
			String resp = r.post(String.class, conditions.toString());
			JSONObject res = JSONObject.fromObject(resp);
			if (!res.isNullObject()) {
				String ja = res.getString("returnCode");
				lockReserveResult = Integer.parseInt(ja);
			}
			logger.debug(res);
			if (audit_type.equals("2")) {
				auditFailInsertMongo(spid, taskid);
				
			}

		} catch (Exception e) {
			e.printStackTrace();
			return lockReserveResult;
		}
		return lockReserveResult;
	}

	/**
	 * 审核失败插入Mongo
	 * 
	 * @param spid
	 * @param taskid
	 */
	public static void auditFailInsertMongo(String spid, String taskid) {

		try {

			Document obj = new Document();
			obj.put("spid", spid);
			obj.put("taskid", taskid);
			obj.put("status", 0);
			logger.info("==============================================================");
			logger.info("AuditFailTask ==========> SPID " + spid + " TASKID " + taskid);
			logger.info("==============================================================");
//			m.insert("audit_failtask", obj);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 信息回复
	 */
	public static void doReply(String callmdn, String mobile, String replytime,	String prefix, String content) {
		try {
			String reply_url = SystemMessage.getString("reply_url");
			logger.info("reply_url  ----------> " + reply_url);
			WebResource r = client.resource(reply_url);
			JSONObject conditions = new JSONObject();
			conditions.put("callmdn", callmdn);
			conditions.put("mobile", mobile);
			conditions.put("replytime", replytime);
			conditions.put("prefix", prefix);
			conditions.put("content", content);
			String resp = r.post(String.class, conditions.toString());
			JSONObject res = JSONObject.fromObject(resp);
			logger.debug(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 对字符串进行MD5编码 */
	public static String encodeByMD5(String originString) {
		if (originString != null) {
			try {
				// 创建具有指定算法名称的信息摘要
				MessageDigest md5 = MessageDigest.getInstance("MD5");
				// 使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
				byte[] results = md5.digest(originString.getBytes());
				// 将得到的字节数组变成字符串返回
				String result = byteArrayToHexString(results);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static Boolean valiadMdn(String mdn) {
		return Pattern.matches("1([\\d]{10})", mdn);
	}

	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0){
            n = 256 + n;
        }

		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	// 发送邮件
	public static void MailToSend(String toMail, String mailTitle, String mailText, String fileName) {
		if (!isEmpty(toMail)) {
			BaseMail bs = new BaseMail(smtpServer, username, password, meMail, toMail, mailTitle, mailText, fileName);
		}
	}

	//获取ping结果
	public static String exeCmd(String ip[]) {
		BufferedReader br = null;
		try {
			Process p = Runtime.getRuntime().exec(ip);
			br = new BufferedReader(new InputStreamReader(p.getInputStream(),"gbk"));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			if (br != null)
			{
				try {
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return "";
	}


	public static String PMConfig(String pa_id,String pa_url,int type) throws Exception {
		logger.info("配置pm,url="+pa_url+",pa_id="+pa_id+",type="+type+",begin");
		PostMethod post = new PostMethod(pmConfigUpdateOrRmURL);
		if(type==1){//1,更新		2.删除
			post.addParameter("type","update");
			post.addParameter("pa_id",pa_id);
			post.addParameter("url",pa_url);
		}else if(type ==2){
			post.addParameter("type","rm");
			post.addParameter("pa_id",pa_id);
		}
		
		handler.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		handler.executeMethod(post);
		StringBuffer sb = new StringBuffer();
		InputStream in = post.getResponseBodyAsStream();
		BufferedReader buff = new BufferedReader(new InputStreamReader(in));
		String line = "";
		while ((line = buff.readLine()) != null) {
			sb.append(line);
		}
		post.releaseConnection();
		logger.info("配置pm,url="+pa_url+",pa_id="+pa_id+",type="+type+",success,respose:"+sb);
		return "";
	}
	
	
	public static <T> List<List<T>> splitList(List<T> list, int pageSize) {

		int listSize = list.size(); // list的大小
		int page = (listSize + (pageSize - 1)) / pageSize; // 页数

		List<List<T>> listArray = new ArrayList<List<T>>(); // 创建list数组
															// ,用来保存分割后的list
		for (int i = 0; i < page; i++) { // 按照数组大小遍历
			List<T> subList = new ArrayList<T>(); // 数组每一位放入一个分割后的list
			for (int j = 0; j < listSize; j++) { // 遍历待分割的list
				int pageIndex = ((j + 1) + (pageSize - 1)) / pageSize; // 当前记录的页码(第几页)
				if (pageIndex == (i + 1)) { // 当前记录的页码等于要放入的页码时
					subList.add(list.get(j)); // 放入list中的元素到分割后的list(subList)
				}
				if ((j + 1) == ((j + 1) * pageSize)) { // 当放满一页时退出当前循环
					break;
				}
			}
			listArray.add(subList); // 将分割后的list放入对应的数组的位中
		}
		return listArray;
	}
	
	/**
	 * 获取上个月
	 * @param format
	 * @return
	 */
	public static String Lastdate2Str(String format) {
		long time = System.currentTimeMillis();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -1);
		if (format != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(cal.getTime());
		} else {
			return "";
		}
	}

}
