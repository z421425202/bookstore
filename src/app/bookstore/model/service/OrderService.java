package app.bookstore.model.service;

import app.bookstore.model.entity.Order;

public interface OrderService {

	/**
	 * ���涩��
	 * @param order Ҫ����Ķ�������
	 */
	public void saveOrder(Order order);
}
