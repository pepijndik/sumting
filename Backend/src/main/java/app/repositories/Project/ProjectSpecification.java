package app.repositories.Project;

import app.advSearch.SearchCriteria;
import app.enums.SearchOperation;
import app.models.Project.Project;
import app.models.Project.ProjectType;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.Objects;
import javax.persistence.criteria.Predicate;

/**
 * This class is used to build a specification for a project.
 */
public class ProjectSpecification implements Specification<Project> {

    private final SearchCriteria searchCriteria;

    public ProjectSpecification(final SearchCriteria searchCriteria){
        super();
        this.searchCriteria = searchCriteria;
    }

    /**
     * This method builds the specification.
     * @param root The root of the project.
     * @param query The query.
     * @param cb The criteria builder.
     * @return The predicate.
     */
    @Override
    public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        String strToSearch = searchCriteria.getValue().toString().toLowerCase();

        switch (Objects.requireNonNull(SearchOperation.getSimpleOperation(searchCriteria.getOperation()))) {
            case CONTAINS -> {
                if (searchCriteria.getFilterKey().equals("typeName")) {
                    return cb.like(cb.lower(productJoin(root).get(searchCriteria.getFilterKey())), "%" + strToSearch + "%");
                }
                return cb.like(cb.lower(root.get(searchCriteria.getFilterKey())), "%" + strToSearch + "%");
            }
            case DOES_NOT_CONTAIN -> {
                if (searchCriteria.getFilterKey().equals("typeName")) {
                    return cb.notLike(cb.lower(productJoin(root).get(searchCriteria.getFilterKey())), "%" + strToSearch + "%");
                }
                return cb.notLike(cb.lower(root.get(searchCriteria.getFilterKey())), "%" + strToSearch + "%");
            }
            case BEGINS_WITH -> {
                if (searchCriteria.getFilterKey().equals("typeName")) {
                    return cb.like(cb.lower(productJoin(root).get(searchCriteria.getFilterKey())), strToSearch + "%");
                }
                return cb.like(cb.lower(root.get(searchCriteria.getFilterKey())), strToSearch + "%");
            }
            case DOES_NOT_BEGIN_WITH -> {
                if (searchCriteria.getFilterKey().equals("typeName")) {
                    return cb.notLike(cb.lower(productJoin(root).get(searchCriteria.getFilterKey())), strToSearch + "%");
                }
                return cb.notLike(cb.lower(root.get(searchCriteria.getFilterKey())), strToSearch + "%");
            }
            case ENDS_WITH -> {
                if (searchCriteria.getFilterKey().equals("typeName")) {
                    return cb.like(cb.lower(productJoin(root).get(searchCriteria.getFilterKey())), "%" + strToSearch);
                }
                return cb.like(cb.lower(root.get(searchCriteria.getFilterKey())), "%" + strToSearch);
            }
            case DOES_NOT_END_WITH -> {
                if (searchCriteria.getFilterKey().equals("typeName")) {
                    return cb.notLike(cb.lower(productJoin(root).get(searchCriteria.getFilterKey())), "%" + strToSearch);
                }
                return cb.notLike(cb.lower(root.get(searchCriteria.getFilterKey())), "%" + strToSearch);
            }
            case EQUAL -> {
                if (searchCriteria.getFilterKey().equals("typeName")) {
                    System.out.println(searchCriteria.getValue());
                    return cb.equal(productJoin(root).<String>get(searchCriteria.getFilterKey()), searchCriteria.getValue());
                }
                return cb.equal(root.get(searchCriteria.getFilterKey()), searchCriteria.getValue());
            }
            case NOT_EQUAL -> {
                if (searchCriteria.getFilterKey().equals("typeName")) {
                    return cb.notEqual(productJoin(root).<String>get(searchCriteria.getFilterKey()), searchCriteria.getValue());
                }
                return cb.notEqual(root.get(searchCriteria.getFilterKey()), searchCriteria.getValue());
            }
            case NUL -> {
                return cb.isNull(root.get(searchCriteria.getFilterKey()));
            }
            case NOT_NULL -> {
                return cb.isNotNull(root.get(searchCriteria.getFilterKey()));
            }
            case GREATER_THAN -> {
                return cb.greaterThan(root.get(searchCriteria.getFilterKey()), searchCriteria.getValue().toString());
            }
            case GREATER_THAN_EQUAL -> {
                return cb.greaterThanOrEqualTo(root.get(searchCriteria.getFilterKey()), searchCriteria.getValue().toString());
            }
            case LESS_THAN -> {
                return cb.lessThan(root.get(searchCriteria.getFilterKey()), searchCriteria.getValue().toString());
            }
            case LESS_THAN_EQUAL -> {
                return cb.lessThanOrEqualTo(root.get(searchCriteria.getFilterKey()), searchCriteria.getValue().toString());
            }
        }
        return null;
    }

    /**
     * This method joins the project with the product.
     * @param root The root of the project.
     * @return The join.
     */
    private Join<Project, ProjectType> productJoin(Root<Project> root){
        return root.join("type");

    }
}