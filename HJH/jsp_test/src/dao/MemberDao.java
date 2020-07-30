package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import vo.MemberVO;

public class MemberDao {
	private MemberDao() {}
	private static MemberDao instance=new MemberDao();
	public static MemberDao getInstance() {	//하나의 인스턴스를 나눠씀
		return instance;
	}
	
	private Connection getConn() {
		Connection conn=null;
		try {
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource)envContext.lookup("jdbc/myoracle"); //데이터소스를 통해 
			conn = ds.getConnection();
			//etc.
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return conn;
	}
	
	public List<MemberVO> selectAll() {
		List<MemberVO> list=new ArrayList<MemberVO>();
		Connection conn=null;
		PreparedStatement ps=null;   //PreparedStatement == sql쿼리를 전송하기 위한 객체
		ResultSet rs=null;
		String sql="select * from member_test";
		try {
			conn=getConn();
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()) {
				MemberVO vo=new MemberVO();
				vo.setMno(rs.getInt("mno"));
				vo.setId(rs.getString("id"));
				vo.setPw(rs.getString("pw"));
				vo.setName(rs.getString("name"));
				vo.setGender(rs.getString("gender"));
				vo.setJob(rs.getString("job"));
				vo.setJoin_date(rs.getDate("join_date"));
				vo.setIntro(rs.getString("intro"));
				list.add(vo);
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			close(conn, ps, rs);
		}
		return list;
	}
	
	public void insert() {
		
	}
	
	private void close(Connection conn, PreparedStatement ps, ResultSet rs) {	
		try {//다른 메서드에서도 close가 사용되니 함수선언후 함수로사용
			if(rs!=null)rs.close();
			if(ps!=null)ps.close();
			if(conn!=null)conn.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void close(Connection conn, PreparedStatement ps) {
		try {
			if(ps!=null)ps.close();
			if(conn!=null)conn.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}