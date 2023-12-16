package bigproject_pro192.PetManagement.DTO;

import java.util.ArrayList;
import java.util.List;

public class Owner {

    private String id;
    private String name;
    private String address;
    private List<Pet> pets;

    public Owner() {
        this.pets = new ArrayList<>();//*
    }

    public Owner(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.pets = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

}
