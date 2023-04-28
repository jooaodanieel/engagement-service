package it.unibz.archlab.digidojo.engagement.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionCardRepository extends JpaRepository<ReactionCard,String> {
}
