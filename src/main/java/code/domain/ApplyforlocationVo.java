package code.domain;

public class ApplyforlocationVo {

	//注意page和limit属性要与Layui的数据表格参数一致
	private Integer page;//当前页码
	private Integer limit;//每页显示数量
	private Integer pid;
	private Integer aid;
	private String astatus;
	private String aname;
	private String pname;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getAid() {
		return aid;
	}

	public void setAid(Integer aid) {
		this.aid = aid;
	}

	public String getAstatus() {
		return astatus;
	}

	public void setAstatus(String astatus) {
		this.astatus = astatus;
	}

	public String getAname() {
		return aname;
	}

	public void setAname(String aname) {
		this.aname = aname;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	@Override
	public String toString() {
		return "ApplyforlocationVo{" +
				"page=" + page +
				", limit=" + limit +
				", pid=" + pid +
				", aid=" + aid +
				", astatus='" + astatus + '\'' +
				", aname='" + aname + '\'' +
				", pname='" + pname + '\'' +
				'}';
	}
}
