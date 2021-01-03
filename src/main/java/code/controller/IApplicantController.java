package code.controller;

import code.domain.*;


import code.service.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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

	@Autowired
	private IApplyforlocationVoService applyforlocationVoService;


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


	//职位查看页面

	//跳转到职位信息查看页面
	@RequestMapping("/jumpToPosition")
	public String jumpToPosition(String aid,String pid,Model model){
		model.addAttribute("aid",aid);
		model.addAttribute("pid",pid);
		return "ResumeDelivery";
	}

	//跳转到职位信息查看页面
	@ResponseBody
	@RequestMapping("/submitPosition")
	public String submitPosition(String pid){
		Integer id =Integer.valueOf(pid);
		Position position =positionService.findByPid(id);
		String data = new Gson().toJson(position);
		return data;
	}



	//文件上传
	@ResponseBody
	@RequestMapping("/fileUpload")
	public String fileUpload(HttpServletRequest request,
							 Applyforlocation applyforlocation,
							 @RequestParam( value="files",required=false)MultipartFile upload,
							 RedirectAttributes attr)throws Exception {
		Applyforlocation applyforlocation1 = applyforlocationService.findByPAid(applyforlocation.getApid(), applyforlocation.getAaid());
		System.out.println(applyforlocation1);
		if (applyforlocation1 == null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
			Date nowDate = new Date();
			String date = sdf.format(nowDate);
			applyforlocation.setAsubmitime(date);
			System.out.println(applyforlocation.toString());
			//使用flieupload组件完成文件上传
			//上传的位置
			String path = request.getSession().getServletContext().getRealPath("/uploads/");
			System.out.println(path);
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			//说明上传文件项
			//获取上传文件的名称
			String filename = upload.getOriginalFilename();
			//把文件的名称设置为唯一值，uuid
			String uuid = UUID.randomUUID().toString().replace("-", "");
			filename = uuid + "_" + filename;
			upload.transferTo(new File(path, filename));
			applyforlocation.setAfilepath(filename);
			applyforlocationService.saveRecording(applyforlocation);
			return "success";
		}
		return "false";
	}

	//进行应聘者信息管理
	//跳转到应聘者信息管理页面
	@RequestMapping(value ="/jumpToApplicantData")
	public String jumpToOne(String aid,Model model){
		model.addAttribute("aid",aid);
		return "Personalinfo";
	}

	//显示应聘者信息（个人信息管理页面）
	@ResponseBody
	@RequestMapping(value ="/showMyData",produces = "text/json; charset=utf-8")
	public String showMyData(Integer aid){
		System.out.println(aid);
		List lists = new ArrayList();
		List<Position> positionList = new ArrayList();
		Applicant applicant =applicantService.findById(aid);
		lists.add(applicant);
		List<Applyforlocation> applyforlocationList = applyforlocationService.findByAaid(aid);
		for(int i = 0; i < applyforlocationList.size(); i++){
			positionList.add(positionService.findByPid(applyforlocationList.get(i).getApid()));
		}
		//假设已经从后端获取到list数据，直接把list传到toJson（）方法中就行。
		lists.add(applyforlocationList);
		lists.add(positionList);
		String endList = new Gson().toJson(lists);
		return endList;
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
	@RequestMapping(value ="/jumpBackApplicantData")
	public String jumpBackApplicantData(ServletRequest request, Model model){
		String aid =request.getParameter("aid");
		model.addAttribute("aid",aid);
		return "Personalinfo";
	}


	//注册后信息没完善时进行的操作

	//跳转到信息完善界面
	@RequestMapping(value ="/jumpToPerfectApplicant")
	public String jumpToPerfectApplicant(ServletRequest request,Model model){
		String aid =request.getParameter("aid");
		model.addAttribute("aid",aid);
		return "PerfectApplicant";
	}

	//完善招聘公司信息
	@RequestMapping(value = "/updateData")
	public String updateData(Applicant applicant, RedirectAttributes attr){
		System.out.println(applicant);
		applicantService.updateApplicant(applicant);
		attr.addAttribute("aid",applicant.getAid());
		return "redirect:/applicant/jumpToApplicant";
	}

	//跳回主页面
	@RequestMapping(value ="/jumpToApplicant")
	public String jumpToCompany(ServletRequest request, Model model){
		String aid =request.getParameter("aid");
		model.addAttribute("aid",aid);
		return "ApplicantHome";
	}


	//显示提交过简历的职位列表
	/**
	 * 查看简历列表
	 * @param applyforlocationVo
	 * @return
	 */
	//查看简历列表
	@ResponseBody
	@RequestMapping(value = "/showResume")
	public DataGridViewResultView showResume(ApplyforlocationVo applyforlocationVo){
		ArrayList<Resume> resumeList = new ArrayList<Resume>();
		PageHelper.startPage(applyforlocationVo.getPage(),applyforlocationVo.getLimit());
		List<Applyforlocation> applyforlocationList =applyforlocationVoService.findPositionList(applyforlocationVo);
		for(int i = 0; i <applyforlocationList.size(); i++){
			Resume resume = new Resume();
			Position position = positionService.findByPid(applyforlocationList.get(i).getApid());
			resume.setPosition(position);
			resume.setApplyforlocation(applyforlocationList.get(i));
			resumeList.add(resume);
		}
		PageInfo<Resume> pageInfo = new PageInfo<Resume>(resumeList);
		return new DataGridViewResultView(pageInfo.getTotal(),pageInfo.getList());
	}
}
