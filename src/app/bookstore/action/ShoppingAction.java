package app.bookstore.action;

import java.util.Map;
import com.opensymphony.xwork2.ActionContext;
import app.bookstore.model.entity.Book;
import app.bookstore.model.entity.Order;
import app.bookstore.model.entity.OrderItem;
import app.bookstore.model.entity.User;
import app.bookstore.model.service.BookService;
import app.bookstore.model.service.OrderService;
import app.bookstore.model.service.ShoppingCart;

public class ShoppingAction extends GenericActionSupport<Order, OrderService>{
	
	private static final long serialVersionUID = 1L;
	
	private long[] bookId;
	private int[] quantity;
	
	private BookService bookService;
	
	public long[] getBookId() {
		return bookId;
	}

	public void setBookId(long[] bookId) {
		this.bookId = bookId;
	}

	public int[] getQuantity() {
		return quantity;
	}

	public void setQuantity(int[] quantity) {
		this.quantity = quantity;
	}
		
	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}
	
	/**
	 * 	���ͼ�鵽���ﳵ
	 * @return ��ӳɹ����߼���ͼ����
	 * @throws Exception ���ܻ��׳��쳣
	 */
	public String addToCart() throws Exception {
		Book book = bookService.getBook(this.getBookId()[0]);
		Map<String, Object> session = ActionContext.getContext().getSession();
		ShoppingCart cart = (ShoppingCart) session.get("cart");
		if (cart == null) {
			cart = new ShoppingCart();
		}
		cart.addBook(book, this.getQuantity()[0]);
		session.put("cart", cart);
		return SUCCESS;
	}
	
	/**
	 * ���¹��ﳵ
	 * @return ���³ɹ�����߼���ͼ����
	 * @throws Exception ���ܻ��׳��쳣
	 */
	public String updateCart() throws Exception {
		Map<String, Object> session = ActionContext.getContext().getSession();
		ShoppingCart cart = (ShoppingCart) session.get("cart");
		
		for(int i = 0; i < getBookId().length; i++)
		{
			cart.updateQuantity(getBookId()[i], getQuantity()[i]);
		}
		
		return SUCCESS;
	}
	
	/**
	 * �ӹ��ﳵ��ɾ��ָ��Id��ͼ��
	 * @return ɾ���ɹ�����߼���ͼ����
	 * @throws Exception ���ܻ��׳����쳣
	 */
	public String removeFromCart() throws Exception {
		Map<String, Object> session = ActionContext.getContext().getSession();
		ShoppingCart cart = (ShoppingCart) session.get("cart");
		cart.removeBook(getBookId()[0]);
		
		if (cart.getOrderItems().size() == 0) {
			session.remove("cart");
		}
		
		return SUCCESS;
	}
	
	/**
	 * ����
	 * @return ���˳ɹ���ʧ�ܵ��߼���ͼ����
	 * @throws Exception ���ܻ��׳����쳣
	 */
	public String checkout() throws Exception {
		Map<String, Object> session = ActionContext.getContext().getSession();
		User user = (User) session.get("user");
		ShoppingCart cart = (ShoppingCart) session.get("cart");
		
		if (user == null) {
			this.addActionMessage("Ҫ���㣬���ȵ�¼!");
			return INPUT;
		}
		if (cart == null) {
			this.addActionError("���ﳵΪ�գ��޷����㣡");
			return ERROR;
		}
		
		doCheckout(user, cart);
		session.remove("cart");
		return SUCCESS;
	}

	private void doCheckout(User user, ShoppingCart cart) {
		Order order = new Order();
		
		order.setUser(user);
		user.addOrder(order);
		
		for(OrderItem item: cart.getOrderItems()){
			item.setOrder(order);
			order.addOrderItem(item);
		}
		order.setTotalPrice(cart.getTotalPrice());
		this.getService().saveOrder(order);
		this.setModel(order);
		cart.clear();
	}
	
}
