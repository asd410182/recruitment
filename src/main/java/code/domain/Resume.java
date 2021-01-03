package code.domain;

public class Resume {

	private Integer aid;
	private String aname;
	private Integer aage;
	private String aeducation;
	private String ajobstatus;
	private String aemail;
	private String aphone;

	private Integer apid;
	private String asubmitime;
	private String astatus;
	private String afilepath;

	private String pname;
	private String pneed;
	private String pcontent;
	private String psalary;
	private String pisopen;
	private String plocation;
	private Integer pcid;
	private String pexperience;
	private String pacademic;
	private String preleasetime;
	private Integer pallcount;
	private Integer pwaitcount;


	public void setApplicant(Applicant applicant) {
		this.aid = applicant.getAid();
		this.aname = applicant.getAname();
		this.aage = applicant.getAage();
		this.aeducation =applicant.getAeducation();
		this.ajobstatus = applicant.getAjobstatus();
		this.aemail = applicant.getAemail();
		this.aphone = applicant.getAphone();
	}

	public  void setApplyforlocation(Applyforlocation applyforlocation){
		this.apid = applyforlocation.getApid();
		this.asubmitime = applyforlocation.getAsubmitime();
		this.astatus = applyforlocation.getAstatus();
		this.afilepath = applyforlocation.getAfilepath();
	}


	public void setPosition(Position position){
		this.pname =position.getPname();
		this.pcontent = position.getPcontent();
		this.psalary = position.getPsalary();
		this.pisopen =position.getPisopen();
		this.plocation = position.getPlocation();
		this.pcid = position.getPcid();
		this.pexperience = position.getPexperience();
		this.pacademic =position.getPacademic();
		this.preleasetime =position.getPreleasetime();
		this.pallcount = position.getPallcount();
		this.pwaitcount =position.getPwaitcount();
	}

	@Override
	public String toString() {
		return "Resume{" +
				"aid=" + aid +
				", aname='" + aname + '\'' +
				", aage=" + aage +
				", aeducation='" + aeducation + '\'' +
				", ajobstatus='" + ajobstatus + '\'' +
				", aemail='" + aemail + '\'' +
				", aphone='" + aphone + '\'' +
				", apid=" + apid +
				", asubmitime='" + asubmitime + '\'' +
				", astatus='" + astatus + '\'' +
				", afilepath='" + afilepath + '\'' +
				", pname='" + pname + '\'' +
				", pneed='" + pneed + '\'' +
				", pcontent='" + pcontent + '\'' +
				", psalary='" + psalary + '\'' +
				", pisopen='" + pisopen + '\'' +
				", plocation='" + plocation + '\'' +
				", pcid=" + pcid +
				", pexperience='" + pexperience + '\'' +
				", pacademic='" + pacademic + '\'' +
				", preleasetime='" + preleasetime + '\'' +
				", pallcount=" + pallcount +
				", pwaitcount=" + pwaitcount +
				", preleasetime='" + preleasetime + '\'' +
				", pallcount=" + pallcount +
				", pwaitcount=" + pwaitcount +
				'}';
	}
}
