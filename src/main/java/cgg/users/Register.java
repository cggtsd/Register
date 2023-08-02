package cgg.users;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

/**
 * Servlet implementation class Register
 */
@MultipartConfig
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		//getting all the incoming details from the request....
		
		String name = request.getParameter("user_name");
		String password = request.getParameter("user_password");
		String email = request.getParameter("user_email");
		Part part = request.getPart("image");
		String filename = part.getSubmittedFileName();
		
		//out.println(filename);
		/*
		 * out.println(name); out.println(password); out.println(email);
		 */
		
		//connection....
		try {
			Thread.sleep(3000);
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/register","postgres","root");
		
		    //query....
			String q = "insert into users (name,password,email,imageName) values(?,?,?,?)";
			PreparedStatement pstmt = connection.prepareStatement(q);
		
			pstmt.setString(1, name);
			pstmt.setString(2, password);
			pstmt.setString(3, email);
			pstmt.setString(4, filename);
			
			pstmt.executeUpdate();
			
			//upload....
			InputStream is = part.getInputStream();
			byte[] data = new byte[is.available()];
			is.read(data);
			String path=getServletContext().getRealPath("/")+"images"+File.separator+filename;
			//out.println(path);
			FileOutputStream fos = new FileOutputStream(path);
			fos.write(data);
			fos.close();
			
			
			
			
			out.println("done");
		
		}
		catch(Exception e) {
			e.printStackTrace();
			out.println("error");
		}
		
		
		
		
		
		
		
		
		
		
	}

}
