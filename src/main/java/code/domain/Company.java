package code.domain;

public class Company {
	private Integer cid;
	private String cname;
	private String cintroduction;
	private String caddress;
	private String cphone;
	private String cemail;

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getCintroduction() {
		return cintroduction;
	}

	public void setCintroduction(String cintroduction) {
		this.cintroduction = cintroduction;
	}

	public String getCaddress() {
		return caddress;
	}

	public void setCaddress(String caddress) {
		this.caddress = caddress;
	}

	public String getCphone() {
		return cphone;
	}

	public void setCphone(String cphone) {
		this.cphone = cphone;
	}

	public String getCemail() {
		return cemail;
	}

	public void setCemail(String cemail) {
		this.cemail = cemail;
	}

	@Override
	public String toString() {
		return "Company{" +
				"cid=" + cid +
				", cname='" + cname + '\'' +
				", cintroduction='" + cintroduction + '\'' +
				", caddress='" + caddress + '\'' +
				", cphone='" + cphone + '\'' +
				", cemail='" + cemail + '\'' +
				'}';
	}
}
