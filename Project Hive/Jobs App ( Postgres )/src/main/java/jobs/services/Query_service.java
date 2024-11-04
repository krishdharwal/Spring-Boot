package jobs.services;

import jobs.pojo.company_pojo;
import jobs.pojo.job_pojo;
import jobs.pojo.reviews_pojo;
import jobs.repo.company_repo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
public class Query_service {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private company_repo companyRepo;


//   Specification<company_pojo> Specification_findByCompanyName(String name){
//        return (root, query, criteriaBuilder) -> {
//            return criteriaBuilder.equal(root.get("company"),name);
//        };
//
//    }

    public List<company_pojo> findByCompanyName(String name){
      CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
      CriteriaQuery<company_pojo> query = criteriaBuilder.createQuery(company_pojo.class);

        Root<company_pojo> root = query.from(company_pojo.class);
        Predicate predicate = criteriaBuilder.like(root.get("company"),name);

        // Apply
        query.select(root).where(predicate);
        return entityManager.createQuery(query).getResultList();
        }
//
//
//    public List<company_pojo> ShowAllCompanyOfType(String type){
//        Query query = new Query();
//        query.addCriteria(Criteria.where("type").is(type));
//        return mongoTemplate.find(query,company_pojo.class);
//    }
//
//    // review queries
//    public List<reviews_pojo> ReviewOfCompany(String name){
//        Query query = new Query();
//         query.addCriteria(Criteria.where("companyName").is(name));
//        List<company_pojo> company = mongoTemplate.find(query,company_pojo.class);
//        return company.get(0).getReviewList();
//    }
}
