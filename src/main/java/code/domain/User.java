package code.domain;

public class User {
	private Integer uid;
	private String role;
	private String phone;
	private String pwd;

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Override
	public String toString() {
		return "User{" +
				"uid=" + uid +
				", role='" + role + '\'' +
				", phone='" + phone + '\'' +
				", pwd='" + pwd + '\'' +
				'}';
	}
}
