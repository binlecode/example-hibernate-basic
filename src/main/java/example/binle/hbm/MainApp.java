package example.binle.hbm;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 */
public class MainApp {
    public static void main(String[] args) {

        // ** create session factory with cfg xml **
        System.out.println("** demo session factory with cfg.xml");

        SessionFactory sessionFactoryWithCfgXml = HibernateUtilWithCfgXml.getSessionFactory();

        dbCheck(sessionFactoryWithCfgXml);

        CompanyDataService cds = new CompanyDataService(sessionFactoryWithCfgXml);
        cds.saveCompany();

        EmployeeDataService eds = new EmployeeDataService(sessionFactoryWithCfgXml);
        eds.saveEmployee();
        eds.queryEmployee();

        // explicitly created hibernate service registry should be shutdown explicitly
        HibernateUtilWithCfgXml.shutdown();


        // ** create session factory without cfg xml **
        System.out.println("** demo session factory with no cfg.xml");

        SessionFactory sessionFactoryWithNoCfgXml = HibernateUtilNoCfgXml.getSessionFactory();

        dbCheck(sessionFactoryWithNoCfgXml);

        CompanyDataService cds2 = new CompanyDataService(sessionFactoryWithNoCfgXml);
        cds2.saveCompany();

        EmployeeDataService eds2 = new EmployeeDataService(sessionFactoryWithNoCfgXml);
        eds2.saveEmployee();
        eds2.queryEmployee();

        // explicitly created hibernate registry should be shutdown explicitly
        HibernateUtilNoCfgXml.shutdown();


        // ** create session factory with DataSource configuration
        System.out.println("** demo session factory with custom connection pool");

        SessionFactory sessionFactoryWithDataSource = HibernateUtilCustomPool.getSessionFactory();

        dbCheck(sessionFactoryWithDataSource);

        CompanyDataService cds3 = new CompanyDataService(sessionFactoryWithDataSource);
        cds3.saveCompany();

        EmployeeDataService eds3 = new EmployeeDataService(sessionFactoryWithDataSource);
        eds3.saveEmployee();
        eds3.queryEmployee();

        // explicitly created hibernate registry should be shutdown explicitly
        HibernateUtilCustomPool.shutdown();
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
