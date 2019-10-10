package kw.sw.web.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kw.sw.web.dao.Dao;

@Service
public class DataService {

	@Autowired
	Dao d;
	
	public List<HashMap<String, Object>> dataSelect(String index) {
		switch(index) {
		case "0":
			return d.nickSelect();
		case "1":
			return d.timeSelect();
		}
		return null;
	}
	
	public void truncate() {
		d.truncate();
	}
}
