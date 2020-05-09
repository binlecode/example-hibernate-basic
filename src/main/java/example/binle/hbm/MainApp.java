package example.binle.hbm;

import example.binle.hbm.entity.Employee;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

/**
 */
public class MainApp {
    public static void main(String[] args) {
//        Session session = HibernateUtil.getSessionFactory().openSession();
        Session session = HibernateCfgUtil.getSessionFactory().openSession();
        session.beginTransaction();

        // Check database version
//        String sql = "select VERSION()";
//        String result = (String) session.createNativeQuery(sql).getSingleResult();
        String sql = "select 1";
        Integer result = (Integer) session.createNativeQuery(sql).getSingleResult();
        System.out.println(result);
        System.out.println("database is up and ready");

        session.getTransaction().commit();
        session.close();


        // do some meaningful transaction

        saveData();

        System.out.println("--- separation between individual transactions ---");

        queryData();


//        HibernateUtil.shutdown();
        HibernateCfgUtil.shutdown();
    }

    protected static void saveData() {
        Session session = HibernateCfgUtil.getSessionFactory().openSession();
        session.beginTransaction();

        saveEmployeeData(session);

        session.getTransaction().commit();
        session.close();
    }

    protected static void queryData() {
        Session session = HibernateCfgUtil.getSessionFactory().openSession();
        session.beginTransaction();

        queryEmployeeData(session);

        session.getTransaction().commit();
        session.close();
    }

    protected static void saveEmployeeData(Session session) {
        Employee e1 = new Employee();
        e1.setId(1);
        e1.setFirstName("Foo");
        e1.setLastName(("Bar"));
        session.save(e1);
    }

    protected static void queryEmployeeData(Session session) {
        Query query = session.createQuery("from Employee");
        List<Employee> employeeList = query.list();

        for (Employee e : employeeList) {
            System.out.println("employee: " + e);
        }
    }

}
