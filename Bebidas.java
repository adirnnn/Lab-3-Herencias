// Adrián López POO Lab 3

public class Bebidas extends Producto {
    private int mililitros;
    private String tipo;

    // Constructor para inicializar un producto de tipo Bebidas con sus atributos específicos.
    public Bebidas(int id, String nombre, int cantidadDisponible, int cantidadVendidos, boolean estado, double precio, int mililitros, String tipo) {
        super(id, nombre, cantidadDisponible, cantidadVendidos, estado, precio);
        this.mililitros = mililitros;
        this.tipo = tipo;
    }

    // Método para establecer el tipo de bebida.
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}