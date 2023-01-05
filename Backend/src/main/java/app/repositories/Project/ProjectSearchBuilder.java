package app.repositories.Project;

import app.advSearch.SearchCriteria;
import app.enums.SearchOperation;
import app.models.Project.Project;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProjectSearchBuilder {
    private final List<SearchCriteria> params;

    public ProjectSearchBuilder() {
        this.params = new ArrayList<>();
    }

    public final ProjectSearchBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public final ProjectSearchBuilder with(SearchCriteria searchCriteria) {
        params.add(searchCriteria);
        return this;
    }

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
