package example.binle.hbm;

import example.binle.hbm.entity.Business;
import example.binle.hbm.entity.Company;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

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

            String companyName = "J.Crew";
            System.out.println("hibernate: checking company with name: " + companyName);
            Query qry = session.createQuery("from Company c where c.name = :company_name");
            qry.setParameter("company_name", companyName);
            List<Company> companyList = qry.list();
            if (companyList.size() > 0) {
                System.out.println("hibernate: company found, skipping insert");
            } else {
                System.out.println("hibernate: company not found, inserting");

                Business biz = new Business();
                biz.setType("Retail");
                session.save(biz);  // let session flush anytime later, but PK is got at this point
                System.out.println("hibernate: business inserted: " + biz);

                Company cmp1 = new Company();
                cmp1.setName(companyName);
                cmp1.setBusiness(biz);
                session.save(cmp1);

                System.out.println("hibernate: company inserted: " + cmp1);
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
