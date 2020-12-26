package code.domain;

public class Applicant {
	private Integer aid;
	private String aname;
	private Integer aage;
	private String aeducation;
	private String ajobstatus;
	private String aemail;
	private String aphone;

	public Integer getAid() {
		return aid;
	}

	public void setAid(Integer aid) {
		this.aid = aid;
	}


	public String getAname() {
		return aname;
	}

	public void setAname(String aname) {
		this.aname = aname;
	}

	public Integer getAage() {
		return aage;
	}

	public void setAage(Integer aage) {
		this.aage = aage;
	}

	public String getAeducation() {
		return aeducation;
	}

	public void setAeducation(String aeducation) {
		this.aeducation = aeducation;
	}

	public String getAjobstatus() {
		return ajobstatus;
	}

	public void setAjobstatus(String ajobstatus) {
		this.ajobstatus = ajobstatus;
	}

	public String getAemail() {
		return aemail;
	}

	public void setAemail(String aemail) {
		this.aemail = aemail;
	}

	public String getAphone() {
		return aphone;
	}

	public void setAphone(String aphone) {
		this.aphone = aphone;
	}

	@Override
	public String toString() {
		return "Applicant{" +
				"aid=" + aid +
				", aname='" + aname + '\'' +
				", aage=" + aage +
				", aeducation='" + aeducation + '\'' +
				", ajobstatus='" + ajobstatus + '\'' +
				", aemail='" + aemail + '\'' +
				", aphone='" + aphone + '\'' +
				'}';
	}
}
