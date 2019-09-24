package kw.sw.web.dao;


import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kw.sw.web.beans.ListBean;
import kw.sw.web.beans.joinBean;


@Repository
public class Dao {
	
	@Autowired
	SqlSession session;
	
	
	public void join(joinBean joBean) {
		System.out.println(session.insert("sql.join", joBean));
	}
	
	public HashMap<String, Object> checkId(joinBean joBean) {
		return session.selectOne("sql.idC", joBean);
	}
	
	public List<ListBean> select(HashMap<String, Object> params) {
		System.out.println("select");
		return session.selectList("sql.list", params);
	}
	
	
	public void insert(ListBean lBean) {
		session.insert("sql.insert", lBean);
	}
	
	public int update(ListBean lBean) {
		return session.update("sql.update", lBean);
	}
	
	public int delete(ListBean lBean) {
		return session.update("sql.delete", lBean);
	}
}
