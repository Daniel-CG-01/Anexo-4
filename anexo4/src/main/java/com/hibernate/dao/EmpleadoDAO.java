package com.hibernate.dao;

import java.util.List;
import org.hibernate.Session;
import com.hibernate.model.Empleado;
import com.hibernate.util.HibernateUtil;
import org.hibernate.query.Query;
import org.hibernate.Transaction;

public class EmpleadoDAO {
	
	//Inserción
	public void insertEmpleado(Empleado emp) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.persist(emp);
			transaction.commit();
		} catch (Exception e) {
			if (transaction!=null) {
				transaction.rollback();
			}
		}
	}
	
	//Actualización
	public void updateEmpleado(Empleado emp) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.merge(emp);
			transaction.commit();
		} catch (Exception e) {
			if (transaction!=null) {
				transaction.rollback();
			}
		}
	}
	
	//Borrado
	public void deleteEmpleado(int id) {
		Transaction transaction = null;
		Empleado emp = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			emp = session.get(Empleado.class, id);
			session.remove(emp);
			transaction.commit();
		} catch (Exception e) {
			if (transaction!=null) {
				transaction.rollback();
			}
		}
	}
	
	//Selección simple
	public Empleado selectEmpleadoById(int id) {
		Transaction transaction = null;
		Empleado emp = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			emp = session.get(Empleado.class, id);
			transaction.commit();
		} catch (Exception e) {
			if (transaction!=null) {
				transaction.rollback();
			}
		}
		return emp;
	}
	
	//Selección múltiple
	public List<Empleado> selectAllEmpleados() {
		Transaction transaction = null;
		List<Empleado> empleados = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			empleados = session.createQuery("FROM Empleado", Empleado.class).getResultList();
			transaction.commit();
		} catch (Exception e) {
			if (transaction!=null) {
				transaction.rollback();
			}
		}
		return empleados;
	}
}