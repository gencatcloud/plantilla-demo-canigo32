package cat.gencat.plantillacanigo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Projections;

import cat.gencat.ctti.canigo.arch.persistence.jpa.querydsl.GenericPredicateBuilder;
import cat.gencat.plantillacanigo.model.Equipament;
import cat.gencat.plantillacanigo.model.QEquipament;
import cat.gencat.plantillacanigo.repository.EquipamentRepository;

@Service("equipamentService")
@Lazy
public class EquipamentService {

	@Autowired
	private EquipamentRepository repository;

	public List<Equipament> findAll() {
		return repository.findAll();
	}

	public Page<Equipament> findPaginated(final Integer page, final Integer rpp, final String sortField, final String sortDirection, final String filter) {

		final GenericPredicateBuilder<Equipament> builder = new GenericPredicateBuilder<Equipament>(Equipament.class, "equipament");
		builder.populateSearchCriteria(filter);

		Direction direction = null;

		if (sortDirection != null && !"".equals(sortDirection)) {
			if (sortDirection.equalsIgnoreCase("asc")) {
				direction = Sort.Direction.ASC;
			} else {
				direction = Sort.Direction.DESC;
			}
		}

		final Pageable pageable = new PageRequest(page - 1, rpp, direction, sortField);

		return repository.findAll(builder.build(), pageable);
	}
	
	public Page<Equipament> findPaginatedProjeccio(final Integer page, final Integer rpp, final String sortField, final String sortDirection, final String filter) {

		final GenericPredicateBuilder<Equipament> builder = new GenericPredicateBuilder<Equipament>(Equipament.class, "equipament");
		builder.populateSearchCriteria(filter);

		Direction direction = null;

		if (sortDirection != null && !"".equals(sortDirection)) {
			if (sortDirection.equalsIgnoreCase("asc")) {
				direction = Sort.Direction.ASC;
			} else {
				direction = Sort.Direction.DESC;
			}
		}

		final Pageable pageable = new PageRequest(page - 1, rpp, direction, sortField);
		
		QEquipament qequipament = QEquipament.equipament;
		
		return repository.findAll(Projections.bean(Equipament.class, qequipament.municipi ), builder.build(), pageable);
	}

	public Equipament getEquipament(final Long equipamentId) {
		return repository.findOne(equipamentId);
	}

	public Long saveEquipament(final Equipament equipament) {
		repository.save(equipament);

		return equipament.getId();
	}

	public void updateEquipament(final Equipament equipament) {
		repository.save(equipament);
	}

	public void deleteEquipament(final Long equipamentId) {
		final Equipament equipament = new Equipament(equipamentId);
		repository.delete(equipament);
	}

}
