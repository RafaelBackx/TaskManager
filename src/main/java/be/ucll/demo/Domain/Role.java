package be.ucll.demo.Domain;

public enum Role {
    ADMIN("ADMIN"),
    USER("USER"),
    GUEST("GUEST");

    private String name;
    Role(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
}

