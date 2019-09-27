package kw.sw.web.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kw.sw.web.beans.ListBean;
import kw.sw.web.dao.Dao;

@Service
public class ListService {
	
	@Autowired
	Dao d;
	
	public List<ListBean> select(HashMap<String, Object> params) {
		List<ListBean> result = d.select(params);
		System.out.println(result.size());
		return result;
	}
	
	public int ud(HashMap<String, Object> params) {
		System.out.println(params.toString());
		String type = (String) params.get("type");
		ListBean lbean = (ListBean) params.get("lbean");
		System.out.println(d.update(lbean));
		System.out.println(type);
		switch(type) {
		case "3":
			return d.update(lbean);
		case "2":
			return d.delete(lbean);
		default:
			return 0;
		}
	}
}
