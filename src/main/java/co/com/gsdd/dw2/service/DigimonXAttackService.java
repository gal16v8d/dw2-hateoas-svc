package co.com.gsdd.dw2.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import co.com.gsdd.dw2.converter.GenericConverter;
import co.com.gsdd.dw2.model.hateoas.DigimonXAttackModel;
import co.com.gsdd.dw2.persistence.entities.DigimonXAttack;
import co.com.gsdd.dw2.repository.DigimonRepository;
import co.com.gsdd.dw2.repository.DigimonXAttackRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DigimonXAttackService {

	private final DigimonRepository digimonRepository;
	private final DigimonXAttackRepository digimonXAttackRepository;
	private final GenericConverter<DigimonXAttack, DigimonXAttackModel> digimonXAttackConverter;

	private DigimonXAttackModel getFromData(Long digimonId, Long attackId) {
		return DigimonXAttackModel.builder().attackId(attackId).digimonId(digimonId).build();
	}

	public List<DigimonXAttackModel> getAllAtk(Long digimonId) {
		return Optional.ofNullable(digimonId).map(digimonRepository::findById).orElseGet(Optional::empty)
				.map(digimonXAttackRepository::findByDigimon).orElseGet(Collections::emptyList).stream()
				.map(digimonXAttackConverter::convertToDomain).collect(Collectors.toList());
	}

	public DigimonXAttackModel getById(Long digimonId, Long attackId) {
		DigimonXAttack dxa = digimonXAttackConverter
				.convertToEntity(DigimonXAttackModel.builder().attackId(attackId).digimonId(digimonId).build());
		return Optional.ofNullable(dxa).map(digimonXAttackConverter::convertToDomain).orElse(null);
	}

	public DigimonXAttackModel associate(Long digimonId, Long attackId) {
		return Optional.ofNullable(getFromData(digimonId, attackId)).map(digimonXAttackConverter::convertToEntity)
				.map(digimonXAttackRepository::saveAndFlush).map(digimonXAttackConverter::convertToDomain).orElse(null);
	}

	public Long deassociate(Long digimonId, Long attackId) {
		DigimonXAttack dxa = digimonXAttackConverter
				.convertToEntity(DigimonXAttackModel.builder().attackId(attackId).digimonId(digimonId).build());
		return Optional.ofNullable(dxa).map(
				d -> digimonXAttackRepository.findByDigimonAndAttack(d.getId().getDigimon(), d.getId().getAttack()))
				.map((DigimonXAttack ndxa) -> {
					digimonXAttackRepository.delete(ndxa);
					return attackId;
				}).orElse(null);

	}

}