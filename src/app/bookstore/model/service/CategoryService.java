package app.bookstore.model.service;

import java.util.List;
import app.bookstore.model.entity.Category;

public interface CategoryService {

	/**
	 * �������е�ͼ�����
	 * @return ͼ�������б�
	 */
	List<Category> getAllCategories();
}
