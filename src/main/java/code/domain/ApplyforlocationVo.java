package code.domain;

public class ApplyforlocationVo {

	//注意page和limit属性要与Layui的数据表格参数一致
	private Integer page;//当前页码
	private Integer limit;//每页显示数量
	private Integer pid;

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

	@Override
	public String toString() {
		return "ApplyforlocationVo{" +
				"page=" + page +
				", limit=" + limit +
				", pid=" + pid +
				'}';
	}
}
