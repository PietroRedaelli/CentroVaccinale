package ClientOperatoreSanitario;

import java.io.Serializable;

public class OperatoreSanitario implements Serializable {

    private static final long serialVersionUID = 1L;

    int id;

    public OperatoreSanitario(int num) {
        id = num;
    }

    public OperatoreSanitario() {
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return "OperatoreSanitario{" +
                "id=" + id +
                '}';
    }

}
