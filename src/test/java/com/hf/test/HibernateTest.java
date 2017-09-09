package com.hf.test;


import com.hf.config.jpa.DataSourceConfig;
import com.hf.config.orm.HibernateConfig;
import com.hf.config.transaction.HibernateTransactionConfig;
import com.hf.utils.core.MapUtils;
import com.hf.utils.reflect.Reflections;
import com.hibernate.manytoone.Customer;
import com.hibernate.manytoone.Order;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataSourceConfig.class, HibernateConfig.class, HibernateTransactionConfig.class})
@TestPropertySource({"classpath:jpa-test.conf"})
@Commit
public class HibernateTest {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Test
	public void test1() throws Exception {
		Session session = sessionFactory.openSession();
		boolean open = session.isOpen();
		Criteria criteria = session.createCriteria(Customer.class);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.groupProperty("sex"));
		projectionList.add(Projections.rowCount());

		criteria.setProjection(projectionList);//select this_.customer_sex as y0_, count(*) as y1_ from hb_manytoone_customers this_ group by this_.customer_sex
		List list = criteria.list();

		for(Iterator it = list.iterator(); it.hasNext(); ) {
			Object[] o = (Object[]) it.next();//一行里面。是一个object数组 每一列，都是一个object
			//Object fieldValue = Reflections.getFieldValue(o[0], "1");
			//System.out.println(fieldValue);
		}
	}

	@Test
	public void test2() throws SQLException{
		Session session = sessionFactory.openSession();
		boolean open = session.isOpen();
		Criteria criteria = session.createCriteria(Order.class);
		List list = criteria.list();

		for(Iterator it = list.iterator(); it.hasNext(); ) {
			Order order = (Order) it.next();
			System.out.println(order.toString());
		}
		Connection connection = dataSource.getConnection();
		System.out.println(connection.getClientInfo());
	}

	@Test
	public void test3() throws SQLException{
		Session session = sessionFactory.openSession();
		Order order = new Order();
		order.setOrderId(3);
		//Transaction transaction = session.getTransaction();
		//transaction.begin();
		session.delete(order);
		//transaction.commit();
	}

}

