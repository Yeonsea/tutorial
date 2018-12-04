package cn.devmgr.tutorial.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.devmgr.tutorial.dao.TvSeriesDao;
import cn.devmgr.tutorial.pojo.TvSeries;

@Service
public class TvSeriesService {
	
	@Autowired private TvSeriesDao tvSeriesDao;
	
	public List<TvSeries> getAllTvSeries() {
		return tvSeriesDao.getAll();
	}
}
