package code.domain;

public class PositionVo extends Position {

	//注意page和limit属性要与Layui的数据表格参数一致
	private Integer page;//当前页码
	private Integer limit;//每页显示数量
	private Integer cid;


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

	@Override
	public String toString() {
		return "PositionVo{" +
				"page=" + page +
				", limit=" + limit +
				", cid=" + cid +
				'}';
	}
}
