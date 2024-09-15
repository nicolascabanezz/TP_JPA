package Main;

import Entidades.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Arrays;

public class PersistenceApp {
    public static void main(String[] args) {

        //Conectamos nuestra aplicación con la unidad de persistencia
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PersistenceAppPU");

        //Instanciamos EntityManagerFactory para poder utilizarlo
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        System.out.println("Todo OK ;)");

        try {

            //Iniciamos una transacción utilizando EntityManager
            entityManager.getTransaction().begin();

            //CREO INSTANCIAS DE FACTURA
            Factura factura1 = Factura.builder()
                    .fecha("31.08.2024")
                    .numero(1)
                    .build();
            Factura factura2 = Factura.builder()
                    .fecha("31.08.2024")
                    .numero(2)
                    .build();

            //CREO INSTANCIAS DE DOMICILIO
            Domicilio domicilio1 = Domicilio.builder()
                    .nombreCalle("Avenida de los Susurros Eternos")
                    .numero(2298)
                    .build();
            Domicilio domicilio2 = Domicilio.builder()
                    .nombreCalle("Calle del Crepúsculo Místico")
                    .numero(1200)
                    .build();

            //CREO INSTANCIAS DE CLIENTE ASOCIADAS A UN DOMICILIO
            Cliente cliente1 = Cliente.builder()
                    .dni(41369221)
                    .apellido("Cabañez")
                    .nombre("Jorge Nicolás")
                    .domicilio(domicilio1)
                    .build();
            Cliente cliente2 = Cliente.builder()
                    .dni(20114456)
                    .apellido("Cabañez")
                    .nombre("Nancy Miriam")
                    .domicilio(domicilio2)
                    .build();

            //ASOCIO CLIENTES A FACTURAS
            factura1.setCliente(cliente1);
            factura2.setCliente(cliente2);

            /* Asocio clientes a domicilios para establecer la relación bidireccional */
            domicilio1.setCliente(cliente1);
            domicilio2.setCliente(cliente2);

            //CREO INSTANCIAS DE CATEGORIA
            Categoria categoria1 = Categoria.builder()
                    .denominacion("Alimentos no perecederos")
                    .build();
            Categoria categoria2 = Categoria.builder()
                    .denominacion("Artículos de limpieza")
                    .build();
            Categoria categoria3 = Categoria.builder()
                    .denominacion("Lácteos")
                    .build();

            //CREO INSTANCIAS DE ARTÍCULO ASOCIADAS A UNA CATEGORÍA
            Articulo articulo1 = Articulo.builder()
                    .cantidad(20)
                    .denominacion("Arroz Gallo Oro X 500gr")
                    .precio(1900)
                    .categorias(Arrays.asList(categoria1))
                    .build();
            Articulo articulo2 = Articulo.builder()
                    .cantidad(35)
                    .denominacion("Aceite de Girasol Natura X 1.5L")
                    .precio(3500)
                    .categorias(Arrays.asList(categoria1))
                    .build();
            Articulo articulo3 = Articulo.builder()
                    .cantidad(25)
                    .denominacion("Cif Detergente Bioactive Lima X 750ml")
                    .precio(3600)
                    .categorias(Arrays.asList(categoria2))
                    .build();
            Articulo articulo4 = Articulo.builder()
                    .cantidad(30)
                    .denominacion("Manteca La Paulina X 200 Gr")
                    .precio(2700)
                    .categorias(Arrays.asList(categoria3))
                    .build();

            /* Asocio artículos a categorías para establecer la relación bidireccional */
            categoria1.getArticulos().add(articulo1); //Arroz
            categoria1.getArticulos().add(articulo2); //Aceite
            categoria2.getArticulos().add(articulo3); //Detergente
            categoria3.getArticulos().add(articulo4); //Manteca

            //CREO INSTANCIAS DE DETALLEFACTURA ASOCIADAS A UN ARTÍCULO
            //Detalles para la factura1
            DetalleFactura detalle1 = DetalleFactura.builder()
                    .articulo(articulo1)
                    .cantidad(1)
                    .build();
            detalle1.setSubTotal(detalle1.getCantidad()*articulo1.getPrecio());

            DetalleFactura detalle2 = DetalleFactura.builder()
                    .articulo(articulo2)
                    .cantidad(2)
                    .build();
            detalle2.setSubTotal(detalle2.getCantidad()*articulo2.getPrecio());

            //Detalles para la factura2
            DetalleFactura detalle3 = DetalleFactura.builder()
                    .articulo(articulo3)
                    .cantidad(1)
                    .build();
            detalle3.setSubTotal(detalle3.getCantidad()*articulo3.getPrecio());

            DetalleFactura detalle4 = DetalleFactura.builder()
                    .articulo(articulo4)
                    .cantidad(2)
                    .build();
            detalle4.setSubTotal(detalle4.getCantidad()*articulo4.getPrecio());

            /* Asocio detalles a artículos para establecer la relación bidireccional */
            articulo1.getDetalleFacturas().add(detalle1);
            articulo2.getDetalleFacturas().add(detalle2);
            articulo3.getDetalleFacturas().add(detalle3);
            articulo4.getDetalleFacturas().add(detalle4);

            //ASOCIO DETALLES A LAS FACTURAS
            factura1.getDetalles().add(detalle1);
            factura1.getDetalles().add(detalle2);
            factura2.getDetalles().add(detalle3);
            factura2.getDetalles().add(detalle4);

            //CALCULO EL MONTO TOTAL DE LAS FACTURAS
            factura1.setTotal(detalle1.getSubTotal() + detalle2.getSubTotal());
            factura2.setTotal(detalle3.getSubTotal() + detalle4.getSubTotal());

            //Persistimos artículos en la base de datos utilizando EntityManager
            entityManager.persist(articulo1);
            entityManager.persist(articulo2);
            entityManager.persist(articulo3);
            entityManager.persist(articulo4);

            //Persistimos facturas en la base de datos utilizando EntityManager
            entityManager.persist(factura1);
            entityManager.persist(factura2);

            entityManager.flush();
            entityManager.getTransaction().commit();

        } catch (Exception e){
            entityManager.getTransaction().rollback();
        }

        entityManager.close();
        entityManagerFactory.close();

        // ---FIN---
    }
}
