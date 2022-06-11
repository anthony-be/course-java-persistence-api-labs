package be.cocoding.jpa.exercise.dao.impl;

import be.cocoding.jpa.exercise.dao.CustomerDao;
import be.cocoding.jpa.exercise.entity.customer.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Repository("customerDao")
public class CustomerDaoImpl implements CustomerDao {

    @PersistenceContext
    private EntityManager em;

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Override
    public Customer findById(int custId) {
        return em.find(Customer.class, custId);
    }

    @Override
    public Customer findByIdNamedQuery(int custId) {
        TypedQuery<Customer> query = em.createNamedQuery("Customer.findById", Customer.class);
        query.setParameter("id", custId);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Customer createCustomer(String username, String password, String lastname, String firstname, Date birthday, String countryIsoCode) {
        Customer cust = new Customer();
        cust.setUsername(username);
        cust.setPassword(password);
        cust.setLastname(lastname);
        cust.setFirstname(firstname);
        cust.setBirthDate(birthday);
        cust.setCountry(getCountryByCode(countryIsoCode));
        em.persist(cust);
        return cust;
    }

    private Country getCountryByCode(String isoCode) {
        TypedQuery<Country> query = em.createQuery("select co from Country co where co.isoCode = :isoCode", Country.class);
        query.setParameter("isoCode", isoCode);
        return query.getSingleResult();
    }

    @Override
    public Customer changeCustomerPassword(Customer cust, String newPassword) {
        Customer managed = findById(cust.getId());
        managed.setPassword(newPassword);
        // Pas besoin d'appeler la méthode em.merge(managed)
        // Comme l'entité est Managed, elle est est surveillée par EntityManager
        // A la fin de la transaction ou au flush, EntityManager fera la synchronisation db et fera le Update en db
        return managed;
    }

    @Override
    public Payment addPaymentMethod(int custId, String providerName, String accountNbr, Date expirationDate) {
        Customer customer = findById(custId);

        Payment payment = new Payment();
        payment.setProvider(providerName);
        payment.setAccountNr(accountNbr);
        payment.setExpirationDate(expirationDate);
        payment.setCustomer(customer);
        em.persist(payment);

        customer.getPaymentMethods().add(payment);

        return payment;
    }

    @Override
    public CustomerAddress addAddress(int custId, AddressType type, String street, String number, String zipcode, String city, String countryIsoCode, String phone, String mobile) {
        Customer customer = findById(custId);

        CustomerAddress custAddr = new CustomerAddress();
        custAddr.setType(type);
        Address addr = new Address();
        addr.setStreetName(street);
        addr.setStreetNumber(number);
        addr.setZipCode(zipcode);
        addr.setCity(city);
        custAddr.setAddress(addr);
        custAddr.setCountry(getCountryByCode(countryIsoCode));
        custAddr.setPhone(phone);
        custAddr.setMobile(mobile);

        custAddr.setCustomer(customer);
        em.persist(custAddr);

        customer.getAddresses().add(custAddr);

        return custAddr;
    }

    @Override
    public void resetCustomerPassword(int custId) {
        EntityManager entityManager = emf.createEntityManager();
        // Entity Manager doit gérer lui-même la transaction.
        // Normalement, c'est Spring qui le fait avec @PersistenceContext mais pas avec @PersistenceUnit
        try {
            entityManager.getTransaction().begin();
            Customer customer = entityManager.find(Customer.class, custId);
            customer.setPassword("12345");
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Collection<Customer> findAll() {
        // On ne spécifie pas la classe à em.createQuery --> Query  (non-typé) est renvoyé
        Query query = em.createQuery("select c from Customer c");
        return query.getResultList(); // C'est une List sans generic qui est renvoyée !
    }

    @Override
    public Collection<Customer> findAll(int page, int pageSize) {
        TypedQuery<Customer> query = em.createQuery("select c from Customer c order by c.lastname", Customer.class);
        query.setFirstResult(page * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    public Customer findByUsername(String username) {
        TypedQuery<Customer> query = em.createQuery("select c from Customer c where c.username = :username", Customer.class);
        query.setParameter("username", username);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Aucun résultat trouvé --> renvoi null
        }
    }

    @Override
    public Customer findCustomerByUsernameAndPassword(String username, String pwd) {
        // Cette requête JPQL utilise la concaténation avec des variables "externes" qui n'ont pas été contrôlées/validées
        // ---> Risque TRES IMPORTANT de sécurité:  SQL Injection !!!
        String jpql = "select c from Customer c where c.username = '" + username + "' and c.password = '" + pwd + "'";
        System.out.println("### JPQL: " + jpql);

        // **** Fix ****
        // Ce fix définit la même requête mais en utilisant des binding parameters --> Plus de risque d'injection SQL
//        jpql = "select c from Customer c where c.username = :username and c.password = :password";
        // *************

        TypedQuery<Customer> query = em.createQuery(jpql, Customer.class);

        // ********* Fix ******
//        query.setParameter("username", username);
//        query.setParameter("password", pwd);
        // ********************

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Collection<Customer> findByCriteria(String username, String lastname, String firstname, Date birthDate, Date creationDate) {
        // Construction du JPQL
        String jpql = "select c from Customer c ";
        List<String> whereCriteria = new ArrayList<>();
        if(username != null){ whereCriteria.add(" c.username = :username"); }

        // upper(c.lastname) = Permet de ne pas être sensible à la casse (Case insensitive)
        // Attention: appliquer une fonction db sur une colonne empêche l'utilisation d'un index sur cette colonne.
        //  Ou alors il faut définir l'index de la même manière que l'appel à la fonction:
        //  CREATE INDEX customer_lastname_idx01 ON CUSTOMER ( UPPER(LAST_NAME) )

        // concat('%', :lastname, '%') permet de rechercher tout ou partie de la valeur dans c.lastname, avec l'opérateur like
        if(lastname != null){ whereCriteria.add(" upper(c.lastname) like concat('%', :lastname, '%')"); }
        if(firstname != null){ whereCriteria.add("upper(c.firstname) like concat('%', :firstname , '%')"); }
        if(birthDate != null){ whereCriteria.add("c.birthDate = :birthDate"); }
        if(creationDate!=null){ whereCriteria.add("c.createdOn >= :creationDate"); }
        if(!whereCriteria.isEmpty()){
            jpql += "where " + StringUtils.join(whereCriteria, " and ");
        }

        // Construction TypedQuery + Initialisation binding parameters
        TypedQuery<Customer> query = em.createQuery(jpql, Customer.class);
        if(username != null){ query.setParameter("username", username); }
        if(lastname != null){ query.setParameter("lastname", lastname.toUpperCase()); }
        if(firstname != null){ query.setParameter("firstname", firstname.toUpperCase()); }
        if(birthDate != null){
            // TemporalType.DATE = Permet de ne pas tenir compte des heures, minutes, secondes...
            query.setParameter("birthDate", birthDate, TemporalType.DATE);
        }
        if(creationDate!=null){ query.setParameter("creationDate", creationDate, TemporalType.TIMESTAMP); }

        // Execution de la query
        return query.getResultList();
    }

    @Override
    public Collection<Customer> findByCriteriaNamedQuery(String username, String lastname, String firstname, Date birthDate, Date creationDate) {
        TypedQuery<Customer> query = em.createNamedQuery("Customer.findByCriteria", Customer.class);
        query.setParameter("username", username);
        query.setParameter("lastname", StringUtils.upperCase(lastname));
        query.setParameter("firstname", StringUtils.upperCase(firstname));
        query.setParameter("birthDate", birthDate, TemporalType.DATE); // Permet de ne pas tenir compte des heures, minutes, secondes...
        query.setParameter("creationDate", creationDate, TemporalType.TIMESTAMP);
        return query.getResultList();
    }

    @Override
    public int resetPasswordInactiveCustomers() {
        Query query = em.createNamedQuery("Customer.resetPasswordInactiveCustomers");
        Date date60DaysAgo = DateUtils.addDays(new Date(), -60);
        query.setParameter("date", date60DaysAgo, TemporalType.TIMESTAMP);
        return query.executeUpdate();
    }
}
