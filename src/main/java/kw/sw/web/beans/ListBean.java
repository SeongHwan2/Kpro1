package kw.sw.web.beans;

public class ListBean {
	private String title;
	private String txt;
	private String fileName;
	private String fileUrl;
	private String nickName;
	private String no;
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTxt() {
		return txt;
	}
	public void setTxt(String txt) {
		this.txt = txt;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	@Override
	public String toString() {
		return "ListBean [title=" + title + ", txt=" + txt + ", fileName=" + fileName + ", fileUrl=" + fileUrl
				+ ", nickName=" + nickName + ", no=" + no + "]";
	}
	
	
	
}
