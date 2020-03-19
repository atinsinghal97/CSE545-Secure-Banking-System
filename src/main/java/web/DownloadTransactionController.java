package web;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import database.SessionManager;
import model.Account;
import model.Request;
import model.User;
import model.UserDetail;
import model.Appointment;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;




import java.io.File;
import java.nio.file.Files;





@Controller
public class DownloadTransactionController {

	@RequestMapping("/Download")
    public String home(final HttpServletRequest request, Model model) throws FileNotFoundException, DocumentException {
		ArrayList<User> u=new ArrayList<>();
		
		Session s = SessionManager.getSession("");
		u=(ArrayList<User>) s.createQuery("FROM User", User.class).list();
		System.out.println(u);
		String up="root";
		String op="root";
		   /* Step-2: Initialize PDF documents - logical objects */
        Document my_pdf_report = new Document();
        String filePath = new File("").getAbsolutePath();
       
        PdfWriter.getInstance(my_pdf_report, new FileOutputStream(filePath+"/Usernames.pdf"));
     
        my_pdf_report.open(); 
        
        my_pdf_report.addAuthor("G13");
        my_pdf_report.addCreationDate();
        my_pdf_report.addCreator("G13");
       
        //we have four columns in our table
        PdfPTable my_report_table = new PdfPTable(1);
        //create a cell object
        PdfPCell table_cell;
        
        for(int i=0;i<u.size();i++)
        {
            Integer username = u.get(i).getId();
            System.out.println(username);
            table_cell=new PdfPCell(new Phrase(username+" "));
            my_report_table.addCell(table_cell);
         
        }
        my_pdf_report.add(my_report_table);                       
        my_pdf_report.close();
        
        s.close();
        return "CutomerDashBoard";
        
      }

	
	
	
	
}

