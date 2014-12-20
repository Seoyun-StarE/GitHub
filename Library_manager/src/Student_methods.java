import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Student_methods {
	private Connection dc;

	public Student_methods() {
		try {
			Class.forName("org.gjt.mm.mysql.Driver");
		} catch (ClassNotFoundException ee) {
			return;
		}
		String url = "jdbc:mysql://localhost:3306/java";
		String id = "root";
		String pass = "1234";
		try {
			dc = DriverManager.getConnection(url, id, pass);
		} catch (SQLException ee) {
		}
	}

	// ȸ������_DB
	public boolean registerMember(String id, String pass, String name,
			String department) {
		String query = "insert into Library_Member values (?, ?, ?, ?)";

		try {
			PreparedStatement pstmt = dc.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, pass);
			pstmt.setString(3, name);
			pstmt.setString(4, department);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException ee) {
			System.err.println("ȸ�� ���� ����: " + ee.toString());
			return false;
		}
		return true;
	}
	
	//���̵� �ߺ�Ȯ��
	public Boolean idValid(String id){
		String query = "select * from Library_Member where id = ?";

		try {
			PreparedStatement pstmt = dc.prepareStatement(query);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();

			rs.last();
			int nRecord = rs.getRow();
			rs.beforeFirst();
			pstmt.close();
			if(nRecord==1){				
				return false;
			}
			else{
				//System.err.println("���� ������ ���̵��Դϴ�.");
				return true;
			}
			
			
			
			
		} catch (SQLException ee ) {
			System.err.println("���� ���� �� ������ �߻��Ͽ����ϴ�.");
		}

		return false;
	}


	// �л� �α���_DB
	public boolean loginMember(String id, String pass) {
		String query = "select * from Library_Member where id = ? and password = ?";

		try {
			PreparedStatement pstmt = dc.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, pass);
			ResultSet rs = pstmt.executeQuery();
			if (!rs.next()) {
				rs.close();
				pstmt.close();
				return false;
			}
			rs.close();
			pstmt.close();
		} catch (SQLException ee) {
			System.err.println("login ó�� ����");
		}
		return true;
	}




	//�뿩 ���� ��ȸ 
	public String checkRent(String id){
		String query = "select * from Booklist where RentBy = ?";
		int ISBN = -1;

		try {
			PreparedStatement pstmt = dc.prepareStatement(query);	
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			rs.last();
			int nRecord = rs.getRow();
			rs.beforeFirst();
			
			if(nRecord==0)
				System.out.println("�뿩 ���� ������ �����ϴ�.");
			else
				System.out.println("\nISBN \t  title \t\t author \t publisher \t " );
			
			while(rs.next()){
				String title = rs.getString(2);
				String author = rs.getString(3);
				String publisher = rs.getString(4);
				ISBN = rs.getInt(5);

				System.out.println(ISBN + " \t " + title+ " \t\t " + author + " \t " + publisher + " \t " );					
			}

			pstmt.close();
			
			
		} catch (SQLException ee ) {
			System.err.println("�뿩 ���� ��ȸ �� ������ �߻��Ͽ����ϴ�.");
		}

		return null;
	}
	
	//�뿩 ���� ���� ���� ���� ���� ���� �� ���
	public String numRent(String id){
		
		int nRecord = numRentRecord(id);
		int bound = 7 - nRecord;
			
		if(nRecord==0)
			System.out.println("�뿩 ���� ���� �� : 7" );
		else
			System.out.println("�뿩 ���� ���� ��: "+ nRecord + "\t\t �뿩 ���� ���� �� : " + bound);
		System.out.println("(�л� 1���� �ִ� 7���� ������ �뿩�Ҽ� ����.)\n");
			
		return null;
	}
	
	public int numRentRecord(String id){
		String query = "select * from Booklist where RentBy = ?";
		int nRecord = 0;
		try {
			PreparedStatement pstmt = dc.prepareStatement(query);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();

			rs.last();
			nRecord = rs.getRow();
			rs.beforeFirst();
			pstmt.close();
			
			return nRecord;
			
		} catch (SQLException ee ) {
			System.err.println("�뿩 ���� ��ȸ �� ������ �߻��Ͽ����ϴ�.");
		}
		return nRecord;
	}

	// ���� �˻�
	public String searchBook(String substr_book) {
		String query = "select * from Booklist where title like ? ORDER BY ISBN ASC";

		try {
			PreparedStatement pstmt = dc.prepareStatement(query);	
			pstmt.setString(1, "%" + substr_book + "%");
			ResultSet rs = pstmt.executeQuery();

			rs.last();
			int nRecord = rs.getRow();
			rs.beforeFirst();

			if(nRecord==0)
				System.out.println("�˻� ����� �����ϴ�.");
			else
				System.out.println("\nISBN \t  title \t\t author \t publisher \t availability " );

			while(rs.next()){
				String title = rs.getString(2);
				String author = rs.getString(3);
				String publisher = rs.getString(4);
				int ISBN = rs.getInt(5);
				String availability = rs.getString(6);
				System.out.println(ISBN + " \t " + title+ " \t\t " + author + " \t " + publisher + " \t " + availability);					
			}

			pstmt.close();
		} catch (SQLException ee ) {
			System.err.println("���� �˻� �� ������ �߻��Ͽ����ϴ�.");
		}

		return null;
	}
	



}
