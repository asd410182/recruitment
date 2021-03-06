package code.controller;

import code.domain.*;
import code.service.*;
import com.alibaba.druid.support.json.JSONUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import net.sf.json.JSONArray;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping("/company")
public class ICompanyController {

	@Autowired
	private IApplicantService applicantService;

	@Autowired
	private ICompanyService companyService;

	@Autowired
	private IPositionService positionService;

	@Autowired
	private IApplyforlocationService applyforlocationService;

	@Autowired
	private IPositionVoService positionVoService;

	@Autowired
	private IApplyforlocationVoService applyforlocationVoService;


	/**
	*@Description 招聘公司信息
	*@Param * @param cid
	*@Return java.lang.String
	*@Author SiYunXin
	*@Date 2021/1/4
	*@Time 23:40
	*/
	//招聘公司主页面
	//ajax传招聘公司信息
	@ResponseBody
	@RequestMapping(value = "/companyData",produces = "text/json; charset=utf-8")
	public String companyData(@RequestParam(value = "cid")Integer cid){
		Company company = companyService.findByCid(cid);//得到公司信息
		String companyData = new Gson().toJson(company);
		return companyData;
	}

	/**
	*@Description
	*@Param * @param company
	*@Return java.lang.String
	*@Author SiYunXin
	*@Date 2021/1/4
	*@Time 23:40
	*/
	//完善招聘公司信息
	@RequestMapping(value = "/fillCompanyData")
	public String fillCompanyData(Company company){
		companyService.updateCompany(company);
		return "redirect:/pages/index.html?cid="+company.getCid();
	}

	

	//更新招聘公司信息
	@ResponseBody
	@RequestMapping(value = "/updateMyData",produces = "text/json; charset=utf-8")
	public String updateMyData(Company company){
		System.out.println(company.toString());
		System.out.println(company.getCid());
		companyService.updateCompany(company);
//		attr.addAttribute("cid",company.getCid());
		Map<String,Object> map = new HashMap<String,Object>();
		if(companyService.updateCompany(company)>0){
			map.put("success",true);
			map.put("message","信息更新成功");
		}else{
			map.put("success",false);
			map.put("message","信息更新失败");
		}
		return JSONUtils.toJSONString(map);
	}



	//返回职位列表
	@ResponseBody
	@RequestMapping(value = "/showPosition")
	public DataGridViewResultView showPosition(PositionVo positionVo){
		PageHelper.startPage(positionVo.getPage(),positionVo.getLimit());
		List<Position> positionList = positionVoService.findPositionList(positionVo);
		return quickForPosition(positionList);
	}

	/**
	*@Description  点击搜索时调用,搜索职位
	*@Param * @param positionVo 传参要cid，pisopen，pname
	*@Return code.domain.DataGridViewResultView
	*@Author SiYunXin
	*@Date 2021/1/4
	*@Time 23:36
	*/
	@ResponseBody
	@RequestMapping(value = "/searchPosition")
	public DataGridViewResultView searchPosition(PositionVo positionVo){
		List<Position> positionList = new ArrayList<Position>();
		PageHelper.startPage(positionVo.getPage(), positionVo.getLimit());
		if(positionVo.getPisopen().equals("01")){
			positionVo.setPanme("%"+positionVo.getPanme()+"%");
			positionList = positionVoService.findByPname(positionVo);
		}
		else {
			positionVo.setPanme("%" + positionVo.getPanme() + "%");
			positionList = positionVoService.findByPnameAndPisoen(positionVo);
		}
		return quickForPosition(positionList);
	}

	/**
	 *
	 * @param positionVo
	 * @return
	 */
	//选择下拉框时调用
	//搜索职位，传参要cid，pisopen
	@ResponseBody
	@RequestMapping(value = "/searchPositionByPisopen")
	public DataGridViewResultView searchPositionByPisopen(PositionVo positionVo){
		PageHelper.startPage(positionVo.getPage(),positionVo.getLimit());
		List<Position> positionList = new ArrayList<Position>();
		if(positionVo.getPisopen().equals("01")){
			positionList = positionVoService.findPositionList(positionVo);
		}
		else {
			positionList =  positionVoService.findByPisopen(positionVo);
		}
		return quickForPosition(positionList);
	}



