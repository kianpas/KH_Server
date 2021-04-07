package board.model.vo;

public class Attachment {

	private int no;
	private int boardNo;
	private String originalFileName;
	private String renameFileName;
	private boolean status; //status Y | N 처리되므로, jdbc에서 형변환필요.
	
	public Attachment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Attachment(int no, int boardNo, String originalFileName, String renameFileName, boolean status) {
		super();
		this.no = no;
		this.boardNo = boardNo;
		this.originalFileName = originalFileName;
		this.renameFileName = renameFileName;
		this.status = status;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	public String getOrginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public String getRenameFileName() {
		return renameFileName;
	}

	public void setRenameFileName(String renameFileName) {
		this.renameFileName = renameFileName;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Attachment [no=" + no + ", boardNo=" + boardNo + ", originalFileName=" + originalFileName
				+ ", renameFileName=" + renameFileName + ", status=" + status + "]";
	}
	
	
	
}
