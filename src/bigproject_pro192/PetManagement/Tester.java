package bigproject_pro192.PetManagement;

import bigproject_pro192.PetManagement.DAO.OwnerManager;
import bigproject_pro192.PetManagement.DAO.PetManager;
import bigproject_pro192.PetManagement.DAO.ServicesManager;
import bigproject_pro192.PetManagement.DTO.Owner;
import bigproject_pro192.PetManagement.DTO.Pet;
import bigproject_pro192.PetManagement.DTO.Services;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author cong.nguyen
 */
public class Tester {

    public static OwnerManager ownerManager = new OwnerManager();
    public static PetManager petManager = new PetManager();
    public static ServicesManager servicesManager = new ServicesManager();
    public static final Map<Integer, List<String>> chargeMoneyLog = new HashMap<>();

    public static void main(String[] args) {
        int choice = 0;
        while (true) {
            System.out.println("====== HEALTH HOSPITAL ======");
            System.out.println("1. Owner Management");
            System.out.println("2. Pet Management");
            System.out.println("3. Services Management");
            System.out.println("4. Exit");
            System.out.println("============================");
            try {
                System.out.print("Please choose: ");
                choice = new Scanner(System.in).nextInt();
                if (choice < 1 || choice > 4) {
                    throw new InputMismatchException();
                }
                switch (choice) {
                    case 1:
                        ownerMgt();
                        break;
                    case 2:
                        petMgt();
                        break;
                    case 3:
                        serviceMgt();
                        break;
                    case 4:
                        System.out.println("Thank you.");
                        System.exit(0);
                }
            } catch (Exception e) {
                System.err.println("Only accept from 1 to 4!");
            }
        }
    }

    public static void ownerMgt() {
        int choice = 0;
        while (true) {
            System.out.println("====== OWNER MANAGEMENT ======");
            System.out.println("1. Add a new owner");
            System.out.println("2. Update owner by id");
            System.out.println("3. Find owner by id");
            System.out.println("4. Delete owner by id");
            System.out.println("5. Add pet to owner");
            System.out.println("6. Back to menu");
            System.out.println("============================");
            try {
                System.out.print("Please choose: ");
                choice = new Scanner(System.in).nextInt();
                if (choice < 1 || choice > 6) {
                    throw new InputMismatchException();
                }
                switch (choice) {
                    case 1:
                        addOwner();
                        break;
                    case 2:
                        updateOwner();
                        break;
                    case 3:
                        findOwner();
                        break;
                    case 4:
                        deleteOwner();
                        break;
                    case 5:
                        addPetToOwner();
                        break;
                    case 6:
                        return;
                }
            } catch (Exception e) {
                System.err.println("Only accept from 1 to 6!");
            }
        }
    }

    public static void addPetToOwner() {
        Owner owner = null;
        boolean flag = false;
        while (true) {
            System.out.println("Please input owner id: ");
            String id = new Scanner(System.in).nextLine();
            owner = ownerManager.findOwnerById(id);
            if (owner == null) {
                flag = true;
                System.err.println("Owner is not found with the provided id. Try again.");
                break;
            } else if (flag == false) {
                while (true) {
                    System.out.println("Please input pet id to add: ");
                    try {
                        int petId = new Scanner(System.in).nextInt();
                        Pet p = petManager.findPetById(petId);
                        if (p != null) {
                            if (ownerManager.doesOwnerHaveThisPet(owner.getId(), petId)) {
                                System.err.println("This owner already has this pet. Try again.");
                            } else {
                                List<Pet> petList = owner.getPets();
                                petList.add(p);
                                System.out.println("Added pet to owner successfull!");
                                System.out.println("Do you want to add more pet to owner? (y/n): ");
                                String choice = new Scanner(System.in).nextLine();
                                if (choice.equals("n") || choice.equals("N")) {
                                    return;
                                }
                            }
                        } else {
                            System.err.println("Pet doesn't existed with provided id. Try again.");
                            return;
                        }
                    } catch (Exception e) {
                        System.err.println("Pet id must be in integer.");
                    }
                }
            }
        }

    }

