package main.dao;

import java.util.List;

import main.entity.Product;
import main.entity.Tag;

public interface TagDAO {
	
	public List<Tag> getTagList();
	
	public List<Tag> getTagListWithProducts();
	
	public void saveTag(Tag tag);
	
	public Tag getTag(int id);
	
	public void deleteTag(int id);
	
	public List<Tag> getTagsById(List<Integer> ids);
}
