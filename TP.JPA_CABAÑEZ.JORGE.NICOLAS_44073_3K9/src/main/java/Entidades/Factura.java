package Entidades;

import java.io.Serializable;
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
@Table (name = "Factura")
public class Factura implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "Fecha")
    private String fecha;

    @Column (name = "Número")
    private int numero;

    @Column (name = "Total")
    private int total;

    //--- CREAMOS LA RELACIÓN UNIDIRECCIONAL ENTRE FACTURA Y CLIENTE  ---
    /* @ManyToOne (JPA) establece el tipo de relación, es este caso es muchos a uno.
    Con "cascade = CascadeType.PERSIST" al generar una factura solo persistimos un cliente si el mismo no existe.
    Si borro/modifico una factura el cliente no se ve afectado */
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "FK_Cliente")
    private Cliente cliente;

    //--- CREAMOS LA RELACIÓN UNIDIRECCIONAL ENTRE FACTURA Y DETALLEFACTURA ---
    /* @OneToMany (JPA) establece el tipo de relación, es este caso es uno a muchos.
    Con "cascade = CascadeType.ALL", como es una composición, cuando se elimine una factura
    también se eliminarán los detalles de la misma. A su vez, cuando se persista una nueva factura
    también se persisten los detalles.
    "orphanRemoval = true" nos ayuda a eliminar todos los detalles cuando se elimine una factura.
    @JoinColumn(name = "FK_DetalleFactura")
    Al no poner @JoinColumn me genera una nueva tabla con las claves primarias de la factura y de sus detalles. */
    //@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<DetalleFactura> detallesFacturaList = new ArrayList<DetalleFactura>();

    //--- CREAMOS LA RELACIÓN BIDIRECCIONAL ENTRE FACTURA Y DETALLEFACTURA  ---
    @Builder.Default
    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleFactura> detalles = new ArrayList<DetalleFactura>();

}
