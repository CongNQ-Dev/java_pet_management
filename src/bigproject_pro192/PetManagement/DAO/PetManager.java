package bigproject_pro192.PetManagement.DAO;

import bigproject_pro192.PetManagement.DTO.Pet;
import java.util.ArrayList;
import java.util.List;

public class PetManager {

    private List<Pet> pets = new ArrayList<>();

    public boolean addPet(Pet p) {
        if (findPetById(p.getId()) != null) {
            return false;
        }
        return pets.add(p);
    }

    public Pet findPetById(int id) {
        for (int i = 0; i < pets.size(); i++) {
            if (pets.get(i).getId() == id) {
                return pets.get(i);
            }
        }
        return null;
    }

    public boolean updatePetById(Pet o) {
        for (int i = 0; i < pets.size(); i++) {
            if (pets.get(i).getId() == o.getId()) {
                pets.set(i, o);
                return true;
            }
        }
        return false;
    }

    public boolean deletePetById(int id) {
        for (int i = 0; i < pets.size(); i++) {
            if (pets.get(i).getId() == id) {
                pets.remove(i);
                return true;
            }
        }
        return false;
    }
}
