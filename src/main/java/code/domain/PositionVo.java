package code.domain;

public class PositionVo extends Position {

	//注意page和limit属性要与Layui的数据表格参数一致
	private Integer page;//当前页码
	private Integer limit;//每页显示数量
	private Integer cid;
	private String pisopen;
	private String panme;


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

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}


	public String getPisopen() {
		return pisopen;
	}

	public void setPisopen(String pisopen) {
		this.pisopen = pisopen;
	}

	public String getPanme() {
		return panme;
	}

	public void setPanme(String panme) {
		this.panme = panme;
	}

	@Override
	public String toString() {
		return "PositionVo{" +
				"page=" + page +
				", limit=" + limit +
				", cid=" + cid +
				", pisopen='" + pisopen + '\'' +
				", panme='" + panme + '\'' +
				'}';
	}
}
