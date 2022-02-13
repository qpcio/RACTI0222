package POJOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Board {
    //uzywane do serializacji i deserializacji danych
    private String name;
    private String id;


    @Override
    public String toString() {
        return "Board{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public Board() {
    }

    public Board(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
