package cat.gencat.plantillacanigo.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cat.gencat.ctti.canigo.arch.web.rs.response.ResponsePage;
import io.swagger.annotations.ApiParam;
import cat.gencat.plantillacanigo.model.Equipament;
import cat.gencat.plantillacanigo.service.EquipamentService;

@RestController
@RequestMapping("/equipaments")
public class EquipamentServiceController {

	@Autowired
	EquipamentService equipamentService;

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponsePage<Equipament> findPaginated(
			@ApiParam(value = "Nombre de p&agrave;gines")@RequestParam(defaultValue = "1", value = "page", required = false) final Integer page,
			@ApiParam(value = "Valors per p&agrave;gina")@RequestParam(defaultValue = "10", value = "rpp", required = false) final Integer rpp,
			@ApiParam(value = "Camp Ordenaci&oacute;")@RequestParam(defaultValue = "id", value = "sortField", required = false) final String sortField,
			@ApiParam(value = "Ordre d'ordenaci&oacute (asc/desc)")@RequestParam(defaultValue = "asc", value = "sortDirection", required = false) final String sortDirection,
			@ApiParam(value = "Filtre Ex(Municipi:Cambrils)")@RequestParam(value = "filter", required = false) final String filter) {
		
		final Page<Equipament> equipaments = equipamentService.findPaginated(page, rpp, sortField, sortDirection, filter);

		return new ResponsePage<Equipament>(equipaments.getTotalElements(), equipaments.getSize(), equipaments.getNumberOfElements(), equipaments.getContent());
	}
	
	@GetMapping(value = "/projeccio", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponsePage<Equipament> findPaginatedProjeccio(
			@ApiParam(value = "Nombre de p&agrave;gines")@RequestParam(defaultValue = "1", value = "page", required = false) final Integer page,
			@ApiParam(value = "Valors per p&agrave;gina")@RequestParam(defaultValue = "10", value = "rpp", required = false) final Integer rpp,
			@ApiParam(value = "Camp Ordenaci&oacute;")@RequestParam(defaultValue = "id", value = "sortField", required = false) final String sortField,
			@ApiParam(value = "Ordre d'ordenaci&oacute (asc/desc)")@RequestParam(defaultValue = "asc", value = "sortDirection", required = false) final String sortDirection,
			@ApiParam(value = "Filtre Ex(Municipi:Cambrils)")@RequestParam(value = "filter", required = false) final String filter) {
		
		final Page<Equipament> equipaments = equipamentService.findPaginatedProjeccio(page, rpp, sortField, sortDirection, filter);

		return new ResponsePage<Equipament>(equipaments.getTotalElements(), equipaments.getSize(), equipaments.getNumberOfElements(), equipaments.getContent());
	}

	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public Equipament getEquipament(@PathVariable("id") final Long equipamentId) {
		return equipamentService.getEquipament(equipamentId);
	}

	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveEquipament(@RequestBody final Equipament equipament) throws Exception {
		equipamentService.saveEquipament(equipament);
	}

	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, headers = "content-type=application/x-www-form-urlencoded")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@ResponseStatus(HttpStatus.CREATED)
	public void saveEquipamentFromForm(@ModelAttribute final Equipament equipament) throws Exception {
		equipamentService.saveEquipament(equipament);
	}

	@PutMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateEquipament(@RequestBody final Equipament equipament) throws Exception {
		equipamentService.updateEquipament(equipament);
	}

	@DeleteMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteEquipament(@PathVariable("id") final Long equipamentId) throws Exception {
		equipamentService.deleteEquipament(equipamentId);
	}

}
