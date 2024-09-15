package Entidades;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import lombok.*;

import javax.persistence.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

@Entity
@Table (name = "Cliente")
public class Cliente implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "DNI", unique = true)
    private int dni;

    @Column (name = "Apellido")
    private String apellido;

    @Column (name = "Nombre")
    private String nombre;

    //--- CREAMOS LA RELACIÓN UNIDIRECCIONAL ENTRE CLIENTE Y DOMICILIO ---
    /* @OneToOne (JPA) establece el tipo de relación, es este caso es uno a uno.
    Con "cascade = CascadeType.ALL" cualquier cambio que realice en Cliente, también se verá reflejado
    en Domicilio. Es decir, si creamos un cliente y le seteamos un domicilio, se persistirán tanto el cliente
    como el domicilio. A su vez, si eliminamos el cliente, también se eliminará el domicilio asociado. */
    @OneToOne(cascade = CascadeType.ALL)
    /* @JoinColumn contendrá la clave primaria de la tabla Domicilio, la cual se comportará como clave foránea en
    la tabla Cliente. */
    @JoinColumn(name = "FK_Domicilio")
    private Domicilio domicilio;

    /* --- RELACIÓN BIDIRECCIONAL ENTRE CLIENTE Y FACTURA ---
    Recordar que en base de datos esto no existe pero en POO esto si es posible. Lo hacemos a modo conceptual.
    Creamos una lista de facturas que tendrá el cliente */
    @OneToMany(mappedBy = "cliente") //Nombre del objeto de la relación dentro de Factura
    private List<Factura> facturas = new ArrayList<Factura>();

}