    public static void addOwner() {
        while (true) {
            Owner owner = new Owner();

            while (true) {
                System.out.println("Please input owner id (Oxxx): ");
                String id = new Scanner(System.in).nextLine();
                if (id.trim().isEmpty()) {
                    System.err.println("Your input is Empty");
                } else if (!id.matches("^O[\\d]{2,}$")) {
                    System.err.println("Please input id in format (Oxxx)!");
                } else {
                    owner.setId(id);
                    break;
                }
            }

            while (true) {
                System.out.println("Please input owner name: ");
                String name = new Scanner(System.in).nextLine();
                if (name.trim().isEmpty()) {
                    System.err.println("Your input is Empty!");
                } else if (!name.matches("^[a-zA-Z\\s]{1,100}$")) {
                    System.err.println("Please input owner name from 1 to 100 characters"
                            + " and must in alphabet!");
                } else {
                    owner.setName(name);
                    break;
                }
            }

            while (true) {
                System.out.println("Please input owner address: ");
                String address = new Scanner(System.in).nextLine();
                if (address.trim().isEmpty()) {
                    System.err.println("Your input is Empty!");
                } else if (!address.matches("^[a-zA-Z\\s]{3,100}$")) {
                    System.err.println("Please input owner address from 3 to 100 characters"
                            + " and must in alphabet!");
                } else {
                    owner.setAddress(address);
                    break;
                }
            }
            if (ownerManager.addOwner(owner)) {
                System.out.println("Successfully added an owner.");
            } else {
                System.out.println("The owner with id " + owner.getId() + " is existed.");
            }

            System.out.println("Do you want to add more? (y/n)?");
            String choice = new Scanner(System.in).nextLine();
            if (choice.equals("n") || choice.equals("N")) {
                return;
            }
        }
    }

    public static void updateOwner() {
        System.out.println("Please input owner id: ");
        String updateId = new Scanner(System.in).nextLine();
        Owner updateOwner = ownerManager.findOwnerById(updateId);
        if (updateOwner == null) {
            System.err.println("The owner with the provided Id doesn't exist.");
            return;
        }

        while (true) {
            System.out.println("Do you want to update name? (y/n): ");
            String choice = new Scanner(System.in).nextLine();
            if (choice.equals("n") || choice.equals("N")) {
                System.out.println("You chose not to update name.");
                break;
            }

            System.out.println("Please input owner name: ");
            String name = new Scanner(System.in).nextLine();
            if (name.trim().isEmpty()) {
                System.err.println("Your input is Empty!");
            } else if (!name.matches("^[a-zA-Z\\s]{1,100}$")) {
                System.err.println("Please input owner name from 1 to 100 characters"
                        + "and must in alphabet.");
            } else {
                updateOwner.setName(name);
                break;
            }
        }

        while (true) {
            System.out.println("Do you want to update address? (y/n): ");
            String choice = new Scanner(System.in).nextLine();
            if (choice.equals("n") || choice.equals("N")) {
                System.out.println("You chose not to update address.");
                break;
            }

            System.out.println("Please input owner address: ");
            String address = new Scanner(System.in).nextLine();
            if (address.trim().isEmpty()) {
                System.err.println("Your input is Empty!");
            } else if (!address.matches("^[a-zA-Z\\s]{3,100}$")) {
                System.err.println("Please input owner address from 3 to 100 characters"
                        + "and must in alphabet.");
            } else {
                updateOwner.setAddress(address);
                break;
            }
        }

        if (ownerManager.updateOwnerById(updateOwner)) {
            System.out.println("Successfully updated the owner with id"
                    + updateOwner.getId());
        } else {
            System.out.println("The owner with id " + updateOwner.getId()
                    + " is not existed.");
        }

    }

