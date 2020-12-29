package code.controller;

import code.dao.IApplyforlocationDao;
import code.domain.Applicant;


import code.domain.Applyforlocation;
import code.domain.Company;
import code.domain.Position;
import code.service.IApplicantService;
import code.service.IApplyforlocationService;
import code.service.ICompanyService;
import code.service.IPositionService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Controller
@RequestMapping("/applicant")
public class IApplicantController {

	@Autowired
	private IApplicantService applicantService;

	@Autowired
	private ICompanyService companyService;

	@Autowired
	private IPositionService positionService;

	@Autowired
	private IApplyforlocationService applyforlocationService;


	//主页面ajax信息传递
	@ResponseBody
	@RequestMapping(value ="/applicantData" ,produces = "text/json; charset=utf-8")
	public String applicantData(@RequestParam(value = "aid")Integer aid){
		//根据用户id查询用户身份
		List list = new ArrayList();
//		int resumeHasDelivery=0;//投递简历的数量
//		int resumeWaitInterview=0;//等待面试简历的数量
//		int resumeHasAdopt=0;//已经通过简历的数量
//		int resumeHasRefuse=0;//已经拒绝简历的数量
		Applicant applicant =applicantService.findById(aid);
		list.add(applicant);
		int resumeHasAdopt = applyforlocationService.ResumeHasAdopt(aid).size();
		list.add(resumeHasAdopt);
		int resumeHasRefuse = applyforlocationService.ResumeHasRefuse(aid).size();
		list.add(resumeHasRefuse);
		int resumeWaitInterview = applyforlocationService.ResumeWaitInterview(aid).size();
		list.add(resumeWaitInterview);
		int resumeHasDelivery = resumeHasAdopt + resumeHasRefuse + resumeWaitInterview;
		list.add(resumeHasDelivery);
		List<Position> positionList = positionService.findAllOpenPosition();
		list.add(positionList);
		List<Company> companyList = new ArrayList<>();
		for(int i = 0; i < positionList.size(); i++){
			companyList.add(companyService.findByCid(positionList.get(i).getPcid()));
		}
		list.add(companyList);
		String myList = new Gson().toJson(list);
		return myList;
	}

	//搜索（搜索职位名称）
	@ResponseBody
	@RequestMapping(value ="/searchPosition",produces = "text/json; charset=utf-8")
	public String searchPosition(String content,String type,Model model){
		System.out.println(type);
//		System.out.println(applicant.toString());
		List lists = new ArrayList();
		List<Position> positionList = new ArrayList<>();
		List<Company> companyList = new ArrayList<>();
		if (type.equals("职位")){
			positionList =positionService.findByName("%"+content+"%");
			for(int i = 0; i < positionList.size(); i++){
				Company company =(companyService.findByCid(positionList.get(i).getPcid()));
				companyList.add(company);
			}
		}
		else{
			List<Company> companies = companyService.findByName("%"+content+"%");
			System.out.println(content);
			for (int i = 0; i < companies.size(); i++){
				System.out.println(companies);
			}
			for (int i = 0; i < companies.size(); i++){
				List<Position> positions = positionService.findByPcidAndPisopen(companies.get(i).getCid());
				for (int j = 0; j <positions.size(); j++){
					positionList.add(positions.get(j));
					companyList.add(companies.get(i));
				}
			}
		}
		lists.add(positionList);
		lists.add(companyList);
		String endList =  new Gson().toJson(lists);
		return endList;
	}


	//跳转到职位信息查看页面
	@RequestMapping("/jumpToPosition")
	public String jumpToPosition(String aid,String pid,Model model){
		model.addAttribute("aid",aid);
		model.addAttribute("pid",pid);
		Integer id= Integer.valueOf(pid);
		Position position = positionService.findByPid(id);
		Company company = companyService.findByCid(position.getPcid());
		model.addAttribute("position",position);
		model.addAttribute("company",company);
		return "";
	}


