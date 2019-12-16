package net.onest.lww;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UpFileServlet
 */
@WebServlet("/UpFileServlet")
public class UpFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpFileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		//接收客户端传输的文件信息
		InputStream in = request.getInputStream();
		String rootPath = getServletContext().getRealPath("/");
		String filePath = rootPath + "/testPdf.pdf";
		OutputStream out = new FileOutputStream(filePath);
		byte[] bytes = new byte[1024];
		int n = -1;
		while((n = in.read(bytes)) != -1) {
			out.write(bytes, 0, n);
			out.flush();
		}
		in.close();
		out.close();
		response.getWriter().write("文件上传成功");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
