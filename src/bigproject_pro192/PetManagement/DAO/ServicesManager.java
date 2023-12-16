package bigproject_pro192.PetManagement.DAO;

import bigproject_pro192.PetManagement.DTO.Pet;
import bigproject_pro192.PetManagement.DTO.Services;
import java.util.ArrayList;
import java.util.List;

public class ServicesManager {

    private List<Services> services = new ArrayList<>();

    public boolean addService(Services s) {
        if (findServiceById(s.getId()) != null) {
            return false;
        }
        return services.add(s);
    }

    public Services findServiceById(int id) {
        for (int i = 0; i < services.size(); i++) {
            if (services.get(i).getId() == id) {
                return services.get(i);
            }
        }
        return null;
    }

    public boolean updatePetById(Services o) {
        for (int i = 0; i < services.size(); i++) {
            if (services.get(i).getId() == o.getId()) {
                services.set(i, o);
                return true;
            }
        }
        return false;
    }

    public boolean deleteServiceById(int id) {
        for (int i = 0; i < services.size(); i++) {
            if (services.get(i).getId() == id) {
                services.remove(i);
                return true;
            }
        }
        return false;
    }

    public int getSize() {
        return services.size();
    }
}
