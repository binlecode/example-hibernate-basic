package example.binle.hbm;

import example.binle.hbm.entity.Company;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateDataService {

    // this class in real world is usually a singleton
    private static HibernateDataService instance = null;
    private HibernateDataService() {}

    // this method should be synchronized in multi-threaded calling environment like web app
    public static HibernateDataService getInstance() {
        // there is no need to synchronize the overall method body for every instance request
        if (instance == null) {
            // only synchronized the code block of instance check-n-create
            synchronized (HibernateDataService.class) {
                if (instance == null) {
                    instance = new HibernateDataService();
                }
            }
        }
        return instance;
    }

    public void saveCompany() {

        Session session = null;

        // in this example, transaction handler is obtained explicitly for manual commit/rollback control
        Transaction transaction = null;

        try {
            session = HibernateUtilWithCfgXml.getSessionFactory().openSession();
            transaction = session.getTransaction();
            transaction.begin();

            System.out.println("hibernate: checking company");
            Company company1 = session.get(Company.class, 1L);
            if (company1 != null) {
                System.out.println("hibernate: company found: " + company1);
            } else {
                System.out.println("hibernate: company not found, inserting");
                company1 = new Company();
                company1.setId(1L);
                company1.setName("Cool Company");
                session.save(company1);
                System.out.println("hibernate: company inserted: " + company1);
            }

            transaction.commit();

        } catch (Exception ex) {
            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (Exception exx) {
                    // nothing really can be done here other than error logging
                    exx.printStackTrace();
                }
            }
            ex.printStackTrace();
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (Exception ex) {
                    // nothing really can be done here other than error logging
                    ex.printStackTrace();
                }
            }
        }
    }

}
