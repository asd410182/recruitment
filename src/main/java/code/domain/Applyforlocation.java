package code.domain;

import java.util.Date;

public class Applyforlocation {
	private Integer aaid;
	private Integer apid;
	private String asubmitime;
	private String astatus;
	private String afilepath;

	public Integer getAaid() {
		return aaid;
	}

	public void setAaid(Integer aaid) {
		this.aaid = aaid;
	}

	public Integer getApid() {
		return apid;
	}

	public void setApid(Integer apid) {
		this.apid = apid;
	}

	public String getAsubmitime() {
		return asubmitime;
	}

	public void setAsubmitime(String asubmitime) {
		this.asubmitime = asubmitime;
	}

	public String getAstatus() {
		return astatus;
	}

	public void setAstatus(String astatus) {
		this.astatus = astatus;
	}

	public String getAfilepath() {
		return afilepath;
	}

	public void setAfilepath(String afilepath) {
		this.afilepath = afilepath;
	}

	@Override
	public String toString() {
		return "Applyforlocation{" +
				"aaid=" + aaid +
				", apid=" + apid +
				", asubmitime='" + asubmitime + '\'' +
				", astatus='" + astatus + '\'' +
				", afilepath='" + afilepath + '\'' +
				'}';
	}
}
