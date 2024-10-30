package learn.model;

public class State {
    private int id;
    private String name;
    private String uspsCode;

    public State(){
    }

    public State(int id, String name, String uspsCode){
        this.name = name;
        this.uspsCode = uspsCode;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }


    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setUspsCode(String uspsCode){
        this.uspsCode = uspsCode;
    }

    public String getUspsCode(){
        return uspsCode;
    }

}
