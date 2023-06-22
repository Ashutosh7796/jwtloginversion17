package com.spring.jwt.repository;




import com.spring.jwt.entity.Dealer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DealerRepo extends JpaRepository<Dealer, Integer> {
    public Dealer findByEmail(String email);

    @Query(value = "DELETE FROM buysellcar.dealer_profile WHERE dealer_id=:dealer_id", nativeQuery = true)
    public void deleteById(int dealer_id);

    public Optional<Dealer> findById(int id);
}

