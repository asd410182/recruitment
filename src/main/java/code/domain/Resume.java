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
				'}';
	}
}