    public static void findOwner() {
        System.out.println("Please input owner id: ");
        String id = new Scanner(System.in).nextLine();
        Owner owner = ownerManager.findOwnerById(id);
        if (owner == null) {
            System.err.println("The owner with the provided Id doesn't exist.");
            return;
        }
        System.out.println("==========");
        System.out.println("The Owner you want to find: ");
        System.out.println("Id: " + owner.getId());
        System.out.println("Name: " + owner.getName());
        System.out.println("Address: " + owner.getAddress());
        if (owner.getPets().size() > 0) {
            System.out.println("Pet List: ");
            for (int i = 0; i < owner.getPets().size(); i++) {
                System.out.println("----------");
                Pet ownerPet = owner.getPets().get(i);
                System.out.println("Pet Id: " + ownerPet.getId());
                System.out.println("Pet Name: " + ownerPet.getName());
                System.out.println("Pet Gender: " + ownerPet.getGender());
                System.out.println("Pet Birthdate: " + ownerPet.getBirthday());
                System.out.println("----------");
            }
        }

        System.out.println("==========");
    }

    public static void deleteOwner() {
        System.out.println("Please input owner id: ");
        String id = new Scanner(System.in).nextLine();
        Owner owner = ownerManager.findOwnerById(id);
        if (owner == null) {
            System.out.println("The owner with the provided Id doesn't exist.");
            return;
        }
        while (true) {
            System.out.println("Do you want to delete this owner? (y/n)?");
            String choice = new Scanner(System.in).nextLine();
            if (choice.equals("n") || choice.equals("N")) {
                return;
            }
            if (ownerManager.deleteOwnerById(id)) {
                System.out.println("Successfully deleted owner with id " + id);
                return;
            }
        }
    }

    public static void petMgt() {
        int choice = 0;
        while (true) {
            System.out.println("====== PET MANAGEMENT ======");
            System.out.println("1. Add a new pet");
            System.out.println("2. Update pet by id");
            System.out.println("3. Find pet by id");
            System.out.println("4. Delete pet by id");
            System.out.println("5. Back to menu");
            System.out.println("============================");
            try {
                System.out.print("Please choose: ");
                choice = new Scanner(System.in).nextInt();
                if (choice < 1 || choice > 5) {
                    throw new InputMismatchException();
                }
                switch (choice) {
                    case 1:
                        addPet();
                        break;
                    case 2:
                        updatePet();
                        break;
                    case 3:
                        findPet();
                        break;
                    case 4:
                        deletePet();
                        break;
                    case 5:
                        return;
                }
            } catch (Exception e) {
                System.err.println("Only accept from 1 to 5.");
            }
        }
    }

