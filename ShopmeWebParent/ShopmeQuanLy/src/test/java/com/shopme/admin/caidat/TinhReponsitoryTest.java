package com.shopme.admin.caidat;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.admin.caidat.tinh.TinhReponsitory;
import com.shopme.common.entity.DatNuoc;
import com.shopme.common.entity.Tinh;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class TinhReponsitoryTest {

	@Autowired private TinhReponsitory repo;
	
	@Autowired private TestEntityManager entityManager;
	@Test
	public void testCreateStatesInIndia() {
		Integer countryId = 16;
		DatNuoc country = entityManager.find(DatNuoc.class, countryId);
		
//		Tinh state = repo.save(new Tinh("Karnataka", country));
//		Tinh state = repo.save(new Tinh("Punjab", country));
//		Tinh state = repo.save(new Tinh("Uttar Pradesh", country));
		Tinh state = repo.save(new Tinh("West Bengal", country));
		
		assertThat(state).isNotNull();
		assertThat(state.getMaTinh()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateStatesInUS() {
		Integer countryId = 17;
		DatNuoc country = entityManager.find(DatNuoc.class, countryId);
		
		Tinh state = repo.save(new Tinh("California", country));
//		Tinh state = repo.save(new Tinh("Texas", country));
//		Tinh state = repo.save(new Tinh("New York", country));
//		Tinh state = repo.save(new Tinh("Washington", country));
		
		assertThat(state).isNotNull();
		assertThat(state.getMaTinh()).isGreaterThan(0);
	}
	
	@Test
	public void testListStatesByCountry() {
		Integer countryId = 17;
		DatNuoc country = entityManager.find(DatNuoc.class, countryId);
		List<Tinh> listStates = repo.findByDatNuocOrderByTenAsc(country);
		
		listStates.forEach(System.out::println);
		
		assertThat(listStates.size()).isGreaterThan(0);
	}
	
//	@Test
//	public void testUpdateState() {
//		Integer stateId = 3;
//		String stateName = "Tamil Nadu";
//		State state = repo.findById(stateId).get();
//		
//		state.setName(stateName);
//		State updatedState = repo.save(state);
//		
//		assertThat(updatedState.getName()).isEqualTo(stateName);
//	}
//	
//	@Test
//	public void testGetState() {
//		Integer stateId = 1;
//		Optional<State> findById = repo.findById(stateId);
//		assertThat(findById.isPresent());
//	}
//	
	@Test
	public void testDeleteState() {
		Integer stateId = 20;
		repo.deleteById(stateId);

		Optional<Tinh> findById = repo.findById(stateId);
		assertThat(findById.isEmpty());		
	}
}
