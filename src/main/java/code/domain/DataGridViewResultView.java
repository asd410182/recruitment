package code.domain;

public class DataGridViewResultView {
	private Integer code = 0;
	private String msg = "";
	private Long count;
	private Object data;


	/**
	 * 封装数据表格的
	 * @param count
	 * @param data
	 */
	public DataGridViewResultView(Long count, Object data) {
		this.count = count;
		this.data = data;
	}

}
