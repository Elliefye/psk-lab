package lt.vu.usecases;

import lombok.Getter;

import lt.vu.mybatis.dao.TagMapper;
import lt.vu.mybatis.model.Tag;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Map;

@Model
public class TagPostsMyBatis implements Serializable {
    @Inject
    private TagMapper tagMapper;

    @Getter
    private Tag tag;

    @PostConstruct
    public void init() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        int tagId = Integer.parseInt(requestParameters.get("id"));
        this.tag = tagMapper.selectByPrimaryKey(tagId);
    }
}
