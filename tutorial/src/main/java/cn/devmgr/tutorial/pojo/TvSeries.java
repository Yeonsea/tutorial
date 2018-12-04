package cn.devmgr.tutorial.pojo;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 *基本的POJO，而且使用Bean Validation注解进行校验数据
 * @author 12091
 *
 */
public class TvSeries {
	@Null private Integer id;
	@NotNull private String name;
	@DecimalMin("1") private int seasonCount;
	
	//@Valid表示要级联校验； @Size(min=2)表示这个列表至少有2项内容，否则通不过校验
	@Valid @NotNull @Size(min=2) private List<TvCharacter> tvCharacters;
	
	//如果想用long型的timestamp表示日期，可用： @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	@JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd")
	//@Past表示只接受过去的时间，比当前时间还要晚的被认为不合格
	@Past private Date originRelease;
	
	public TvSeries() {
		
	}
	
	public TvSeries(int id, String name, int seasonCount, Date originRelease) {
		super();
		this.id = id;
		this.name = name;
		this.seasonCount = seasonCount;
		this.originRelease = originRelease;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSeasonCount() {
		return seasonCount;
	}
	public void setSeasonCount(int seasonCount) {
		this.seasonCount = seasonCount;
	}
	public Date getOriginRelease() {
		return originRelease;
	}
	public void setOriginRelease(Date originRelease) {
		this.originRelease = originRelease;
	}

	public List<TvCharacter> getTvCharacters() {
		return tvCharacters;
	}

	public void setTvCharacters(List<TvCharacter> tvCharacters) {
		this.tvCharacters = tvCharacters;
	}

	@Override
	public String toString() {
		return this.getClass().getName() + "{id=" + id + ";name=" + name + "}";
	}
	
}
