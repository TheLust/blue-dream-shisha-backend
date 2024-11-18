package ro.bluedreamshisha.backend.repository.i18n;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ro.bluedreamshisha.backend.model.i18n.Translation;

import java.util.ArrayList;
import java.util.List;

public class CriteriaQueryI18nRepositoryImpl implements CriteriaQueryI18nRepository {

    private final EntityManager entityManager;

    public CriteriaQueryI18nRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Translation> findAllByLanguageAndPrefixList(String language, List<String> prefixList) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Translation> query = cb.createQuery(Translation.class);
        Root<Translation> root = query.from(Translation.class);

        Predicate languagePredicate = cb.equal(root.get("language"), language);
        List<Predicate> prefixPredicates = new ArrayList<>();
        for (String prefix : prefixList) {
            prefixPredicates.add(cb.like(root.get("code"), prefix + "%"));
        }

        Predicate combinedPrefixPredicate = cb.or(prefixPredicates.toArray(new Predicate[0]));

        query.where(cb.and(languagePredicate, combinedPrefixPredicate));

        return entityManager.createQuery(query).getResultList();
    }
}
