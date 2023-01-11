package app.repositories.Project;

import app.advSearch.SearchCriteria;
import app.enums.SearchOperation;
import app.models.Project.Project;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used for finding projects in diverse ways.
 */
public class ProjectSearchBuilder {
    private final List<SearchCriteria> params;

    public ProjectSearchBuilder() {
        this.params = new ArrayList<>();
    }

    /**
     * This method adds a search criteria to the list of search criteria.
     * @param key The key of the search criteria.
     * @param operation The operation of the search criteria.
     * @param value The value of the search criteria.
     * @return The ProjectSearchBuilder object.
     */
    public final ProjectSearchBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    /**
     * This method builds the specification.
     * @param searchCriteria The search criteria.
     * @return The specification.
     */
    public final ProjectSearchBuilder with(SearchCriteria searchCriteria) {
        params.add(searchCriteria);
        return this;
    }

    /**
     * This method builds the specification with the given search criteria.
     * @return The specification.
     */
    public Specification<Project> build() {
        if (params.size() == 0) {
            return null;
        }

        Specification<Project> result = new ProjectSpecification(params.get(0));
        for (int idx = 1; idx < params.size(); idx++) {
            SearchCriteria criteria = params.get(idx);
            result = SearchOperation.getDataOption(criteria.getDataOption()) == SearchOperation.ALL
                    ? Specification.where(result).and(new ProjectSpecification(criteria))
                    : Specification.where(result).or(new ProjectSpecification(criteria));
        }

        return result;
    }
}
