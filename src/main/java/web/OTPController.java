package web;

@Controller
public class OTPController {
	@RequestMapping(value ="/generateAccountOtp", method = RequestMethod.GET)
	public ModelAndView generateLoginOtp(HttpServletRequest request, HttpSession session){
		ModelMap model = new ModelMap();
		return new ModelAndView("OtpPage",model);
	}
	
	
	@RequestMapping(value ="/validateOtp", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> validateOtp(@RequestParam("otpnum") int otpnum, HttpSession session){
	return SUCCESS;	
	}
}