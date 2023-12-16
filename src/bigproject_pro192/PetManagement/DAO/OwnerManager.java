package bigproject_pro192.PetManagement.DAO;

import bigproject_pro192.PetManagement.DTO.Owner;
import bigproject_pro192.PetManagement.DTO.Pet;
import java.util.ArrayList;
import java.util.List;

public class OwnerManager {

    private List<Owner> owners = new ArrayList<>();

    public boolean addOwner(Owner o) {

        if (findOwnerById(o.getId()) != null) {
            return false;
        }
        return owners.add(o);
    }

    public Owner findOwnerById(String id) {
        for (int i = 0; i < owners.size(); i++) {
            if (owners.get(i).getId().equals(id)) {
                return owners.get(i);
            }
        }
        return null;
    }

    public boolean updateOwnerById(Owner o) {
        for (int i = 0; i < owners.size(); i++) {
            if (owners.get(i).getId().equals(o.getId())) {
                owners.set(i, o);
                return true;
            }
        }
        return false;
    }

    public boolean deleteOwnerById(String id) {
        for (int i = 0; i < owners.size(); i++) {
            if (owners.get(i).getId().equals(id)) {
                owners.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean doesOwnerHaveThisPet(String ownerId, int petId) {
        for (int i = 0; i < owners.size(); i++) {
            if (owners.get(i).getId().equals(ownerId)) {
                Owner owner = owners.get(i);
                List<Pet> listPet = owner.getPets();
                for (int j = 0; j < listPet.size(); j++) {
                    if (listPet.get(j).getId() == petId) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