	public DataGridViewResultView quickForPosition(List<Position> positionList){
		for(int i = 0; i < positionList.size(); i++){
			int waitcount = applyforlocationService.PositionNoHavePass(positionList.get(i).getPid()).size();
			positionList.get(i).setPwaitcount(waitcount);
		}
		PageInfo<Position> pageInfo = new PageInfo<Position>(positionList);
		return new DataGridViewResultView(pageInfo.getTotal(),pageInfo.getList());
	}


	//	发布职位
	@ResponseBody
	@RequestMapping(value = "/addPosition",produces = "text/json; charset=utf-8")
	public String addPosition(Position position){
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		Date nowDate= new Date();
		String date= sdf.format(nowDate);
		position.setPreleasetime(date);

		Map<String,Object> map = new HashMap<String,Object>();
		if(positionService.savePosition(position)>0){
			map.put("success",true);
			map.put("message","添加职位成功");
		}else{
			map.put("success",false);
			map.put("message","添加职位失败");
		}
		return JSONUtils.toJSONString(map);
	}


	/**
	 *
	 * @param pid
	 * @return
	 */
	//删除职位信息
	@ResponseBody
	@RequestMapping(value = "/deletePosition",produces = "text/json; charset=utf-8")
	public String deletePosition(@RequestParam(value = "pid")Integer pid){
		System.out.println(pid);
		Map<String,Object> map = new HashMap<String,Object>();
		if (positionService.deletePosition(pid)>0){
			map.put("success",true);
			map.put("message","删除成功");
		}
		else{
			map.put("success",false);
			map.put("message","删除失败");
		}
		return JSONUtils.toJSONString(map);
	}

	/**
	 *
	 * @param pids
	 * @return
	 */
	//批量删除
	@ResponseBody
	@RequestMapping(value = "/batchDelete",produces = "text/json; charset=utf-8")
	public String BarchDelete(@RequestParam(value = "PidList")String pids){
		Map<String,Object> map = new HashMap<String,Object>();
		int count = 0;
		//将字符串拆分成数组
		String[] pidstr = pids.split(",");
		for (int i = 0; i < pidstr.length; i++) {
			count = positionService.deletePosition(Integer.valueOf(pidstr[i]));
			if (count > 0) {
				map.put("success", true);
				map.put("message", "删除成功");
			}
		}
		//判断受影响的行数是否为0
		if (count<=0){
			map.put("success", false);
			map.put("message", "删除失败");
		}
		return JSONUtils.toJSONString(map);
	}


	//	更新职位
	@ResponseBody
	@RequestMapping(value = "/updatePosition" ,produces = "text/json; charset=utf-8")
	public String updatePosition(Position position){
		Map<String,Object> map = new HashMap<String,Object>();
		System.out.println(position.toString());
		if(positionService.updatePosition(position)>0){
			map.put("success",true);
			map.put("message","修改职位成功");
		}else{
			map.put("success",false);
			map.put("message","修改职位失败");
		}
		return JSONUtils.toJSONString(map);
	}

	//ajax传职位信息
	@ResponseBody
	@RequestMapping(value = "/positionData",produces = "text/json; charset=utf-8")
	public String positionData(@RequestParam(value = "pid")Integer pid){
		Position position = positionService.findByPid(pid);
		String positionData = new Gson().toJson(position);
		return positionData;
	}

