package be.cocoding.jpa.exercise.dao.impl;

import be.cocoding.jpa.exercise.dao.ProductDao;
import be.cocoding.jpa.exercise.entity.product.Category;
import be.cocoding.jpa.exercise.entity.product.Inventory;
import be.cocoding.jpa.exercise.entity.product.Product;
import be.cocoding.jpa.exercise.entity.product.ProductStatus;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

@Repository("productDao")
public class ProductDaoImpl implements ProductDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Product findById(int productId) {
        return em.find(Product.class, productId);
    }

    @Override
    public Product findByIdNamedQuery(int productId) {
        TypedQuery<Product> query = em.createNamedQuery("Product.findById", Product.class);
        query.setParameter("id", productId);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }

    @Override
    public Category addProductCategory(String name, String description) {
        Category cat = new Category();
        cat.setName(name);
        cat.setDescription(description);
        em.persist(cat);
        return cat;
    }

    @Override
    public void deleteProductCategory(Category cat) {
        Category managed = em.find(Category.class, cat.getId());
        em.remove(managed);
    }

    @Override
    public Product addProduct(String name, String description, BigDecimal price, String sku, int categoryId, int initialNumberOfItems) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setSku(sku);
        Category category = em.find(Category.class, categoryId);
        product.setCategory(category);

        Inventory inventory = new Inventory();
        inventory.setQuantity(initialNumberOfItems);
        em.persist(inventory);

        product.setInventory(inventory);
        em.persist(product);

        return product;
    }

    @Override
    public void changeProductStatus(int productId, ProductStatus status) {
        Product product = em.find(Product.class, productId);
        product.setStatus(status);
        em.merge(product); // Cet appel n'a aucun effet, comme product est déjà Managed
    }

    @Override
    public void increaseInventory(Product product, int numberOfItems) {
        Product managed = em.find(Product.class, product.getId());
        Inventory inventory = managed.getInventory();
        Integer currentQty = inventory.getQuantity();
        inventory.setQuantity(currentQty + numberOfItems);
    }

    @Override
    public void decreaseInventory(Product product, int numberOfItems) {
        Product managed = em.find(Product.class, product.getId());
        Inventory inventory = managed.getInventory();
        Integer currentQty = inventory.getQuantity();
        inventory.setQuantity(currentQty - numberOfItems);
        em.merge(managed); // Cet appel ne fait rien, comme product est déjà Managed
    }

    @Override
    public Collection<Product> findAll() {
        TypedQuery<Product> query = em.createQuery("select p from Product p", Product.class);
        return query.getResultList();
    }

    @Override
    public Collection<Product> findByCriteria(String categoryName, String name, String description,
                                              BigDecimal priceFrom, BigDecimal priceTo, Collection<ProductStatus> statuses,
                                              boolean inStock, Date createdFrom, Date createdTo) {
        TypedQuery<Product> query = em.createNamedQuery("Product.findByCriteria", Product.class);
        query.setParameter("categoryName", categoryName);
        query.setParameter("productName", StringUtils.upperCase(name));
        query.setParameter("productDescription", StringUtils.upperCase(description));
        query.setParameter("priceFrom", priceFrom);
        query.setParameter("priceTo", priceTo);
        query.setParameter("statuses", statuses);
        query.setParameter("inStock", inStock);
        query.setParameter("createdFrom", createdFrom);
        query.setParameter("createdTo", createdTo);
        return query.getResultList();
    }

    @Override
    public int deleteUnusedCategories() {
        Query query = em.createNamedQuery("Category.deleteUnusedCategories");
        Date date30DaysAgo = DateUtils.addDays(new Date(), -30);
        query.setParameter("creationDate", date30DaysAgo);
        return query.executeUpdate();
    }

    @Override
    public Collection<Product> findAllWithApi() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> prod = cq.from(Product.class);
        cq.select(prod);
        TypedQuery<Product> query = em.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public Collection<Product> findByCriteriaWithApi(String categoryName, String name, String description, BigDecimal priceFrom, BigDecimal priceTo, boolean inStock, Date createdFrom, Date createdTo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> prod = cq.from(Product.class);
        Join<Product, Category> category = prod.join("category");
        Join<Product, Inventory> inventory = prod.join("inventory");
        cq.select(prod);

        Predicate wherePredicate = cb.conjunction();

        // Category Name -> category.name = :categoryName OR :categoryName is null
        ParameterExpression<String> paramCategoryName = cb.parameter(String.class, "categoryName");
        Predicate pCategoryNameEquals = cb.equal(category.get("name"), paramCategoryName);
        Predicate pParamCategoryIsNull = cb.isNull(paramCategoryName);
        Predicate pCategoryName = cb.or(pCategoryNameEquals, pParamCategoryIsNull);
        wherePredicate = cb.and(wherePredicate, pCategoryName);

        // Product name -> upper(product.name) = :productName OR :productName is null
        ParameterExpression<String> paramProductName = cb.parameter(String.class, "productName");
        Predicate pProductNameUpperEquals = cb.equal(cb.upper(prod.get("name")), paramProductName);
        Predicate pParamProductNameIsNull = cb.isNull(paramProductName);
        Predicate pProductName = cb.or(pProductNameUpperEquals, pParamProductNameIsNull);
        wherePredicate = cb.and(wherePredicate, pProductName);

        // Product description -> upper(product.description) like concat('%',:productDescription,'%') or :productDescription is null
        ParameterExpression<String> paramProductDescription = cb.parameter(String.class, "productDescription");
        Predicate pProductDescriptionLike = cb.like(cb.upper(prod.get("description")), cb.concat(cb.concat("%", paramProductDescription), "%"));
        Predicate pParamProductDescriptionIsNull = cb.isNull(paramProductDescription);
        Predicate pProductDescription = cb.or(pProductDescriptionLike, pParamProductDescriptionIsNull);
        wherePredicate = cb.and(wherePredicate, pProductDescription);

        // priceFrom -> product.price >= :priceFrom OR :priceFrom is null
        ParameterExpression<BigDecimal> paramPriceFrom = cb.parameter(BigDecimal.class, "priceFrom");
        Predicate pPriceGreaterThan = cb.greaterThanOrEqualTo(prod.get("price"), paramPriceFrom);
        Predicate pParamPriceFromIsNull = cb.isNull(paramPriceFrom);
        Predicate pPriceFrom = cb.or(pPriceGreaterThan, pParamPriceFromIsNull);
        wherePredicate = cb.and(wherePredicate, pPriceFrom);

        // priceTo -> product.price <= :priceTo OR :priceTo is null
        ParameterExpression<BigDecimal> paramPriceTo = cb.parameter(BigDecimal.class, "priceTo");
        Predicate pPriceLowerThan = cb.lessThanOrEqualTo(prod.get("price"), paramPriceTo);
        Predicate pParamPriceToIsNull = cb.isNull(paramPriceTo);
        Predicate pPriceTo = cb.or(pPriceLowerThan, pParamPriceToIsNull);
        wherePredicate = cb.and(wherePredicate, pPriceTo);

        // inStock -> (:inStock = true AND inventory.quantity>0) OR (:inStock = false AND inventory.quantity=0)
        ParameterExpression<Boolean> paramInStock = cb.parameter(Boolean.class, "inStock");
        Predicate pInStockTrue = cb.and(cb.isTrue(paramInStock), cb.greaterThan(inventory.get("quantity"), 0));
        Predicate pInStockFalse = cb.and(cb.isFalse(paramInStock), cb.equal(inventory.get("quantity"), 0));
        Predicate pInStock = cb.or(pInStockTrue, pInStockFalse);
        wherePredicate = cb.and(wherePredicate, pInStock);

        // createdFrom -> product.createdOn >= :createdFrom OR :createdFrom is null
        ParameterExpression<Date> paramCreatedFrom = cb.parameter(Date.class, "createdFrom");
        Predicate pCreatedOnGreaterThan = cb.greaterThanOrEqualTo(prod.get("createdOn"), paramCreatedFrom);
        Predicate pParamCreatedFromIsNull = cb.isNull(paramCreatedFrom);
        Predicate pCreatedFrom = cb.or(pCreatedOnGreaterThan, pParamCreatedFromIsNull);
        wherePredicate = cb.and(wherePredicate, pCreatedFrom);

        // createdTo -> product.createdOn <= :createdTo OR :createdTo is null
        ParameterExpression<Date> paramCreatedTo = cb.parameter(Date.class, "createdTo");
        Predicate pCreatedOnLessThan = cb.lessThanOrEqualTo(prod.get("createdOn"), paramCreatedTo);
        Predicate pParamCreatedToIsNull = cb.isNull(paramCreatedTo);
        Predicate pCreatedTo = cb.or(pCreatedOnLessThan, pParamCreatedToIsNull);
        wherePredicate = cb.and(wherePredicate, pCreatedTo);

        cq.where(wherePredicate);

        TypedQuery<Product> query = em.createQuery(cq);
        query.setParameter("categoryName", categoryName);
        query.setParameter("productName", StringUtils.upperCase(name));
        query.setParameter("productDescription", StringUtils.upperCase(description));
        query.setParameter("priceFrom", priceFrom);
        query.setParameter("priceTo", priceTo);
        query.setParameter("inStock", inStock);
        query.setParameter("createdFrom", createdFrom, TemporalType.TIMESTAMP);
        query.setParameter("createdTo", createdTo, TemporalType.TIMESTAMP);
        return query.getResultList();
    }
}
