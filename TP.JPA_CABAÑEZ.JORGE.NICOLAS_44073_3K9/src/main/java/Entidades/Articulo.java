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
@Table (name = "Articulo")
public class Articulo implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "Cantidad")
    private int cantidad;

    @Column (name = "Denominación")
    private String denominacion;

    @Column (name = "Precio")
    private int precio;

    //--- CREAMOS LA RELACIÓN BIDIRECCIONAL ENTRE DETALLEFACTURA Y ARTÍCULO  ---
    @Builder.Default
    @OneToMany (mappedBy = "articulo", cascade = CascadeType.PERSIST)
    private List<DetalleFactura> detalleFacturas = new ArrayList<DetalleFactura>();

    //--- CREAMOS LA RELACIÓN UNIDIRECCIONAL ENTRE ARTÍCULO Y CATEGORÍA  ---
    /* "cascade = {CascadeType.PERSIST, CascadeType.MERGE}" Solo usamos PERSIST y MERGE ya que solo necesitamos que
    se persistan las categorías o se actualicen. No vamos a necesitar que cuando se elimine un artículo se eliminen
    las categorías. */
    @Builder.Default
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    //@JoinTable se encarga de crear la nueva tabla de relación muchos a muchos.
    @JoinTable(
            name = "Articulo_Categoria",
            joinColumns = @JoinColumn(name = "articulo_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id"))
    private List<Categoria> categorias = new ArrayList<Categoria>();



}
