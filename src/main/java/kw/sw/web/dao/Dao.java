package kw.sw.web.dao;


import java.util.ArrayList;
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
//		System.out.println("select");
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
	
	public void dataInsert(HashMap<String, Object> map) {
		System.out.println(map.get("nickname") + " " + map.get("date") + " " + map.get("msg"));
		session.insert("sql.dataInsert", map);
	}
	
	public List<HashMap<String, Object>> nickSelect() {
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String,Object>>();
		resultList = session.selectList("sql.nick");
		System.out.println(resultList.toString());
		return resultList;
	}
	
	public List<HashMap<String, Object>> timeSelect() {
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String,Object>>();
		resultList = session.selectList("sql.time");
		System.out.println(resultList.toString());
		return resultList;
	}
	
	public void truncate() {
		session.update("sql.truncate");
	}
}
