package example.binle.hbm;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 */
public class MainApp {
    public static void main(String[] args) {

        dbCheck(HibernateUtilWithCfgXml.getSessionFactory());

        CompanyDataService hds = new CompanyDataService(
                HibernateUtilWithCfgXml.getSessionFactory());
        hds.saveCompany();

        EmployeeDataService eds = new EmployeeDataService(
                HibernateUtilWithCfgXml.getSessionFactory());
        eds.saveEmployee();
        eds.queryEmployee();

        // explicitly created hibernate registry should be shutdown explicitly
        HibernateUtilWithCfgXml.shutdown();

        // ** create session factory without cfg xml **

        dbCheck(HibernateUtilNoCfgXml.getSessionFactory());

        CompanyDataService hds2 = new CompanyDataService(
                HibernateUtilNoCfgXml.getSessionFactory());
        hds2.saveCompany();

        EmployeeDataService eds2 = new EmployeeDataService(
                HibernateUtilNoCfgXml.getSessionFactory());
        eds2.saveEmployee();
        eds2.queryEmployee();

        // explicitly created hibernate registry should be shutdown explicitly
        HibernateUtilNoCfgXml.shutdown();
    }

    protected static void dbCheck(SessionFactory sf) {
        Session session = sf.openSession();
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
    }

}
