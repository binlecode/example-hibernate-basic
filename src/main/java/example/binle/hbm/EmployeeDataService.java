package example.binle.hbm;

import example.binle.hbm.entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.function.Function;

public class EmployeeDataService {

    private SessionFactory sessionFactory;

    // this class in real world is usually a singleton
    public EmployeeDataService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveEmployee() {
        doWithSession((session -> { saveEmployeeData(session); return null; }));
    }

    public void queryEmployee() {
        doWithSession((session -> {
            List<Employee> employeeList = session.createQuery("from Employee").list();
            for (Employee e : employeeList) {
                System.out.println("employee: " + e);
            }
            return null;
        }));
    }

    protected void saveEmployeeData(Session session) {
        int id = 1;

        Employee emp = session.get(Employee.class, id);
        if (emp != null) {
            System.out.println("Employee found: " + emp);
        } else {
            System.out.println("Employee not found, inserting");
            emp = new Employee();
            emp.setId(id);
            emp.setFirstName("Foo");
            emp.setLastName(("Bar"));
            session.save(emp);
            System.out.println("Employee inserted: " + emp);
        }
    }

    protected void doWithSession(Function<Session, Object> taskInSession) {
        Transaction transaction = null;
        // use try with resource for auto close
        try (Session session = sessionFactory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            taskInSession.apply(session);
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
        }
    }

}
