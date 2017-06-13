package app.bookstore.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public final class HibernateHelper {

	private static SessionFactory sf;
	
	/*@SuppressWarnings("deprecation")*/
	public static SessionFactory getSessionFactory() {
		if (sf == null) {
			try 
			{
				Configuration cfg = new Configuration();
				cfg.configure();
				ServiceRegistry sr = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();
				sf = cfg.buildSessionFactory(sr); 
				/*sf = cfg.configure().buildSessionFactory();*/
			}
			catch (Throwable ex) 
			{
				System.err.println("Initialize Session Factory Failed:" + ex);
				throw new ExceptionInInitializerError(ex);
			}
		}
		return sf;
	}
}
