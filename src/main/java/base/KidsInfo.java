package base;

public class KidsInfo {
	
	
	String mobileno;
	String parentName;
	String kidName;
	String Url;
	String Gender;
	
	public KidsInfo(String mobileno, String parentName, String kidName, String url, String gender) {
		super();
		this.mobileno = mobileno;
		this.parentName = parentName;
		this.kidName = kidName;
		Url = url;
		Gender = gender;
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getKidName() {
		return kidName;
	}

	public void setKidName(String kidName) {
		this.kidName = kidName;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

}