	/**
	 * 查看简历列表
	 * @param applyforlocationVo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/showResume")
	public DataGridViewResultView showResume(ApplyforlocationVo applyforlocationVo){
		System.out.println(applyforlocationVo);
		PageHelper.startPage(applyforlocationVo.getPage(),applyforlocationVo.getLimit());
		List<Applyforlocation> applyforlocationList =applyforlocationVoService.findApplicntList(applyforlocationVo);
		return quickForResume(applyforlocationList);
	}

	/**
	 * 查看简历列表 传name
	 * @param applyforlocationVo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/showResumeByName")
	public DataGridViewResultView showResumeByName(ApplyforlocationVo applyforlocationVo){
		applyforlocationVo.setAname("%"+applyforlocationVo.getAname()+"%");
		PageHelper.startPage(applyforlocationVo.getPage(),applyforlocationVo.getLimit());
		List<Applyforlocation> applyforlocationList =applyforlocationVoService.findApplicntListByName(applyforlocationVo);
		return quickForResume(applyforlocationList);
	}


	public DataGridViewResultView quickForResume(List<Applyforlocation> applyforlocationList){
		ArrayList<Resume> resumeList = new ArrayList<Resume>();
		for(int i = 0; i <applyforlocationList.size(); i++){
			Resume resume = new Resume();
			resume.setApplyforlocation(applyforlocationList.get(i));
			Applicant applicant = applicantService.findById(applyforlocationList.get(i).getAaid());
			resume.setApplicant(applicant);
			resumeList.add(resume);
			System.out.println("传到前台的参数\n");
			System.out.println(resume);
		}
		PageInfo<Resume> pageInfo = new PageInfo<Resume>(resumeList);
		return new DataGridViewResultView(pageInfo.getTotal(),pageInfo.getList());
	}


	//通知面试
	@ResponseBody
	@RequestMapping(value = "/acceptResume" ,produces = "text/json; charset=utf-8")
	public String acceptResume(@RequestParam(value = "apid")Integer apid,@RequestParam(value = "aaid")Integer aaid){
		Map<String,Object> map = new HashMap<String,Object>();
		Applyforlocation applyforlocation =applyforlocationService.findByPAid(apid,aaid);
		if(applyforlocationService.acceptResume(applyforlocation)>0){
			map.put("success",true);
			map.put("message","通知面试成功");
		}else{
			map.put("success",false);
			map.put("message","通知面试失败");
		}
		return JSONUtils.toJSONString(map);
	}

	/**
	 *
	 * @param pids
	 * @return
	 */
	//批量通知面试
	@ResponseBody
	@RequestMapping(value = "/batchAcceptResume",produces = "text/json; charset=utf-8")
	public String batchAcceptResume(@RequestParam(value = "list")String pids){
		int count = 0;
//		//将字符串拆分成数组
		JSONArray jsonArray = JSONArray.fromObject(pids);
		List<Applyforlocation> list = new ArrayList<Applyforlocation>();
		list = (List<Applyforlocation>) JSONArray.toCollection(jsonArray, Applyforlocation.class);//转换成列表

		Map<String,Object> map = new HashMap<String,Object>();
		for (int i = 0; i < list.size(); i++) {
			count = applyforlocationService.acceptResume(list.get(i));
			if (count > 0) {
				map.put("success", true);
				map.put("message", "通知面试成功");
			}
		}
		//判断受影响的行数是否为0
		if (count<=0){
			map.put("success", false);
			map.put("message", "通知面试失败");
		}
		return JSONUtils.toJSONString(map);
	}

	//拒绝
	@ResponseBody
	@RequestMapping(value = "/refuseResume" ,produces = "text/json; charset=utf-8")
	public String refuseResume(@RequestParam(value = "apid")Integer apid,@RequestParam(value = "aaid")Integer aaid){
		Map<String,Object> map = new HashMap<String,Object>();
		Applyforlocation applyforlocation =applyforlocationService.findByPAid(apid,aaid);
		if(applyforlocationService.refuseResume(applyforlocation)>0){
			map.put("success",true);
			map.put("message","拒绝成功");
		}else{
			map.put("success",false);
			map.put("message","拒绝失败");
		}
		return JSONUtils.toJSONString(map);
	}
	//批量拒绝
	@ResponseBody
	@RequestMapping(value = "/batchRefuseResume",produces = "text/json; charset=utf-8")
	public String batchRefuseResume(@RequestParam(value = "list")String pids){
		JSONArray jsonArray = JSONArray.fromObject(pids);
		List<Applyforlocation> list = new ArrayList<Applyforlocation>();
		Map<String,Object> map = new HashMap<String,Object>();
		list = (List<Applyforlocation>) JSONArray.toCollection(jsonArray, Applyforlocation.class);//转换成列表
		int count = 0;
		for (int i = 0; i < list.size(); i++) {
			count = applyforlocationService.refuseResume(list.get(i));
			if (count > 0) {
				map.put("success", true);
				map.put("message", "拒绝成功");
			}
		}
		//判断受影响的行数是否为0
		if (count<=0){
			map.put("success", false);
			map.put("message", "拒绝失败");
		}
		return JSONUtils.toJSONString(map);
	}


	//文件下载
	@ResponseBody
	@RequestMapping("/fileDownload")
	public ResponseEntity<byte[]> fileDownload(HttpServletRequest request, @RequestParam(value="aaid") Integer aaid, @RequestParam(value="apid")Integer apid) throws Exception{
		String path = request.getServletContext().getRealPath("/uploads/");
		Applyforlocation applyforlocation = applyforlocationService.findByPAid(apid, aaid);
		String filename =applyforlocation.getAfilepath();
		File file = new File(path+File.separator+filename);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDispositionFormData("attachment",filename);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.OK);
	}

}
