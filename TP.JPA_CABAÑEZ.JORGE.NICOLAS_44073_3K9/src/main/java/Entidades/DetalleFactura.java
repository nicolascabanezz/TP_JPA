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
@Table (name = "DetalleFactura")
public class DetalleFactura implements Serializable{

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "Cantidad")
    private int cantidad;

    @Column (name = "SubTotal")
    private int subTotal;

    //--- CREAMOS LA RELACIÓN UNIDIRECCIONAL ENTRE DETALLEFACTURA Y ARTÍCULO---
    @ManyToOne (cascade = CascadeType.PERSIST)
    @JoinColumn (name = "FK_Articulo")
    private Articulo articulo;

    //--- CREAMOS LA RELACIÓN BIDIRECCIONAL ENTRE FACTURA Y DETALLEFACTURA  ---
    @ManyToOne (cascade = CascadeType.PERSIST)
    @JoinColumn (name = "FK_Factura")
    private Factura factura;



}
