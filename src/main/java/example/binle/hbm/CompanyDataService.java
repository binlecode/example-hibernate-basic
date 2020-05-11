package example.binle.hbm;

import example.binle.hbm.entity.Company;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class CompanyDataService {

    private SessionFactory sessionFactory;

    public CompanyDataService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveCompany() {

        Session session = null;

        // in this example, transaction handler is obtained explicitly for manual commit/rollback control
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
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
