package Entidades;

import java.io.Serializable;

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
@Table (name = "Domicilio")
public class Domicilio implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "NombreCalle")
    private String nombreCalle;

    @Column (name = "Número")
    private int numero;

    /* --- RELACIÓN BIDIRECCIONAL ENTRE CLIENTE Y DOMICILIO ---
    Recordar que en base de datos esto no existe pero en POO esto si es posible. Lo hacemos a modo conceptual.  */
    @OneToOne(mappedBy = "domicilio") //Nombre del objeto de la relación dentro de Cliente
    private Cliente cliente;
















}