	//文件上传职位信息查看页面）
	@ResponseBody
	@RequestMapping("/fileUpload")
	public String fileUpload(HttpServletRequest request,Applyforlocation applyforlocation,MultipartFile upload, RedirectAttributes attr)throws Exception {
		Applyforlocation applyforlocation1 = applyforlocationService.findByPAid(applyforlocation.getApid(),applyforlocation.getAaid());
		System.out.println(applyforlocation1);
		if (applyforlocation1==null){
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
			Date nowDate= new Date();
			String date= sdf.format(nowDate);
			applyforlocation.setAsubmitime(date);
			System.out.println(applyforlocation.toString());
			//使用flieupload组件完成文件上传
			//上传的位置
			String path = request.getSession().getServletContext().getRealPath("/uploads/");
			System.out.println(path);
			File file =new File(path);
			if(!file.exists()){
				file.mkdirs();
			}
			//说明上传文件项
			//获取上传文件的名称
			String filename = upload.getOriginalFilename();
			//把文件的名称设置为唯一值，uuid
			String uuid = UUID.randomUUID().toString().replace("-","");
			filename = uuid+"_"+filename;
			upload.transferTo(new File(path,filename));
			applyforlocation.setAfilepath(filename);
			applyforlocationService.saveRecording(applyforlocation);
			return "success";
		}
		else{
			return "false";
		}
	}


	//跳转到应聘者信息管理页面
	@RequestMapping(value ="/jumpToApplicantData")
	public String jumpToOne(String aid,Model model){
		model.addAttribute("aid",aid);
		return "";
	}

	//显示应聘者信息（个人信息管理页面）
	@ResponseBody
	@RequestMapping(value ="/showMyData",produces = "text/json; charset=utf-8")
	public String showMyData(Integer aid){
		System.out.println(aid);
		Applicant applicant =applicantService.findById(aid);
		//假设已经从后端获取到list数据，直接把list传到toJson（）方法中就行。
		String data = new Gson().toJson(applicant);
		return data;
	}

	//修改应聘者信息（个人信息管理页面）
	@RequestMapping(value ="/updateMyData")
	public String updateMyData(Applicant applicant,RedirectAttributes attr){
//		System.out.println(applicant.toString());
		applicantService.updateApplicant(applicant);
		attr.addAttribute("aid",applicant.getAid());
		return "redirect:/applicant/jumpBackApplicantData";
	}

	//跳转回公司信息管理页面
	@RequestMapping(value ="/jumpBackApplicantDat")
	public String jumpBackApplicantDat(ServletRequest request, Model model){
		String aid =request.getParameter("aid");
		model.addAttribute("aid",aid);
		return "";
	}






















//	//	跳转回应聘公司主页面
//	@RequestMapping(value ="/jumpToApplicantHomepage")
//	public String jumpToApplicantHomepage(String aid,Model model){
//		model.addAttribute("aid",aid);
//		return "applicant_homepage";
//	}
//
//
//
//
//
//
//
//
//
//	//查看已经提交简历的职位（个人信息管理页面）
//	@ResponseBody
//	@RequestMapping(value ="/haveSubmitPosition",produces = "text/json; charset=utf-8")
//	public String haveSubmitPosition(Integer aid){
//		List<Applyforlocation> locations=applyforlocationService.findByAaid(aid);
//		List<Position> positions = new ArrayList<>();
//		List<Company> companys = new ArrayList<>();
//		List lists = new ArrayList();
//		lists.add(locations);
//		for(int i = 0; i < locations.size(); i++){
//			Position position =(positionService.findByPid(locations.get(i).getApid()));
//			positions.add(position);
//			companys.add(companyService.findByCid(position.getPcid()));
//		}
//		lists.add(positions);
//		lists.add(companys);
//		String endList =  new Gson().toJson(lists);
//		return endList;
//	}







//	//跳转回职位信息查看页面
//	@RequestMapping("/jumpBackPosition")
//	public String jumpBackPosition(String aid,String pid,Model model){
//		model.addAttribute("aid",aid);
//		model.addAttribute("pid",pid);
//		Integer id= Integer.valueOf(pid);
//		Position position = positionService.findByPid(id);
//		model.addAttribute("position",position);
//		return "position";
//	}
}
