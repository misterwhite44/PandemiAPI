package fr.epsi.b3devc1.msprapi.service;

import fr.epsi.b3devc1.msprapi.model.Country;
import fr.epsi.b3devc1.msprapi.model.Continent;
import fr.epsi.b3devc1.msprapi.repository.CountryRepository;
import fr.epsi.b3devc1.msprapi.repository.ContinentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {

    private final CountryRepository countryRepository;
    private final ContinentRepository continentRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository, ContinentRepository continentRepository) {
        this.countryRepository = countryRepository;
        this.continentRepository = continentRepository;
    }

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    public Optional<Country> getCountryById(Long id) {
        return countryRepository.findById(id);
    }

    public Country createCountry(Country country) {
        // Vérifie si le continent est déjà persistant (existe dans la base de données)
        if (country.getContinent() != null && country.getContinent().getId() != null) {
            Optional<Continent> existingContinent = continentRepository.findById(country.getContinent().getId());
            if (existingContinent.isPresent()) {
                // Le continent existe déjà, on le garde tel quel
                country.setContinent(existingContinent.get());
            } else {
                // Si le continent n'existe pas, on le sauvegarde
                Continent savedContinent = continentRepository.save(country.getContinent());
                country.setContinent(savedContinent); // On met à jour le continent du pays
            }
        }

        // Maintenant on sauvegarde le pays
        return countryRepository.save(country);
    }

    public void deleteCountry(Long id) {
        countryRepository.deleteById(id);
    }
}
