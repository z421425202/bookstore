package app.bookstore.model.service;

import app.bookstore.model.entity.User;

public interface UserService  {

	/**
	 * �洢���û�
	 * @param user Ҫ�洢���û�����
	 */
	public void saveUser(User user);
	
	/**
	 * ��ָ֤����¼Id��������û�����
	 * @param loginId �û��ĵ�¼Id
	 * @param password �û�����
	 * @return ��֤�ɹ�������ָ�����û����󣬷���Ϊ��
	 */
	public User verifyUser(String loginId, String password);
	
}