    public static void addPet() {
        while (true) {
            Pet pet = new Pet();
            while (true) {
                try {
                    System.out.println("Please input pet id (above 0): ");
                    int id = new Scanner(System.in).nextInt();
                    if (id < 1 || id > Integer.MAX_VALUE) {
                        throw new InputMismatchException();
                    }
                    pet.setId(id);
                    break;
                } catch (Exception e) {
                    System.err.println("Pet id must be a positive integer.");
                }
            }

            while (true) {
                System.out.println("Please input pet name: ");
                String name = new Scanner(System.in).nextLine();
                if (name.trim().isEmpty()) {
                    System.err.println("Your input is Empty!");
                } else if (!name.matches("^[A-Za-z\\s]{1,100}$")) {
                    System.err.println("Pet name must be in alphabet characters from 1 to 100.");
                } else {
                    pet.setName(name);
                    break;
                }
            }
            while (true) {
                System.out.println("Please input pet gender (M/F): ");
                String gender = new Scanner(System.in).nextLine();
                if (!(gender.equals("M") || gender.equals("F"))) {
                    System.err.println("Pet gender must be male(M) or female(F).");
                } else {
                    pet.setGender(gender);
                    break;
                }
            }

            while (true) {
                System.out.println("Please input pet birthdate (dd/MM/yyyy): ");
                String birthdate = new Scanner(System.in).nextLine();
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    sdf.setLenient(false);
                    sdf.parse(birthdate);
                    pet.setBirthday(birthdate);
                    break;
                } catch (ParseException e) {
                    System.err.println("Pet's birthday is Invalid!");
                }
            }

            if (petManager.addPet(pet)) {
                System.out.println("Successfully added a pet with id " + pet.getId());
            } else {
                System.out.println("Failed to add a pet");
            }
            System.out.println("Do you want to add more? (y/n)?");
            String choice = new Scanner(System.in).nextLine();
            if (choice.equals("n") || choice.equals("N")) {
                return;
            }
        }
    }

    public static void updatePet() {
        System.out.println("Please input pet id: ");
        int updateId = new Scanner(System.in).nextInt();
        Pet updatePet = petManager.findPetById(updateId);
        if (updatePet == null) {
            System.err.println("The pet with the provided Id doesn't exist.");
            return;
        }

        while (true) {
            System.out.println("Do you want to update name? (y/n): ");
            String choice = new Scanner(System.in).nextLine();
            if (choice.equals("n") || choice.equals("N")) {
                System.out.println("You chose not to update name.");
                break;
            }

            System.out.println("Please input pet name: ");
            String name = new Scanner(System.in).nextLine();
            if (name.trim().isEmpty()) {
                System.err.println("Your input is Empty!");
            } else if (!name.matches("^[a-zA-Z\\s]{1,100}$")) {
                System.err.println("Please input pet name from 1 to 100 characters"
                        + "and must in alphabet.");
            } else {
                updatePet.setName(name);
                break;
            }
        }

        while (true) {
            System.out.println("Do you want to update birthday's pet? (y/n): ");
            String choice = new Scanner(System.in).nextLine();
            if (choice.equals("n") || choice.equals("N")) {
                System.out.println("You chose not to update address.");
                break;
            }

            while (true) {
                System.out.println("Please input pet birthdate (dd/MM/yyyy): ");
                String birthdate = new Scanner(System.in).nextLine();
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    sdf.setLenient(false);
                    sdf.parse(birthdate);
                    updatePet.setBirthday(birthdate);
                    break;
                } catch (ParseException e) {
                    System.err.println("Pet's birthday is Invalid!");
                }
            }

        }
        while (true) {
            System.out.println("Do you want to update pet's gender (y/n) ?");
            String choice = new Scanner(System.in).nextLine();
            if (choice.equals("n") || choice.equals("N")) {
                System.out.println("You chose not to update pet's gender.");
                break;
            } else {
                System.out.println("Please input pet gender (M/F): ");
                String gender = new Scanner(System.in).nextLine();
                if (!(gender.equals("M") || gender.equals("F"))) {
                    System.err.println("Pet gender must be male(M) or female(F).");
                } else {
                    updatePet.setGender(gender);
                    break;
                }
            }
        }

        if (petManager.updatePetById(updatePet)) {
            System.out.println("Successfully updated the pet with id"
                    + updatePet.getId());
        } else {
            System.out.println("The pet with id " + updatePet.getId()
                    + " is not existed.");
        }
    }

    public static void findPet() {
        System.out.println("Please input pet ID: ");
        int id = new Scanner(System.in).nextInt();
        Pet pet = petManager.findPetById(id);
        if (pet == null) {
            System.out.println("The Pet with the provided Id doesn't exist.");
            return;
        }
        System.out.println("==========");
        System.out.println("The Pet you want to find: ");
        System.out.println("Id: " + pet.getId());
        System.out.println("Name: " + pet.getName());
        System.out.println("Birthday: " + pet.getBirthday());
        System.out.println("Gender: " + pet.getGender());
        System.out.println("==========");
    }

    public static void deletePet() {
        System.out.println("Please input pet id: ");
        int id = new Scanner(System.in).nextInt();
        Pet pet = petManager.findPetById(id);
        if (pet == null) {
            System.out.println("The pet with the provided Id doesn't exist.");
            return;
        }
        while (true) {
            System.out.println("Do you want to delete this pet? (y/n)?");
            String choice = new Scanner(System.in).nextLine();
            if (choice.equals("n") || choice.equals("N")) {
                return;
            }
            if (petManager.deletePetById(id)) {
                System.out.println("Successfully deleted pet with id " + id);
                return;
            }
        }
    }

    public static void serviceMgt() {
        int choice = 0;
        while (true) {
            System.out.println("====== SERVICES MANAGEMENT ======");
            System.out.println("1. Add a new service");
            System.out.println("2. Update service by id");
            System.out.println("3. Find service by id");
            System.out.println("4. Delete service by id");
            System.out.println("5. Charge money");
            System.out.println("6. Back to menu");
            System.out.println("============================");
            try {
                System.out.print("Please choose: ");
                choice = new Scanner(System.in).nextInt();
                if (choice < 1 || choice > 6) {
                    throw new InputMismatchException();
                }
                switch (choice) {
                    case 1:
                        addService();
                        break;
                    case 2:
                        updateService();
                        break;
                    case 3:
                        findService();
                        break;
                    case 4:
                        deleteService();
                        break;
                    case 5:
                        chargeMoney();
                        break;
                    case 6:
                        return;
                }
            } catch (Exception e) {
                System.err.println("Only accept from 1 to 6.");
            }
        }
    }

    public static void chargeMoney() {
        if (servicesManager.getSize() < 1) {
            System.err.println("Please add at least 1 service to do this.");
            return;
        }
        Owner o = null;
        while (true) {
            System.out.println("Please input owner id: ");
            String ownerid = new Scanner(System.in).nextLine();
            o = ownerManager.findOwnerById(ownerid);
            if (o != null) {
                break;
            } else {
                System.err.println("Owner is not found with provided id.");
            }
        }

        List<Pet> ownerPets = o.getPets();
        if (ownerPets.size() < 1) {
            System.err.println("The owner does not have any pet yet.");
            return;
        }
       
        while (true) {
            System.out.println("Please input pet id of owner: ");
            int petId = -1;
            while (true) {     
                try {
                    petId = new Scanner(System.in).nextInt();

                    break;
                } catch (Exception e) {
                    System.err.println("Pet id must be integer.");
                }
            }
            boolean flag = false;
            Pet selectedPet = null;
            for (int i = 0; i < ownerPets.size(); i++) {
                if (ownerPets.get(i).getId() == petId) {
                    flag = true;
                    selectedPet = ownerPets.get(i);
                }
            }
            if (flag == false) {
                System.err.println("This provided pet id does not exist in owner.");
                return;
            } else {
                int serviceId = -1;
                while (true) {
                    System.out.println("Please provide service id to use: ");
                    try {
                        serviceId = new Scanner(System.in).nextInt();
                        break;
                    } catch (Exception e) {
                        System.out.println("Service Id must in integer.");
                    }
                }
                Services s = servicesManager.findServiceById(serviceId);
                if (s != null) {
                    System.out.println("Do you want to charge money to this owner with serivce: ");
                    System.out.println("Service Id: " + s.getId());
                    System.out.println("Service Name: " + s.getName());
                    System.out.println("Service Price: " + s.getPrice());
                    System.out.println("Enter Y to charge, N to quit?");
                    String serviceChoice = new Scanner(System.in).nextLine();
                    if (serviceChoice.equalsIgnoreCase("y")) {
                        List<String> log = chargeMoneyLog.get(s.getId());
                        if (log == null) {
                            log = new ArrayList<>();
                        }
                        log.add("Charged owner " + o.getId() + " with pet "
                                + selectedPet.getId() + " with service " + s.getId());
                        chargeMoneyLog.put(s.getId(), log);
                        System.out.println("Successfully charge money.");
                        return;
                    } else if (serviceChoice.equalsIgnoreCase("n")) {
                        System.out.println("You chose to exit");
                        return;
                    }
                } else {
                    System.out.println("Service not found with id " + serviceId);

                }
            }
        }

    }

    public static void addService() {
        while (true) {
            Services service = new Services();
            while (true) {
                try {
                    System.out.println("Please input service id (above 0): ");
                    int id = new Scanner(System.in).nextInt();
                    if (id < 1 || id > Integer.MAX_VALUE) {
                        throw new InputMismatchException();
                    }
                    service.setId(id);
                    break;
                } catch (InputMismatchException e) {
                    System.err.println("Service id must be a positive integer.");
                } catch (Exception e) {
                    System.err.println("Invalid Input!");
                }

            }
            while (true) {
                System.out.println("Please input service name: ");
                String name = new Scanner(System.in).nextLine();
                if (name.trim().isEmpty()) {
                    System.err.println("Your input is Empty!");
                } else if (!name.matches("^[A-Za-z\\s]{1,100}$")) {
                    System.err.println("Service name must be in alphabet characters from 1 to 100.");
                } else {
                    service.setName(name);
                    break;
                }
            }

            while (true) {
                System.out.println("Please input service price: ");
                try {
                    int price = new Scanner(System.in).nextInt();
                    if (price < 1 || price > Integer.MAX_VALUE) {
                        throw new InputMismatchException();
                    } else {
                        service.setPrice(price);
                        break;
                    }
                } catch (InputMismatchException e) {
                    System.err.println("Price must be positive integer.");

                } catch (Exception e) {
                    System.err.println("Invalid Input!");
                }
            }

            if (servicesManager.addService(service)) {
                System.out.println("Successfully added a service with id " + service.getId());
            } else {
                System.out.println("Failed to add a service");
            }
            System.out.println("Do you want to add more? (y/n)?");
            String choice = new Scanner(System.in).nextLine();
            if (choice.equals("n") || choice.equals("N")) {
                return;
            }
        }
    }

    public static void updateService() {
        System.out.println("Please input service id: ");
        int updateId = new Scanner(System.in).nextInt();
        Services updateService = servicesManager.findServiceById(updateId);
        if (updateService == null) {
            System.err.println("The service with the provided Id doesn't exist.");
            return;
        }

        while (true) {
            System.out.println("Do you want to update service's name? (y/n): ");
            String choice = new Scanner(System.in).nextLine();
            if (choice.equals("n") || choice.equals("N")) {
                System.out.println("You chose not to update service's  name.");
                break;
            }

            System.out.println("Please input service's name: ");
            String name = new Scanner(System.in).nextLine();
            if (name.trim().isEmpty()) {
                System.err.println("Your input is Empty!");
            } else if (!name.matches("^[a-zA-Z\\s]{1,100}$")) {
                System.err.println("Please input services' name from 1 to 100 characters"
                        + "and must in alphabet.");
            } else {
                updateService.setName(name);
                break;
            }
        }

        while (true) {
            System.out.println("Do you want to update price's service? (y/n): ");
            String choice = new Scanner(System.in).nextLine();
            if (choice.equals("n") || choice.equals("N")) {
                System.out.println("You chose not to update price's service.");
                break;
            }
            System.out.println("Please input price's service: ");
            try {
                int price = new Scanner(System.in).nextInt();
                if (price < 1 || price > Integer.MAX_VALUE) {
                    throw new InputMismatchException();
                }
                updateService.setPrice(price);
                break;
            } catch (InputMismatchException e) {
                System.err.println("Service's price must be positive integer!");
            } catch (Exception e) {
                System.err.println("Invalid Input!");
            }

        }

        if (servicesManager.updatePetById(updateService)) {
            System.out.println("Successfully updated the service with id"
                    + updateService.getId());
        } else {
            System.out.println("The pet with id " + updateService.getId()
                    + " is not existed.");
        }
    }

    public static void findService() {
        System.out.println("Please in put service's id: ");
        int id = new Scanner(System.in).nextInt();
        Services service = servicesManager.findServiceById(id);
        if (service == null) {
            System.out.println("The service with provided Id doesn't exist.");
            return;
        }
        System.out.println("==========");
        System.out.println("The Service you want to find: ");
        System.out.println("Id: " + service.getId());
        System.out.println("Name: " + service.getName());
        System.out.println("Price: " + service.getPrice());
        List<String> log = chargeMoneyLog.get(service.getId());
        if (log != null && log.size() > 0) {
            for (int i = 0; i < log.size(); i++) {
                System.out.println(log.get(i));
            }
        }
        System.out.println("==========");

    }

    public static void deleteService() {
        System.out.println("Please input service's id: ");
        int id = new Scanner(System.in).nextInt();
        Services service = servicesManager.findServiceById(id);
        if (service == null) {
            System.out.println("The service with the provided Id doesn't exist.");
            return;
        }
        while (true) {
            System.out.println("Do you want to delete this service? (y/n)?");
            String choice = new Scanner(System.in).nextLine();
            if (choice.equals("n") || choice.equals("N")) {
                return;
            }
            if (servicesManager.deleteServiceById(id)) {
                System.out.println("Successfully deleted service with id " + id);
                return;
            }
        }
    }
}
