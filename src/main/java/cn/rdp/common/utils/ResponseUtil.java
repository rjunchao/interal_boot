package cn.rdp.common.utils;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import cn.rdp.common.domain.ExportFile;


public class ResponseUtil {

    public static void write(HttpServletResponse response, Object o) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        out.println(o.toString());
        out.flush();
        out.close();
    }
    
    public static void download(HttpServletResponse response, ExportFile ef) throws Exception {
    	response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.addHeader("Content-Disposition", "attachment;filename=" + 
		ef.getFileName() + ef.getSufix());
		try {
			ef.getWb().write(response.getOutputStream());
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
