package com.hf.test;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hf.config.jpa.DataSourceConfig;
import com.hf.config.jpa.JpaConfig;
import com.hf.config.transaction.JpaTransactionConfig;
import com.hf.entity.manytomany.Category;
import com.hf.entity.manytomany.Item;
import com.hf.entity.manytoone.Customer;
import com.hf.entity.onetoone.Department;
import com.hf.entity.onetoone.Manager;
import com.hf.repository.CategoryRepository;
import com.hf.repository.CustomerRepository;
import com.hf.repository.DepartmentRepository;
import com.hf.repository.ManagerRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataSourceConfig.class,JpaConfig.class,JpaTransactionConfig.class})
@TestPropertySource("classpath:jpa-test.conf")
public class JpaTest extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired DepartmentRepository departmentRepository;
	@Autowired ManagerRepository managerRepository;
	@Autowired EntityManager em;
	@Autowired CustomerRepository customerRepository;
	@Autowired CategoryRepository categoryRepository;
	
	@Test
	@Commit
	public void dataSourceTest() throws SQLException{
		String sql="INSERT INTO users (id, create_date, create_user, sort, update_date, update_user, email, name, password, role, username) VALUES (2, '2016-08-01 10:08:44', '1', 1, '2016-08-01 10:08:52', '1', '45@45', '陈玲', '45', 'admin', 'chengling')";
	
		jdbcTemplate.update(sql);
		throw new RuntimeException("error");	//错误未回滚
	}
	
	@Test
	public void testOneToOne1(){
		Department department=departmentRepository.findOne(0);
		System.out.println(department.getDeptName());
		System.out.println(department.getMgr().getMgrName());
	}
	
	@Test
	public void testOneToOne2(){
		Manager manager = managerRepository.findOne(0);
		//System.out.println(manager.getMgrName());
		//System.out.println(department.getMgr().getMgrName());
	}
	
	
	//jpa动态查询
	/**
	 * 实现带查询条件的分页
	 */
	@Test
	public void testJpaSpecificationExecutor() {

		int page = 0;
		int size = 2;

		// desc从大到小
		Sort sort = new Sort(new org.springframework.data.domain.Sort.Order(
				Direction.DESC, "id"));
		PageRequest pageable = new PageRequest(page, size, sort);// 第一个参数，0为第一页
		
		Specification<Department> specification = (root, query, cb) -> {
			Predicate predicate = cb.ge(root.get("id"), 1);// id大于或等于1
			return predicate;
		};

		Page<Department> pagelist = departmentRepository.findAll(specification,
				pageable);

		System.out.println("总纪录数" + pagelist.getTotalElements());
		System.out.println("当前第几页" + (pagelist.getNumber() + 1));
		System.out.println("总页数" + pagelist.getTotalPages());
		System.out.println("当前页面list" + pagelist.getContent());
		System.out.println("当前页面记录数" + pagelist.getNumberOfElements());
	}
	
	/**
	 * 测试JpaCriteria
	 */
	@Test
	public void testJpaCriteria1(){
		//原生jpa 第一种写法
		CriteriaBuilder criteriaBuilder=em.getCriteriaBuilder();
		CriteriaQuery<Department> criteriaQuery = criteriaBuilder.createQuery(Department.class);
		Root<Department> root = criteriaQuery.from(Department.class);
		Predicate predicate = criteriaBuilder.gt(root.get("id"), 1);
		criteriaQuery.where(predicate);
		TypedQuery<Department> typedQuery=em.createQuery(criteriaQuery);
		List<Department> result =typedQuery.getResultList();
		System.out.println(result.toString());
		
		//spring 封装的写法
		Specification<Department> specification = (root1, query, cb) -> {
			return cb.gt(root.get("id"), 1);
		};
		List<Department> result1 = departmentRepository.findAll(specification);
		System.out.println(result1.toString());
	}
	
	/**
	 * 测试JpaCriteria
	 */
	@Test
	public void testJpaCriteria2(){
		CriteriaBuilder criteriaBuilder=em.getCriteriaBuilder();
		CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
		Root<Customer> root = criteriaQuery.from(Customer.class);
		List<Predicate> predicatesList=new ArrayList<Predicate>();
		predicatesList.add(criteriaBuilder.or(criteriaBuilder.equal(root.get("id"), 8),criteriaBuilder.equal(root.get("age"), 60)));
		String queryString="456A";
		predicatesList.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("email")), StringUtils.upperCase(StringUtils.trim(queryString)) + "%"));
		//predicatesList里的Predicate是and关系连接 ，第一个 predicate 里面有一个or连接
		//(customer0_.id=8 or customer0_.age=60) and (upper(customer0_.email) like ?)
		criteriaQuery.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
		TypedQuery<Customer> typedQuery=em.createQuery(criteriaQuery);
		List<Customer> result =typedQuery.getResultList();
		System.out.println(result.toString());
		
		//spring的写法
		Specification<Customer> specification = (root1, query, cb) -> {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(cb.or(cb.equal(root1.get("id"), 9),cb.equal(root1.get("age"), 60)));
			predicates.add(cb.like(cb.upper(root1.get("email")), "%"+StringUtils.upperCase(StringUtils.trim("456A")) + "%"));
			
            if (predicates.size() > 0) {
            	// 将所有条件用 and 联合起来  
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));  
            }
			return cb.conjunction();
		};
		List<Customer> list = customerRepository.findAll(specification);
		System.out.println(list);
	}
	
	/**
	 * 	连接查询(默认使用 INNER JOIN)
	 */
	@Test
	public void testJpaCriteria7(){
		//EntityManager em=ctx.getBean(EntityManager.class);
		CriteriaBuilder criteriaBuilder=em.getCriteriaBuilder();
		CriteriaQuery<Category> cqcategory = criteriaBuilder.createQuery(Category.class);
		Root<Category> categoryRoot = cqcategory.from(Category.class);
		Join<Category, Item> itemjoin = categoryRoot.join("items",JoinType.LEFT);
		cqcategory.where(criteriaBuilder.equal(itemjoin.get("id"), 1));
		TypedQuery<Category> typedQuery = em.createQuery(cqcategory);
		List<Category> result =typedQuery.getResultList();
		System.out.println(result.toString());
		
		//spring的写法
		Specification<Category> specification = (root1, query, cb) -> {
			Join<Category, Item> itemjoin1 = root1.join("items");
			return cb.equal(itemjoin1.get("id"), 1);
		};
		List<Category> list = categoryRepository.findAll(specification);
		System.out.println(list);
	}

}

