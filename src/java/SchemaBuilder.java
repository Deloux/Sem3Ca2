
import javax.persistence.Persistence;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alex
 */
public class SchemaBuilder {
    public static void main(String[] args) {
        Persistence.generateSchema("Sem3Ca2PU", null);
    }
}
