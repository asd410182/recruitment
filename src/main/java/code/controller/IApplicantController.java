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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
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

	//	跳转到应聘者主页面
	@RequestMapping(value ="/jumpToApplicantHomepage")
	public String jumpToApplicantHomepage(String aid,Model model){
		model.addAttribute("aid",aid);
		return "applicant_homepage";
	}

	//跳转到应聘者信息管理页面
	@RequestMapping(value ="/jumpToApplicantData")
	public String jumpToOne(String aid,Model model){
		model.addAttribute("aid",aid);
		return "applicant_information_manage";
	}

//	得到所有职位列表（应聘者主页面）
	@ResponseBody
	@RequestMapping(value ="/showPosition",produces = "text/json; charset=utf-8")
	public String showPosition(){
		List<Position> positions = positionService.findAllPosition();
//		for (int x = 0; x < positions.size(); x++) {
//			System.out.println(positions.get(x));
//		}
		//假设已经从后端获取到list数据，直接把list传到toJson（）方法中就行。
		String places = new Gson().toJson(positions);
		System.out.println(places);
		return places;
	}

	//搜索（搜索职位名称）
	@RequestMapping(value ="/searchPosition")
	public String searchPosition(String content,String aid,Model model){
//		System.out.println(applicant.toString());
		List<Position> positionList =positionService.findByName("%"+content+"%");
		List<Company> companyList = new ArrayList<>();
		List lists = new ArrayList();
		lists.add(positionList);
		for(int i = 0; i < positionList.size(); i++){
			Company company =(companyService.findByCid(positionList.get(i).getPcid()));
			companyList.add(company);
		}
		lists.add(companyList);
		model.addAttribute("aid",aid);
		String endList =  new Gson().toJson(lists);
		model.addAttribute("endList",endList);
		return "inquire";
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
	public String updateMyData(Applicant applicant,Model model){
//		System.out.println(applicant.toString());
		applicantService.updateApplicant(applicant);
		model.addAttribute("aid",applicant.getAid());
		return "applicant_information_manage";
	}


	//查看已经提交简历的职位（个人信息管理页面）
	@ResponseBody
	@RequestMapping(value ="/haveSubmitPosition",produces = "text/json; charset=utf-8")
	public String haveSubmitPosition(Integer aid){
		List<Applyforlocation> locations=applyforlocationService.findByAaid(aid);
		List<Position> positions = new ArrayList<>();
		List<Company> companys = new ArrayList<>();
		List lists = new ArrayList();
		lists.add(locations);
		for(int i = 0; i < locations.size(); i++){
			Position position =(positionService.findByPid(locations.get(i).getApid()));
			positions.add(position);
			companys.add(companyService.findByCid(position.getPcid()));
		}
		lists.add(positions);
		lists.add(companys);
		String endList =  new Gson().toJson(lists);
		return endList;
	}

	//跳转到职位信息查看页面（职位信息查看页面）
	@RequestMapping("/jumpToPosition")
	public String jumpToPosition(String aid,String pid,Model model){
		model.addAttribute("aid",aid);
		model.addAttribute("pid",pid);
		Integer id= Integer.valueOf(pid);
		Position position = positionService.findByPid(id);
		model.addAttribute("position",position);
		return "position";
	}

	//文件上传职位信息查看页面）
	@RequestMapping("/fileUpload")
	public String fileUpload(HttpServletRequest request,Applyforlocation applyforlocation,MultipartFile upload)throws Exception {
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
		return"success";
	}
}
