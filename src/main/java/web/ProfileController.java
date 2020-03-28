package web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import bankApp.repositories.UserDetailsImpl;
import database.SessionManager;
import forms.PasswordChange;
import model.User;

@RequestMapping(value="/profile")
@Controller
public class ProfileController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/change_password", method = RequestMethod.GET)
    public String changeAccPasswordPage(final HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object msg = session.getAttribute("msg");
            model.put("message", session.getAttribute("msg"));
            if (msg != null)
                session.removeAttribute("msg");
        }

        model.put("passwordForm", new PasswordChange());
        return "ChangePassword";
    }

    @RequestMapping(value = "/change_password", method = RequestMethod.POST)
    public ModelAndView changeAccPassword(final HttpServletRequest request, HttpServletResponse response,
    		@Valid @ModelAttribute("passwordForm") PasswordChange passwordForm, BindingResult result,
            Map<String, Object> model) {
    	if (result.hasErrors()) {
    		return new ModelAndView("ChangePassword");
    	}

    	UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userDetails.getUser();
        if (!passwordEncoder.matches(passwordForm.getOldpassword(), user.getPassword())) {
        	request.getSession().setAttribute("msg", "Old password doesn't match the records.");
        	return new ModelAndView("redirect:/profile/change_password");
        }

        if (saveNewPassword(passwordForm.getPassword())) {
        	request.getSession().setAttribute("msg", "Password changed successfully!");
        } else {
        	request.getSession().setAttribute("msg", "Password not changed!");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        auth.setAuthenticated(false);
        SecurityContextHolder.getContext().setAuthentication(null);
        return new ModelAndView("redirect:/login");
    }

    private Boolean saveNewPassword(String newPassword) {
        Session s = SessionManager.getSession("");
        Transaction tx = null;

        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            User user = userDetails.getUser();

            tx = s.beginTransaction();

            user.setPassword(passwordEncoder.encode(newPassword));
            s.update(user);

            if (tx.isActive()) tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            s.close();
        } finally {
            s.close();
        }

        return true;
    }
}