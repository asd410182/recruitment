package code.controller;

import code.domain.User;
import code.service.IApplicantService;
import code.service.ICompanyService;
import code.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/user")
public class IUserController {
	@Autowired
	private IUserService userService;

	@Autowired
	private ICompanyService companyService;

	@Autowired
	private IApplicantService applicantService;


	//注册页面进行信息的注册
	@ResponseBody
	@RequestMapping("/save")
	public String save(User user) throws IOException {
		Integer uid = userService.login(user.getPhone(),user.getPwd());
		System.out.println(user);
		System.out.println(uid);
		Integer id = userService.findByAccount(user.getPhone());
		System.out.println(id);
		if(id == null){
			userService.save(user);
			Integer userid = userService.findByAccount(user.getPhone());
			String enduserid = String.valueOf(userid);
			return enduserid;
		}
		else if(uid == null){
			return "fail";
		}
		else{
			return "successfail";
		}
	}

	//注册界面直接进行跳转，跳转到信息完善界面
	@RequestMapping("/jumpAfterRegister")
	public String jumpAfterRegister(@RequestParam(value = "uid")Integer uid, RedirectAttributes attr){
		//根据用户id查询用户身份
		String role = userService.findRoleById(uid);
		System.out.println(role);
		if (role.equals("applicant")){
			String aid = String.valueOf(uid);
			attr.addAttribute("aid",aid);
			return "applicant_homepage";
		}
		else {
			String cid = String.valueOf(uid);
			attr.addAttribute("cid",cid);
			return "redirect:/company/jumpToPerfectCompanyData";
		}
	}

	//登录页面信息验证
	@ResponseBody
	@RequestMapping("/login")
	public  String login(@RequestParam(value = "account")String uaccount, @RequestParam(value = "pwd")String upassword) throws Exception{
		Integer uid = userService.login(uaccount,upassword);
		String id = String.valueOf(uid);
		if(uid == null){
			return "fail";
		}
		else{
			return id;
		}
	}

	//登录页面跳转判断
	@RequestMapping("/jump")
	public String jump(@RequestParam(value = "uid")Integer uid, Model model,RedirectAttributes attr){
		//根据用户id查询用户身份
		String role = userService.findRoleById(uid);
		if (role.equals("applicant")){
			String aid = String.valueOf(uid);
			Integer id = Integer.valueOf(aid);
			String name = applicantService.findById(id).getAname();
//			System.out.println(name);
//			if(name == null) {
//				attr.addAttribute("cid",cid);
//				return "redirect:/company/jumpToPerfectCompanyData";
//			}
//			else{
//				model.addAttribute("aid", id);
//				return "CompanyHome";
//			}
			return"";
		}
		else {
			String cid = String.valueOf(uid);
			Integer id = Integer.valueOf(cid);
			String name = companyService.findByCid(id).getCname();
			System.out.println(name);
			if(name == null) {
				attr.addAttribute("cid",cid);
				return "redirect:/company/jumpToPerfectCompanyData";
			}
			else{
				model.addAttribute("aid", id);
				return "CompanyHome";
			}
		}
	}

}
