package cn.devmgr.tutorial.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.devmgr.tutorial.pojo.TvSeries;
import cn.devmgr.tutorial.service.TvSeriesService;

@RestController
@RequestMapping("/tvseries")
public class TvSeriesController {
	
	private static final Log log = LogFactory.getLog(TvSeriesController.class);
	
	@Autowired TvSeriesService tvSeriesService;
	
	@GetMapping()
//	@RequestMapping(value="/1",method=RequestMethod.GET)
	public Map<String, Object> sayHello(){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("message", "Hello, world!");
		return result;
	}
	
	@GetMapping("/all")
	public List<TvSeries> getAll() {
		if(log.isTraceEnabled()) {
			log.trace("getAll();被调用了");
		}
		List<TvSeries> list = tvSeriesService.getAllTvSeries();
		if(log.isTraceEnabled()) {
			log.trace("查询获得 " + 	list.size() + " 条记录");
		}
		return list;
	}
	
	@GetMapping("/{id}")
	public TvSeries getOne(@PathVariable int id) {
		if(log.isTraceEnabled()) {
			log.trace("getOne " + id);
		}
		if(id == 101) {
			return createWestWorld();
		}else if(id == 102) {
			return createPoi();
		}else {
			throw new ResourceNotFoundException();
		}
	}
	
	/**
     * @Valid 注解表示需要验证传入的参数TvSeriesDto，需要验证的field在TvSeriesDto内通过注解定义（@NotNull, @DecimalMin等）
     * @param tvSeriesDto
     * @return
     */
    @PostMapping
    public TvSeries insertOne(@Valid @RequestBody TvSeries tvSeriesDto) {
        if(log.isTraceEnabled()) {
            log.trace("这里应该写新增tvSeriesDto到数据库的代码, 传递进来的参数是：" + tvSeriesDto);
        }
        //TODO:在数据
        tvSeriesDto.setId(9999);
        return tvSeriesDto;
    }
	
	@PutMapping("/{id}")
	public TvSeries updateOne(@PathVariable int id, @RequestBody TvSeries tvSeriesDto) {
		if(log.isTraceEnabled()) {
			log.trace("update One " + id);
		}
		if(id == 101 || id == 102) {
			// TODO
			return createPoi();
		}else {
			throw new ResourceNotFoundException();
		}
	}
	
	@DeleteMapping("/{id}")
	public Map<String, String> deleteOne(@PathVariable int id, HttpServletRequest request,
			@RequestParam(value="delete_reason", required=false) String deleteReason) throws
			Exception {
		if(log.isTraceEnabled()) {
			log.trace("delete One " + id);
		}
		Map<String, String> result = new HashMap<>();
		if(id == 101) {
			// TODO
			result.put("message", "#101被" + request.getRemoteAddr() + "删除（原因： " + deleteReason
					+ ")");
		}else if(id == 102) {
			// AccessDeniedException
			throw new RuntimeException();
		}else {
			// 不存在
			throw new ResourceNotFoundException();
		}
		return result;
	}
	
	@PostMapping(value="/{id}/photos", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public void addPhoto(@PathVariable int id, @RequestParam("photo") MultipartFile 
			imgFile) throws Exception {
		if(log.isTraceEnabled()) {
			log.trace("接收到的文件" + id + "收到文件：" + imgFile.getOriginalFilename());
		}
		//保存文件
		FileOutputStream fos = new FileOutputStream("target/" + imgFile.getOriginalFilename());
		IOUtils.copy(imgFile.getInputStream(), fos);
		fos.close();
	}
	
	@GetMapping(value="/{id}/icon", produces=MediaType.IMAGE_JPEG_VALUE)
	public byte[] getIcon(@PathVariable int id) throws Exception{
		if(log.isTraceEnabled()) {
			log.trace("getIcon(" + id + ")");
		}
		String iconFile = "src/test/resources/icon.jpg";
		InputStream is = new FileInputStream(iconFile);
		return IOUtils.toByteArray(is);
	}
	
	private TvSeries createPoi() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2016, Calendar.OCTOBER, 2, 0, 0);
		return new TvSeries(102, "WestWorld", 2, calendar.getTime());
	}
	
	private TvSeries createWestWorld() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2011, Calendar.SEPTEMBER, 22, 0, 0);
		return new TvSeries(101, "Person of Interest", 5, calendar.getTime());
	}
}
