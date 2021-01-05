package code.controller;

import code.domain.*;


import code.service.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@Autowired
	private IApplyforlocationVoService applyforlocationVoService;

	//显示提交过简历的职位列表
	/**
	 * 查看简历列表
	 * @param applyforlocationVo
	 * @return
	 */
	//查看简历列表

	/**
	 * 查看简历列表
	 * @param applyforlocationVo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/showResumePosition")
	public DataGridViewResultView showResumePosition(ApplyforlocationVo applyforlocationVo){
		PageHelper.startPage(applyforlocationVo.getPage(),applyforlocationVo.getLimit());
		List<Applyforlocation> applyforlocationList = applyforlocationVoService.findPositionList(applyforlocationVo);
		return quickForResumePosition(applyforlocationList);
	}

	/**
	 * 查看简历列表 传name
	 * @param applyforlocationVo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/showResumePositionByName")
	public DataGridViewResultView showResumePositionByName(ApplyforlocationVo applyforlocationVo){
		applyforlocationVo.setPname("%"+applyforlocationVo.getPname()+"%");
		PageHelper.startPage(applyforlocationVo.getPage(),applyforlocationVo.getLimit());
		List<Applyforlocation> applyforlocationList =applyforlocationVoService.findPositionListByName(applyforlocationVo);
		return quickForResumePosition(applyforlocationList);
	}

	//应聘者主页面

	//主页面ajax信息传递
	@ResponseBody
	@RequestMapping(value ="/applicantData" ,produces = "text/json; charset=utf-8")
	public String applicantData(@RequestParam(value = "aid")Integer aid){
		//根据用户id查询用户身份
		List list = new ArrayList();
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
	public String searchPosition(String content,String type){
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



	//是否上传过简历
	@ResponseBody
	@RequestMapping(value ="/showPositionAndResume",produces = "text/json; charset=utf-8")
	public String showPosition(@RequestParam( value="pid")Integer pid, @RequestParam( value="aid")Integer aid){
		List lists = new ArrayList();
		Position position = positionService.findByPid(pid);
		Company company = companyService.findByCid(position.getPcid());
		String result;
		lists.add(position);
		lists.add(company);
		Applyforlocation applyforlocation = applyforlocationService.findByPAid(pid,aid);
		if (applyforlocation == null) {
			result = "没有投递过简历";
		}
		else{
			result = "投递过简历";
		}
		lists.add(result);
		String endList =  new Gson().toJson(lists);
		return endList;
	}


	//文件上传
	@RequestMapping("/fileUpload")
	public String fileUpload(HttpServletRequest request,
						   Applyforlocation applyforlocation,
						   MultipartFile upload)throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		Date nowDate = new Date();
		String date = sdf.format(nowDate);
		applyforlocation.setAsubmitime(date);
			//使用flieupload组件完成文件上传
			//上传的位置
		String path = request.getSession().getServletContext().getRealPath("/uploads/");
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
		return "redirect:/pages/ResumeDelivery.html?aaid="+applyforlocation.getAaid()+"&apid="+applyforlocation.getApid();
	}


	//应聘者个人信息管理页面

	//显示应聘者信息（个人信息管理页面）
	@ResponseBody
	@RequestMapping(value ="/showMyData",produces = "text/json; charset=utf-8")
	public String showMyData(Integer aid){
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
	public String updateMyData(Applicant applicant){
		applicantService.updateApplicant(applicant);
		return "redirect:/pages/Personalinfo.html?aid="+applicant.getAid();
	}


	//注册后信息完善信息时的操作

	//修改应聘者信息
	@RequestMapping(value = "/updateData")
	public String updateData(Applicant applicant){
		applicantService.updateApplicant(applicant);
		return "redirect:/pages/ApplicantHome.html?aid="+applicant.getAid();
	}

	public DataGridViewResultView quickForResumePosition(List<Applyforlocation> applyforlocationList){
		ArrayList<Resume> resumeList = new ArrayList<Resume>();
		for(int i = 0; i <applyforlocationList.size(); i++){
			Resume resume = new Resume();
			resume.setApplyforlocation(applyforlocationList.get(i));
			Position position = positionService.findByPid(applyforlocationList.get(i).getApid());
			resume.setPosition(position);
			resumeList.add(resume);
		}
		PageInfo<Resume> pageInfo = new PageInfo<Resume>(resumeList);
		return new DataGridViewResultView(pageInfo.getTotal(),pageInfo.getList());
	}

}
